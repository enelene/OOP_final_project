package com.example.quizwebsite.quizManager;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;

/**
 * Represents a quiz in the quiz website.
 * This class encapsulates all the properties and behaviors of a quiz.
 */
public class Quiz {
    private static final String NAME_ERROR_MESSAGE = "Name cannot be null";
    private static final String DESCRIPTION_ERROR_MESSAGE = "Description cannot be null";
    private static final String CATEGORY_ERROR_MESSAGE = "Category cannot be null";
    private static final String QUESTION_ERROR_MESSAGE = "Question cannot be null";

    private int id;
    private String name;
    private String description;
    private QuizCategory category;
    private boolean displayOnSinglePage;
    private boolean displayInRandomOrder;
    private boolean allowPracticeMode;
    private boolean correctImmediately;
    private List<Question> questions;

    /**
     * Constructs a new Quiz with the given properties.
     *
     * @param name                 the name of the quiz
     * @param description          the description of the quiz
     * @param category             the category of the quiz
     * @param displayOnSinglePage  whether to display all questions on a single page
     * @param displayInRandomOrder whether to display questions in random order
     * @param allowPracticeMode    whether to allow practice mode
     * @param correctImmediately   whether to correct answers immediately
     * @throws NullPointerException if name, description, or category is null
     */
    public Quiz(String name, String description, QuizCategory category, boolean displayOnSinglePage,
                boolean displayInRandomOrder, boolean allowPracticeMode, boolean correctImmediately) {
        this(0, name, description, category, displayOnSinglePage, displayInRandomOrder, allowPracticeMode, correctImmediately);
    }

    /**
     * Constructs a new Quiz with the given properties, including an ID.
     *
     * @param id                   the ID of the quiz
     * @param name                 the name of the quiz
     * @param description          the description of the quiz
     * @param category             the category of the quiz
     * @param displayOnSinglePage  whether to display all questions on a single page
     * @param displayInRandomOrder whether to display questions in random order
     * @param allowPracticeMode    whether to allow practice mode
     * @param correctImmediately   whether to correct answers immediately
     * @throws NullPointerException if name, description, or category is null
     */
    public Quiz(int id, String name, String description, QuizCategory category, boolean displayOnSinglePage,
                boolean displayInRandomOrder, boolean allowPracticeMode, boolean correctImmediately) {
        this.id = id;
        setName(name);
        setDescription(description);
        setCategory(category);
        this.displayOnSinglePage = displayOnSinglePage;
        this.displayInRandomOrder = displayInRandomOrder;
        this.allowPracticeMode = allowPracticeMode;
        this.correctImmediately = correctImmediately;
        this.questions = new ArrayList<>();
    }

    /**
     * Gets the ID of the quiz.
     *
     * @return the quiz ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the quiz.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the quiz.
     *
     * @return the quiz name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the quiz.
     *
     * @param name the name to set
     * @throws NullPointerException if name is null
     */
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, NAME_ERROR_MESSAGE);
    }

    /**
     * Gets the description of the quiz.
     *
     * @return the quiz description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the quiz.
     *
     * @param description the description to set
     * @throws NullPointerException if description is null
     */
    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, DESCRIPTION_ERROR_MESSAGE);
    }

    /**
     * Gets the category of the quiz.
     *
     * @return the quiz category
     */
    public QuizCategory getCategory() {
        return category;
    }

    /**
     * Sets the category of the quiz.
     *
     * @param category the category to set
     * @throws NullPointerException if category is null
     */
    public void setCategory(QuizCategory category) {
        this.category = Objects.requireNonNull(category, CATEGORY_ERROR_MESSAGE);
    }

    /**
     * Gets an unmodifiable list of questions in the quiz.
     *
     * @return an unmodifiable list of questions
     */
    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * Adds a question to the quiz.
     *
     * @param question the question to add
     * @throws NullPointerException if question is null
     */
    public void addQuestion(Question question) {
        this.questions.add(Objects.requireNonNull(question, QUESTION_ERROR_MESSAGE));
    }

    /**
     * Removes a question from the quiz.
     *
     * @param question the question to remove
     */
    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }

    /**
     * Checks if the quiz should be displayed on a single page.
     *
     * @return true if the quiz should be displayed on a single page, false otherwise
     */
    public boolean isDisplayOnSinglePage() {
        return displayOnSinglePage;
    }

    /**
     * Sets whether the quiz should be displayed on a single page.
     *
     * @param displayOnSinglePage true to display on a single page, false otherwise
     */
    public void setDisplayOnSinglePage(boolean displayOnSinglePage) {
        this.displayOnSinglePage = displayOnSinglePage;
    }

    /**
     * Checks if the questions should be displayed in random order.
     *
     * @return true if questions should be displayed in random order, false otherwise
     */
    public boolean isDisplayInRandomOrder() {
        return displayInRandomOrder;
    }

    /**
     * Sets whether the questions should be displayed in random order.
     *
     * @param displayInRandomOrder true to display in random order, false otherwise
     */
    public void setDisplayInRandomOrder(boolean displayInRandomOrder) {
        this.displayInRandomOrder = displayInRandomOrder;
    }

    /**
     * Checks if practice mode is allowed for this quiz.
     *
     * @return true if practice mode is allowed, false otherwise
     */
    public boolean isAllowPracticeMode() {
        return allowPracticeMode;
    }

    /**
     * Sets whether practice mode is allowed for this quiz.
     *
     * @param allowPracticeMode true to allow practice mode, false otherwise
     */
    public void setAllowPracticeMode(boolean allowPracticeMode) {
        this.allowPracticeMode = allowPracticeMode;
    }

    /**
     * Checks if answers should be corrected immediately.
     *
     * @return true if answers should be corrected immediately, false otherwise
     */
    public boolean isCorrectImmediately() {
        return correctImmediately;
    }

    /**
     * Sets whether answers should be corrected immediately.
     *
     * @param correctImmediately true to correct answers immediately, false otherwise
     */
    public void setCorrectImmediately(boolean correctImmediately) {
        this.correctImmediately = correctImmediately;
    }

    /**
     * Returns a string representation of the Quiz object.
     *
     * @return a string representation of the Quiz
     */
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", displayOnSinglePage=" + displayOnSinglePage +
                ", displayInRandomOrder=" + displayInRandomOrder +
                ", allowPracticeMode=" + allowPracticeMode +
                ", correctImmediately=" + correctImmediately +
                ", questionCount=" + questions.size() +
                '}';
    }

    /**
     * Gets the number of questions in the quiz.
     *
     * @return the number of questions
     */
    public int getQuestionCount() {
        return questions.size();
    }

    /**
     * Checks if the quiz is valid (has at least one question).
     * @return true if the quiz is valid, false otherwise
     */
    public boolean isValid() {
        return !questions.isEmpty();
    }

    /**
     * retrieves specific quiz's questions.
     * @param questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

/**
 * Enumeration of quiz categories.
 */
public enum QuizCategory {
    MATH, SCIENCE, HISTORY, LITERATURE, GENERAL_KNOWLEDGE
    // Add more categories as needed
}