package com.example.quizwebsite.quizManager;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ViewQuizServlet")
public class ViewQuizServlet extends HttpServlet {
    private QuizManager quizManager;
    private static final Logger LOGGER = Logger.getLogger(ViewQuizServlet.class.getName());

    @Override
    public void init() throws ServletException {
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource is not initialized in the ServletContext");
        }
        this.quizManager = new QuizManager(dataSource);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int quizId = getQuizId(request);
            String mode = request.getParameter("mode");

            Quiz quiz = quizManager.getQuizById(quizId);
            if (quiz == null) {
                handleError(request, response, "Quiz not found", HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            List<Question> questions = quizManager.getQuestionsByQuizId(quizId);

            request.setAttribute("quiz", quiz);
            request.setAttribute("questions", questions);

            String viewPage;
            if ("edit".equals(mode)) {
                viewPage = "create/editQuiz.jsp";
            } else if ("take".equals(mode)) {
                viewPage = "quiz/takeQuiz.jsp";
            } else {
                viewPage = "create/viewQuiz.jsp";
            }

            request.getRequestDispatcher(viewPage).forward(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid quiz ID", e);
            handleError(request, response, "Invalid quiz ID: " + e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
            handleError(request, response, "Database error: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error", e);
            handleError(request, response, "Unexpected error: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private int getQuizId(HttpServletRequest request) throws IllegalArgumentException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing quiz ID");
        }
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quiz ID format");
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage, int statusCode)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        response.setStatus(statusCode);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}