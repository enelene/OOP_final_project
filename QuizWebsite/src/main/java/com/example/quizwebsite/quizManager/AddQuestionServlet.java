package com.example.quizwebsite.quizManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Servlet responsible for adding questions to a quiz.
 * This servlet handles the POST request from the add questions form.
 */
@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddQuestionServlet.class.getName());
    private static final String ADD_QUESTIONS_PAGE = "/create/addQuestions.jsp";
    private static final int MINIMUM_MULTIPLE_CHOICE_OPTIONS = 2;

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
     * Handles the POST request to add a new question to a quiz.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizId = request.getParameter("quizId");
        String quizName = request.getParameter("quizName");

        try {
            Question question = createQuestionFromRequest(request);
            boolean saved = saveQuestionAndLogDetails(question);

            if (saved) {
                redirectToAddQuestionsPage(request, response, quizId, quizName);
            } else {
                throw new ServletException("Failed to save the question");
            }
        } catch (IllegalArgumentException e) {
            handleInputError(request, response, e);
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        } catch (Exception e) {
            handleUnexpectedError(request, response, e);
        }
    }

    /**
     * Creates a Question object from the request parameters.
     * @param request the HttpServletRequest object
     * @return a new Question object
     * @throws IllegalArgumentException if required parameters are missing or invalid
     */
    private Question createQuestionFromRequest(HttpServletRequest request) throws IllegalArgumentException {
        int quizIdInt = Integer.parseInt(getRequiredParameter(request, "quizId"));
        String questionText = getRequiredParameter(request, "questionText");
        QuestionType questionType = QuestionType.valueOf(getRequiredParameter(request, "questionType"));
        String imageUrl = request.getParameter("imageUrl"); // New parameter for image URL

        Question question = new Question(quizIdInt, questionText, questionType, imageUrl);

        switch (questionType) {
            case MULTIPLE_CHOICE:
                handleMultipleChoiceQuestion(request, question);
                break;
            case TRUE_FALSE:
                handleTrueFalseQuestion(request, question);
                break;
            case SINGLE_ANSWER:
                handleSingleAnswerQuestion(request, question);
                break;
            case PICTURE_RESPONSE:
                handlePictureResponseQuestion(request, question);
                break;
            default:
                throw new IllegalArgumentException("Unsupported question type: " + questionType);
        }

        return question;
    }

    private void handlePictureResponseQuestion(HttpServletRequest request, Question question) {
        String correctAnswer = getRequiredParameter(request, "correctAnswer");
        question.setCorrectAnswer(correctAnswer);
        LOGGER.info("Setting Picture-Response correct answer: " + correctAnswer);
    }

    /**
     * Handles the creation of a multiple-choice question.
     * @param request the HttpServletRequest object
     * @param question the Question object to populate
     * @throws IllegalArgumentException if the question has fewer than the minimum required options
     */
    private void handleMultipleChoiceQuestion(HttpServletRequest request, Question question) {
        String[] correctOptions = request.getParameterValues("correctOptions");
        List<String> correctAnswers = new ArrayList<>();
        int optionCount = 1;

        while (true) {
            String option = request.getParameter("option" + optionCount);
            if (option == null || option.trim().isEmpty()) {
                break;
            }
            boolean isCorrect = isOptionCorrect(correctOptions, optionCount);
            question.addOption(option.trim(), isCorrect);
            if (isCorrect) {
                correctAnswers.add(option.trim());
            }
            optionCount++;
        }

        if (optionCount <= MINIMUM_MULTIPLE_CHOICE_OPTIONS) {
            throw new IllegalArgumentException("Multiple choice questions must have at least " + MINIMUM_MULTIPLE_CHOICE_OPTIONS + " options");
        }

        String correctAnswerString = String.join(", ", correctAnswers);
        question.setCorrectAnswer(correctAnswerString);
        LOGGER.info("Setting Multiple Choice correct answer(s): " + correctAnswerString);
    }

    /**
     * Checks if a given option is marked as correct.
     * @param correctOptions array of correct option indices
     * @param optionCount the current option index
     * @return true if the option is correct, false otherwise
     */
    private boolean isOptionCorrect(String[] correctOptions, int optionCount) {
        return correctOptions != null &&
                java.util.Arrays.asList(correctOptions).contains(String.valueOf(optionCount - 1));
    }

    /**
     * Handles the creation of a true/false question.
     * @param request the HttpServletRequest object
     * @param question the Question object to populate
     */
    private void handleTrueFalseQuestion(HttpServletRequest request, Question question) {
        String correctAnswer = getRequiredParameter(request, "correctAnswer");
        question.setCorrectAnswer(correctAnswer);
        LOGGER.info("Setting True/False correct answer: " + correctAnswer);
        question.addOption("True", "true".equals(correctAnswer));
        question.addOption("False", "false".equals(correctAnswer));
    }

    /**
     * Handles the creation of a single answer question.
     * @param request the HttpServletRequest object
     * @param question the Question object to populate
     */
    private void handleSingleAnswerQuestion(HttpServletRequest request, Question question) {
        String correctAnswer = getRequiredParameter(request, "correctAnswer");
        question.setCorrectAnswer(correctAnswer);
        LOGGER.info("Setting Short Answer correct answer: " + correctAnswer);
    }

    /**
     * Saves the question to the database and logs the details.
     * @param question the Question object to save
     * @return true if the question was saved successfully, false otherwise
     * @throws SQLException if a database error occurs
     */
    private boolean saveQuestionAndLogDetails(Question question) throws SQLException {
        LOGGER.info("Saving question: " + question.toString());
        return quizManager.saveQuestion(question);
    }

    /**
     * Redirects to the add questions page after successfully saving the question.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param quizId the ID of the quiz
     * @param quizName the name of the quiz
     * @throws IOException if an I/O error occurs
     */
    private void redirectToAddQuestionsPage(HttpServletRequest request, HttpServletResponse response, String quizId, String quizName) throws IOException {
        String encodedQuizName = URLEncoder.encode(quizName, StandardCharsets.UTF_8.toString());
        String redirectUrl = request.getContextPath() + ADD_QUESTIONS_PAGE + "?quizId=" + quizId + "&quizName=" + encodedQuizName;
        response.sendRedirect(redirectUrl);
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

    /**
     * Handles input errors by logging the error and forwarding to the add questions page.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param e the IllegalArgumentException that was caught
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleInputError(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException e)
            throws ServletException, IOException {
        LOGGER.log(Level.WARNING, "Invalid input", e);
        handleError(request, response, "Invalid input: " + e.getMessage());
    }

    /**
     * Handles database errors by logging the error and forwarding to the add questions page.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param e the SQLException that was caught
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleDatabaseError(HttpServletRequest request, HttpServletResponse response, SQLException e)
            throws ServletException, IOException {
        LOGGER.log(Level.SEVERE, "Database error", e);
        handleError(request, response, "Database error: " + e.getMessage());
    }

    /**
     * Handles unexpected errors by logging the error and forwarding to the add questions page.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param e the Exception that was caught
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleUnexpectedError(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws ServletException, IOException {
        LOGGER.log(Level.SEVERE, "Unexpected error", e);
        handleError(request, response, "Unexpected error: " + e.getMessage());
    }

    /**
     * Handles errors by setting an error message and forwarding to the add questions page.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param errorMessage the error message to display
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher(ADD_QUESTIONS_PAGE).forward(request, response);
    }
}