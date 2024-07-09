package com.example.quizwebsite.quizManager.playQuiz;

import com.example.quizwebsite.quizManager.Quiz;
import com.example.quizwebsite.quizManager.QuizManager;
import com.example.quizwebsite.quizManager.Question;
import com.example.quizwebsite.userManager.User;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


@WebServlet("/PlayQuizServlet")
public class PlayQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizIdStr = request.getParameter("quizId");
        if (quizIdStr == null || quizIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz ID is required");
            return;
        }

        int quizId;
        try {
            quizId = Integer.parseInt(quizIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Quiz ID");
            return;
        }

        QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quizManager");
        if (quizManager == null) {
            throw new ServletException("QuizManager not found in ServletContext");
        }

        try {
            Quiz quiz = quizManager.getQuizById(quizId);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
                return;
            }

            List<Question> questions = quizManager.getQuestionsByQuizId(quizId);
            quiz.setQuestions(questions);

            request.setAttribute("quiz", quiz);
            request.getRequestDispatcher("/playQuiz/playQuiz.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving quiz data", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle quiz submission
        String quizIdStr = request.getParameter("quizId");
        if (quizIdStr == null || quizIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz ID is required");
            return;
        }

        int quizId = Integer.parseInt(quizIdStr);
        QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quizManager");
        if (quizManager == null) {
            throw new ServletException("QuizManager not found in ServletContext");
        }

        try {
            Quiz quiz = quizManager.getQuizById(quizId);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
                return;
            }

            List<Question> questions = quizManager.getQuestionsByQuizId(quizId);
            int score = 0;

            for (Question question : questions) {
                String userAnswer = request.getParameter("question_" + question.getId());
                if (userAnswer != null && userAnswer.equals(question.getCorrectAnswer())) {
                    score++;
                }
            }
            quizManager.addAttempt(quizId, ((User)request.getSession().getAttribute("user")).getId(), score, LocalDateTime.now());

            request.setAttribute("quiz", quiz);
            request.setAttribute("score", score);
            request.setAttribute("totalQuestions", questions.size());
            request.getRequestDispatcher("/playQuiz/quizResult.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error processing quiz submission", e);
        }
    }
}