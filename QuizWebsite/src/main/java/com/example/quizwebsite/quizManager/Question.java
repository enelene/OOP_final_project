package com.example.quizwebsite.quizManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a question in a quiz.
 * This class encapsulates all the properties and behaviors of a quiz question.
 */
public class Question {
    private static final String ID_ERROR_MESSAGE = "ID must be non-negative";
    private static final String QUIZ_ID_ERROR_MESSAGE = "Quiz ID must be non-negative";
    private static final String TEXT_ERROR_MESSAGE = "Question text cannot be null";
    private static final String TYPE_ERROR_MESSAGE = "Question type cannot be null";
    private static final String OPTIONS_ERROR_MESSAGE = "Options list cannot be null";
    private static final String CORRECT_OPTIONS_ERROR_MESSAGE = "Correct options list cannot be null";
    private static final String OPTION_ERROR_MESSAGE = "Option cannot be null";
    private String imageUrl;
    private int id;
    private int quizId;
    private String text;
    private QuestionType type;
    private List<String> options;
    private List<Boolean> correctOptions;
    private String correctAnswer;

    /**
     * Constructs a new Question with the given quiz ID, text, and type.
     *
     * @param quizId the ID of the quiz this question belongs to
     * @param text   the text of the question
     * @param type   the type of the question
     * @throws IllegalArgumentException if quizId is negative
     * @throws NullPointerException     if text or type is null
     */
    public Question(int quizId, String text, QuestionType type, String imageUrl) {
        setQuizId(quizId);
        setText(text);
        setType(type);
        setImageUrl(imageUrl);
        this.options = new ArrayList<>();
        this.correctOptions = new ArrayList<>();
    }

    /**
     * Gets the ID of the question.
     *
     * @return the question ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the ID of the quiz this question belongs to.
     *
     * @return the quiz ID
     */
    public int getQuizId() {
        return quizId;
    }

    /**
     * Gets the text of the question.
     *
     * @return the question text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the type of the question.
     *
     * @return the question type
     */
    public QuestionType getType() {
        return type;
    }

    /**
     * Gets an unmodifiable list of the question options.
     *
     * @return an unmodifiable list of options
     */
    public List<String> getOptions() {
        return Collections.unmodifiableList(options);
    }

    /**
     * Gets an unmodifiable list indicating which options are correct.
     *
     * @return an unmodifiable list of boolean values
     */
    public List<Boolean> getCorrectOptions() {
        return Collections.unmodifiableList(correctOptions);
    }

    /**
     * Gets the correct answer for the question.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets the ID of the question.
     *
     * @param id the ID to set
     * @throws IllegalArgumentException if id is negative
     */
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException(ID_ERROR_MESSAGE);
        }
        this.id = id;
    }

    /**
     * Sets the ID of the quiz this question belongs to.
     *
     * @param quizId the quiz ID to set
     * @throws IllegalArgumentException if quizId is negative
     */
    public void setQuizId(int quizId) {
        if (quizId < 0) {
            throw new IllegalArgumentException(QUIZ_ID_ERROR_MESSAGE);
        }
        this.quizId = quizId;
    }

    /**
     * Sets the text of the question.
     *
     * @param text the question text to set
     * @throws NullPointerException if text is null
     */
    public void setText(String text) {
        this.text = Objects.requireNonNull(text, TEXT_ERROR_MESSAGE);
    }

    /**
     * Sets the type of the question.
     *
     * @param type the question type to set
     * @throws NullPointerException if type is null
     */
    public void setType(QuestionType type) {
        this.type = Objects.requireNonNull(type, TYPE_ERROR_MESSAGE);
    }

    /**
     * Sets the options for the question.
     *
     * @param options the list of options to set
     * @throws NullPointerException if options is null
     */
    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(Objects.requireNonNull(options, OPTIONS_ERROR_MESSAGE));
    }

    /**
     * Sets which options are correct.
     *
     * @param correctOptions the list of boolean values indicating correct options
     * @throws NullPointerException if correctOptions is null
     */
    public void setCorrectOptions(List<Boolean> correctOptions) {
        this.correctOptions = new ArrayList<>(Objects.requireNonNull(correctOptions, CORRECT_OPTIONS_ERROR_MESSAGE));
    }

    /**
     * Sets the correct answer for the question.
     *
     * @param correctAnswer the correct answer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Adds an option to the question and specifies whether it's correct.
     *
     * @param option    the option to add
     * @param isCorrect whether the option is correct
     * @throws NullPointerException if option is null
     */
    public void addOption(String option, boolean isCorrect) {
        options.add(Objects.requireNonNull(option, OPTION_ERROR_MESSAGE));
        correctOptions.add(isCorrect);
    }

    /**
     * Clears all options and correct options from the question.
     */
    public void clearOptions() {
        options.clear();
        correctOptions.clear();
    }

    /**
     * Returns a string representation of the Question object.
     *
     * @return a string representation of the Question
     */
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", text='" + text + '\'' +
                ", type=" + type +
                ", options=" + options +
                ", correctOptions=" + correctOptions +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }

    /**
     * Checks if the question has any correct options set.
     *
     * @return true if at least one option is marked as correct, false otherwise
     */
    public boolean hasCorrectOption() {
        return correctOptions.contains(true);
    }

    /**
     * Gets the number of options for this question.
     *
     * @return the number of options
     */
    public int getOptionCount() {
        return options.size();
    }

    /**
     * Checks if the question is valid based on its type and options.
     *
     * @return true if the question is valid, false otherwise
     */
    public boolean isValid() {
        switch (type) {
            case MULTIPLE_CHOICE:
                return getOptionCount() >= 2 && hasCorrectOption();
            case TRUE_FALSE:
                return getOptionCount() == 2 && hasCorrectOption();
            case SINGLE_ANSWER:
                return correctAnswer != null && !correctAnswer.trim().isEmpty();
            case PICTURE_RESPONSE:
                return correctAnswer != null && !correctAnswer.trim().isEmpty() &&
                        (type != QuestionType.PICTURE_RESPONSE || (imageUrl != null && !imageUrl.trim().isEmpty()));
            default:
                return false;
        }
    }

    // Add getter and setter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}