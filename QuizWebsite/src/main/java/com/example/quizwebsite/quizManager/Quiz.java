package com.example.quizwebsite.quizManager;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Quiz {
    private int id;
    private String name;
    private String description;
    private QuizCategory category;
    private boolean displayOnSinglePage;
    private boolean displayInRandomOrder;
    private boolean allowPracticeMode;
    private boolean correctImmediately;
    private List<Question> questions;

    public Quiz(String name, String description, QuizCategory category, boolean displayOnSinglePage,
                boolean displayInRandomOrder, boolean allowPracticeMode, boolean correctImmediately) {
        this(0, name, description, category, displayOnSinglePage, displayInRandomOrder, allowPracticeMode, correctImmediately);
    }

    public Quiz(int id, String name, String description, QuizCategory category, boolean displayOnSinglePage,
                boolean displayInRandomOrder, boolean allowPracticeMode, boolean correctImmediately) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.category = Objects.requireNonNull(category, "Category cannot be null");
        this.displayOnSinglePage = displayOnSinglePage;
        this.displayInRandomOrder = displayInRandomOrder;
        this.allowPracticeMode = allowPracticeMode;
        this.correctImmediately = correctImmediately;
        this.questions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "Description cannot be null");
    }

    public QuizCategory getCategory() {  // Changed return type to QuizCategory
        return category;
    }

    public void setCategory(QuizCategory category) {  // Changed parameter type to QuizCategory
        this.category = Objects.requireNonNull(category, "Category cannot be null");
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    public void addQuestion(Question question) {
        this.questions.add(Objects.requireNonNull(question, "Question cannot be null"));
    }

    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }

    public boolean isDisplayOnSinglePage() {
        return displayOnSinglePage;
    }

    public void setDisplayOnSinglePage(boolean displayOnSinglePage) {
        this.displayOnSinglePage = displayOnSinglePage;
    }

    public boolean isDisplayInRandomOrder() {
        return displayInRandomOrder;
    }

    public void setDisplayInRandomOrder(boolean displayInRandomOrder) {
        this.displayInRandomOrder = displayInRandomOrder;
    }

    public boolean isAllowPracticeMode() {
        return allowPracticeMode;
    }

    public void setAllowPracticeMode(boolean allowPracticeMode) {
        this.allowPracticeMode = allowPracticeMode;
    }

    public boolean isCorrectImmediately() {
        return correctImmediately;
    }

    public void setCorrectImmediately(boolean correctImmediately) {
        this.correctImmediately = correctImmediately;
    }

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
    // public List<Question> getQuestions() {
    //     return questions;
    // }

    // public void setQuestions(List<Question> questions) {
    //    this.questions = questions;
    //}
}

enum QuizCategory {
    MATH, SCIENCE, HISTORY, LITERATURE, GENERAL_KNOWLEDGE
    // Add more categories as needed
}