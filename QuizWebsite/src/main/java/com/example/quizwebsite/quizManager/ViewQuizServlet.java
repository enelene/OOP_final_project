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

/**
 * Servlet responsible for handling requests to view a quiz.
 * This servlet supports different viewing modes: view, edit, and take.
 */
@WebServlet("/ViewQuizServlet")
public class ViewQuizServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ViewQuizServlet.class.getName());
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String EDIT_PAGE = "create/editQuiz.jsp";
    private static final String TAKE_PAGE = "quiz/takeQuiz.jsp";
    private static final String VIEW_PAGE = "create/viewQuiz.jsp";
    private static final String MODE_PARAM = "mode";
    private static final String ID_PARAM = "id";

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
     * Handles GET requests to view a quiz.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int quizId = getQuizId(request);
            String mode = request.getParameter(MODE_PARAM);

            Quiz quiz = fetchQuiz(quizId);
            List<Question> questions = fetchQuestions(quizId);

            setRequestAttributes(request, quiz, questions);
            forwardToAppropriateView(request, response, mode);
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

    /**
     * Retrieves the quiz ID from the request parameters.
     * @param request the HttpServletRequest object
     * @return the quiz ID as an integer
     * @throws IllegalArgumentException if the quiz ID is missing or invalid
     */
    private int getQuizId(HttpServletRequest request) throws IllegalArgumentException {
        String idParam = request.getParameter(ID_PARAM);
        if (idParam == null || idParam.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing quiz ID");
        }
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quiz ID format");
        }
    }

    /**
     * Fetches a quiz by its ID.
     * @param quizId the ID of the quiz to fetch
     * @return the Quiz object
     * @throws SQLException if a database error occurs
     * @throws ServletException if the quiz is not found
     */
    private Quiz fetchQuiz(int quizId) throws SQLException, ServletException {
        Quiz quiz = quizManager.getQuizById(quizId);
        if (quiz == null) {
            throw new ServletException("Quiz not found");
        }
        return quiz;
    }

    /**
     * Fetches questions for a given quiz ID.
     * @param quizId the ID of the quiz
     * @return a List of Question objects
     * @throws SQLException if a database error occurs
     */
    private List<Question> fetchQuestions(int quizId) throws SQLException {
        return quizManager.getQuestionsByQuizId(quizId);
    }

    /**
     * Sets the quiz and questions as request attributes.
     * @param request the HttpServletRequest object
     * @param quiz the Quiz object
     * @param questions the List of Question objects
     */
    private void setRequestAttributes(HttpServletRequest request, Quiz quiz, List<Question> questions) {
        request.setAttribute("quiz", quiz);
        request.setAttribute("questions", questions);
    }

    /**
     * Forwards the request to the appropriate view based on the mode.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param mode the viewing mode
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void forwardToAppropriateView(HttpServletRequest request, HttpServletResponse response, String mode)
            throws ServletException, IOException {
        String viewPage = determineViewPage(mode);
        request.getRequestDispatcher(viewPage).forward(request, response);
    }

    /**
     * Determines the appropriate view page based on the mode.
     * @param mode the viewing mode
     * @return the path to the appropriate JSP page
     */
    private String determineViewPage(String mode) {
        if ("edit".equals(mode)) {
            return EDIT_PAGE;
        } else if ("take".equals(mode)) {
            return TAKE_PAGE;
        } else {
            return VIEW_PAGE;
        }
    }

    /**
     * Handles errors by setting the appropriate status code and forwarding to the error page.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param errorMessage the error message to display
     * @param statusCode the HTTP status code to set
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage, int statusCode)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        response.setStatus(statusCode);
        request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
    }
}