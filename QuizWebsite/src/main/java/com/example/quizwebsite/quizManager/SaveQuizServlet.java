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
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/SaveQuizServlet")
public class SaveQuizServlet extends HttpServlet {
    private QuizManager quizManager;
    private static final Logger LOGGER = Logger.getLogger(SaveQuizServlet.class.getName());

    @Override
    public void init() throws ServletException {
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource is not initialized in the ServletContext");
        }
        this.quizManager = new QuizManager(dataSource);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String name = getRequiredParameter(request, "quizName");
            String description = getRequiredParameter(request, "quizDescription");
            QuizCategory category = QuizCategory.valueOf(getRequiredParameter(request, "quizCategory"));
            boolean displayOnSinglePage = Boolean.parseBoolean(request.getParameter("displayOnSinglePage"));
            boolean displayInRandomOrder = Boolean.parseBoolean(request.getParameter("displayInRandomOrder"));
            boolean allowPracticeMode = Boolean.parseBoolean(request.getParameter("allowPracticeMode"));
            boolean correctImmediately = Boolean.parseBoolean(request.getParameter("correctImmediately"));

            Quiz quiz = new Quiz(name, description, category, displayOnSinglePage, displayInRandomOrder, allowPracticeMode, correctImmediately);

            int quizId = quizManager.saveQuizToDatabase(quiz, user.getUsername());
            LOGGER.info("Quiz saved with ID: " + quizId);
            LOGGER.info("Redirecting to: " + "create/addQuestions.jsp?quizId=" + quizId + "&quizName=" + URLEncoder.encode(name, StandardCharsets.UTF_8.name()));

            if (quizId != -1) {
                response.sendRedirect("create/addQuestions.jsp?quizId=" + quizId + "&quizName=" + URLEncoder.encode(name, StandardCharsets.UTF_8.name()));
            } else {
                throw new ServletException("Error saving quiz to database");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid input: " + e.getMessage(), e);
            request.setAttribute("error", "Invalid input: " + e.getMessage());
            request.getRequestDispatcher("/create/createQuiz.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while saving quiz", e);
            throw new ServletException("Error saving quiz to database", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error processing quiz data", e);
            throw new ServletException("Error processing quiz data", e);
        }
    }

    private String getRequiredParameter(HttpServletRequest request, String paramName) throws IllegalArgumentException {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: " + paramName);
        }
        return value.trim();
    }
}