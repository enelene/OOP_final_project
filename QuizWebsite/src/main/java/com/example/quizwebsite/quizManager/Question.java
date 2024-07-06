package com.example.quizwebsite.quizManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Question {
    private int id;
    private int quizId;
    private String text;
    private QuestionType type;
    private List<String> options;
    private List<Boolean> correctOptions;
    private String correctAnswer;

    public Question(int quizId, String text, QuestionType type) {
        this.quizId = quizId;
        setText(text);
        setType(type);
        this.options = new ArrayList<>();
        this.correctOptions = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getText() {
        return text;
    }

    public QuestionType getType() {
        return type;
    }

    public List<String> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public List<Boolean> getCorrectOptions() {
        return Collections.unmodifiableList(correctOptions);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Setters with validation
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be non-negative");
        }
        this.id = id;
    }

    public void setQuizId(int quizId) {
        if (quizId < 0) {
            throw new IllegalArgumentException("Quiz ID must be non-negative");
        }
        this.quizId = quizId;
    }

    public void setText(String text) {
        this.text = Objects.requireNonNull(text, "Question text cannot be null");
    }

    public void setType(QuestionType type) {
        this.type = Objects.requireNonNull(type, "Question type cannot be null");
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(Objects.requireNonNull(options, "Options list cannot be null"));
    }

    public void setCorrectOptions(List<Boolean> correctOptions) {
        this.correctOptions = new ArrayList<>(Objects.requireNonNull(correctOptions, "Correct options list cannot be null"));
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // Helper methods for managing options and correct answers
    public void addOption(String option, boolean isCorrect) {
        options.add(Objects.requireNonNull(option, "Option cannot be null"));
        correctOptions.add(isCorrect);
    }

    public void clearOptions() {
        options.clear();
        correctOptions.clear();
    }

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
}

