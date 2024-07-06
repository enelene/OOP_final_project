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

/**
 * Servlet responsible for saving a new quiz to the database.
 * This servlet handles the POST request from the quiz creation form.
 */
@WebServlet("/SaveQuizServlet")
public class SaveQuizServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SaveQuizServlet.class.getName());
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ADD_QUESTIONS_PAGE = "create/addQuestions.jsp";
    private static final String CREATE_QUIZ_PAGE = "/create/createQuiz.jsp";

    private QuizManager quizManager;

    /**
     * Initializes the servlet by setting up the QuizManager with the data source.
     * @throws ServletException if the data source is not initialized in the ServletContext
     */
    @Override
    public void init() throws ServletException {
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource is not initialized in the ServletContext");
        }
        this.quizManager = new QuizManager(dataSource);
    }

    /**
     * Handles the POST request to save a new quiz.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }

        try {
            Quiz quiz = createQuizFromRequest(request);
            int quizId = saveQuizAndLogDetails(quiz, user.getUsername());
            redirectToAddQuestions(response, quiz.getName(), quizId);
        } catch (IllegalArgumentException e) {
            handleInputError(request, response, e);
        } catch (SQLException e) {
            handleDatabaseError(e);
        } catch (Exception e) {
            handleUnexpectedError(e);
        }
    }

    /**
     * Creates a Quiz object from the request parameters.
     * @param request the HttpServletRequest object
     * @return a new Quiz object
     * @throws IllegalArgumentException if required parameters are missing
     */
    private Quiz createQuizFromRequest(HttpServletRequest request) throws IllegalArgumentException {
        String name = getRequiredParameter(request, "quizName");
        String description = getRequiredParameter(request, "quizDescription");
        QuizCategory category = QuizCategory.valueOf(getRequiredParameter(request, "quizCategory"));
        boolean displayOnSinglePage = Boolean.parseBoolean(request.getParameter("displayOnSinglePage"));
        boolean displayInRandomOrder = Boolean.parseBoolean(request.getParameter("displayInRandomOrder"));
        boolean allowPracticeMode = Boolean.parseBoolean(request.getParameter("allowPracticeMode"));
        boolean correctImmediately = Boolean.parseBoolean(request.getParameter("correctImmediately"));

        return new Quiz(name, description, category, displayOnSinglePage, displayInRandomOrder, allowPracticeMode, correctImmediately);
    }

    /**
     * Saves the quiz to the database and logs the details.
     * @param quiz the Quiz object to save
     * @param username the username of the quiz creator
     * @return the ID of the saved quiz
     * @throws SQLException if a database error occurs
     * @throws ServletException if the quiz couldn't be saved
     */
    private int saveQuizAndLogDetails(Quiz quiz, String username) throws SQLException, ServletException {
        int quizId = quizManager.saveQuizToDatabase(quiz, username);
        LOGGER.info("Quiz saved with ID: " + quizId);

        if (quizId == -1) {
            throw new ServletException("Error saving quiz to database");
        }

        return quizId;
    }

    /**
     * Redirects to the add questions page after successfully saving the quiz.
     * @param response the HttpServletResponse object
     * @param quizName the name of the quiz
     * @param quizId the ID of the saved quiz
     * @throws IOException if an I/O error occurs
     */
    private void redirectToAddQuestions(HttpServletResponse response, String quizName, int quizId) throws IOException {
        String encodedQuizName = URLEncoder.encode(quizName, StandardCharsets.UTF_8.name());
        String redirectUrl = ADD_QUESTIONS_PAGE + "?quizId=" + quizId + "&quizName=" + encodedQuizName;
        LOGGER.info("Redirecting to: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    /**
     * Handles input errors by logging the error and forwarding to the create quiz page.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param e the IllegalArgumentException that was caught
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleInputError(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException e)
            throws ServletException, IOException {
        LOGGER.log(Level.WARNING, "Invalid input: " + e.getMessage(), e);
        request.setAttribute("error", "Invalid input: " + e.getMessage());
        request.getRequestDispatcher(CREATE_QUIZ_PAGE).forward(request, response);
    }

    /**
     * Handles database errors by logging the error and throwing a ServletException.
     * @param e the SQLException that was caught
     * @throws ServletException with the database error message
     */
    private void handleDatabaseError(SQLException e) throws ServletException {
        LOGGER.log(Level.SEVERE, "Database error while saving quiz", e);
        throw new ServletException("Error saving quiz to database", e);
    }

    /**
     * Handles unexpected errors by logging the error and throwing a ServletException.
     * @param e the Exception that was caught
     * @throws ServletException with the unexpected error message
     */
    private void handleUnexpectedError(Exception e) throws ServletException {
        LOGGER.log(Level.SEVERE, "Unexpected error processing quiz data", e);
        throw new ServletException("Error processing quiz data", e);
    }

    /**
     * Retrieves a required parameter from the request.
     * @param request the HttpServletRequest object
     * @param paramName the name of the parameter
     * @return the trimmed value of the parameter
     * @throws IllegalArgumentException if the parameter is missing or empty
     */
    private String getRequiredParameter(HttpServletRequest request, String paramName) throws IllegalArgumentException {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: " + paramName);
        }
        return value.trim();
    }
}