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

@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
    private QuizManager quizManager;
    private static final Logger LOGGER = Logger.getLogger(AddQuestionServlet.class.getName());

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
        String quizId = request.getParameter("quizId");
        String quizName = request.getParameter("quizName");

        try {
            int quizIdInt = Integer.parseInt(quizId);
            String questionText = getRequiredParameter(request, "questionText");
            QuestionType questionType = QuestionType.valueOf(getRequiredParameter(request, "questionType"));

            Question question = new Question(quizIdInt, questionText, questionType);

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
                default:
                    throw new IllegalArgumentException("Unsupported question type: " + questionType);
            }

            LOGGER.info("Saving question: " + question.toString());
            boolean saved = quizManager.saveQuestion(question);

            if (saved) {
                response.sendRedirect(request.getContextPath() + "/create/addQuestions.jsp?quizId=" + quizId + "&quizName=" + URLEncoder.encode(quizName, StandardCharsets.UTF_8.toString()));
            } else {
                throw new ServletException("Failed to save the question");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid input", e);
            handleError(request, response, "Invalid input: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
            handleError(request, response, "Database error: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error", e);
            handleError(request, response, "Unexpected error: " + e.getMessage());
        }
    }

    private void handleMultipleChoiceQuestion(HttpServletRequest request, Question question) {
        String[] correctOptions = request.getParameterValues("correctOptions");
        List<String> correctAnswers = new ArrayList<>();
        int optionCount = 1;

        while (true) {
            String option = request.getParameter("option" + optionCount);
            if (option == null || option.trim().isEmpty()) {
                break;
            }
            boolean isCorrect = correctOptions != null &&
                    java.util.Arrays.asList(correctOptions).contains(String.valueOf(optionCount - 1));
            question.addOption(option.trim(), isCorrect);
            if (isCorrect) {
                correctAnswers.add(option.trim());
            }
            optionCount++;
        }

        if (optionCount < 3) {
            throw new IllegalArgumentException("Multiple choice questions must have at least 2 options");
        }

        String correctAnswerString = String.join(", ", correctAnswers);
        question.setCorrectAnswer(correctAnswerString);
        LOGGER.info("Setting Multiple Choice correct answer(s): " + correctAnswerString);
    }

    private void handleTrueFalseQuestion(HttpServletRequest request, Question question) {
        String correctAnswer = getRequiredParameter(request, "correctAnswer");
        question.setCorrectAnswer(correctAnswer);
        LOGGER.info("Setting True/False correct answer: " + correctAnswer);
        question.addOption("True", "true".equals(correctAnswer));
        question.addOption("False", "false".equals(correctAnswer));
    }

    private void handleSingleAnswerQuestion(HttpServletRequest request, Question question) {
        String correctAnswer = getRequiredParameter(request, "correctAnswer");
        question.setCorrectAnswer(correctAnswer);
        LOGGER.info("Setting Short Answer correct answer: " + correctAnswer);
    }

    private String getRequiredParameter(HttpServletRequest request, String paramName) throws IllegalArgumentException {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: " + paramName);
        }
        return value.trim();
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/create/addQuestions.jsp").forward(request, response);
    }
}