package com.example.quizwebsite.quizManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;

@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
    private QuizManager quizManager;

    @Override
    public void init() throws ServletException {
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        this.quizManager = new QuizManager(dataSource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        String questionText = request.getParameter("questionText");
        String questionType = request.getParameter("questionType");

        Question question = new Question();
        question.setQuizId(quizId);
        question.setText(questionText);
        question.setType(questionType);

        if ("multiple_choice".equals(questionType)) {
            List<String> options = new ArrayList<>();
            List<Boolean> correctOptions = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                String option = request.getParameter("option" + i);
                if (option != null && !option.trim().isEmpty()) {
                    options.add(option);
                    correctOptions.add(request.getParameterValues("correctOptions") != null &&
                            java.util.Arrays.asList(request.getParameterValues("correctOptions")).contains(String.valueOf(i-1)));
                }
            }
            question.setOptions(options);
            question.setCorrectOptions(correctOptions);
        } else if ("true_false".equals(questionType)) {
            question.setCorrectAnswer(request.getParameter("correctAnswer"));
        } else if ("short_answer".equals(questionType)) {
            question.setCorrectAnswer(request.getParameter("correctAnswer"));
        }

        boolean saved = quizManager.saveQuestion(question);

        if (saved) {
            response.sendRedirect("create/addQuestions.jsp?quizId=" + quizId);
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}