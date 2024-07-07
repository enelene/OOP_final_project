package com.example.quizwebsite.quizManager.playQuiz;
import com.example.quizwebsite.quizManager.Quiz;
import com.example.quizwebsite.quizManager.QuizManager;
import com.example.quizwebsite.relationManager.RelationManager;
import com.example.quizwebsite.userManager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/QuizzesServlet")
public class QuizzesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get QuizManager from ServletContext
        QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quizManager");
        // Check if quizManager is null
        if (quizManager == null) {
            throw new ServletException("QuizManager not found in ServletContext");
        }

        List<Quiz> quizzes = quizManager.getAllQuizzes();

        request.setAttribute("quizzes", quizzes);

        request.getRequestDispatcher("/homepage/listQuizzes.jsp").forward(request, response);
    }
}