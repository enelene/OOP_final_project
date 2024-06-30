package com.example.quizwebsite.quizManager;

import com.example.quizwebsite.quizManager.Question;
import com.example.quizwebsite.quizManager.Quiz;
import com.example.quizwebsite.quizManager.QuizManager;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/ViewQuizServlet")
public class ViewQuizServlet extends HttpServlet {
    private QuizManager quizManager;

    @Override
    public void init() throws ServletException {
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        this.quizManager = new QuizManager(dataSource);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("id"));

        Quiz quiz = quizManager.getQuizById(quizId);
        List<Question> questions = quizManager.getQuestionsByQuizId(quizId);

        request.setAttribute("quiz", quiz);
        request.setAttribute("questions", questions);

        request.getRequestDispatcher("create/viewQuiz.jsp").forward(request, response);
    }
}