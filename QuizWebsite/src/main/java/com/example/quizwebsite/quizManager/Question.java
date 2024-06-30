package com.example.quizwebsite.quizManager;

import java.util.List;

public class Question {
    private int id;
    private int quizId;
    private String text;
    private String type;
    private List<String> options;
    private List<Boolean> correctOptions;
    private String correctAnswer;

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

    public String getType() {
        return type;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<Boolean> getCorrectOptions() {
        return correctOptions;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectOptions(List<Boolean> correctOptions) {
        this.correctOptions = correctOptions;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // You might want to add a toString() method for debugging purposes
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", options=" + options +
                ", correctOptions=" + correctOptions +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}