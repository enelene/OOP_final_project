package com.example.quizwebsite.quizManager;

import com.example.quizwebsite.userManager.User;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/SaveQuizServlet")
public class SaveQuizServlet extends HttpServlet {
    private QuizManager quizManager;

    @Override
    public void init() throws ServletException {
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource is not initialized in the ServletContext");
        }
        this.quizManager = new QuizManager(dataSource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("quizName");
        String description = request.getParameter("quizDescription");
        String category = request.getParameter("quizCategory");
        boolean displayOnSinglePage = request.getParameter("displayOnSinglePage") != null;
        boolean displayInRandomOrder = request.getParameter("displayInRandomOrder") != null;
        boolean allowPracticeMode = request.getParameter("allowPracticeMode") != null;
        boolean correctImmediately = request.getParameter("correctImmediately") != null;

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Quiz quiz = new Quiz(name, description, category, displayOnSinglePage, displayInRandomOrder, allowPracticeMode, correctImmediately);

        boolean isSaved = quizManager.saveQuizToDatabase(quiz, user.getUsername());
        if (isSaved) {
            int quizId = quizManager.getQuizIdByName(name);
            if (quizId != -1) {
                response.sendRedirect("create/addQuestions.jsp?quizId=" + quizId + "&quizName=" + URLEncoder.encode(name, "UTF-8"));
            } else {
                throw new ServletException("Error retrieving quiz ID after saving");
            }
        } else {
            throw new ServletException("Error saving quiz to database");
        }
    }
}