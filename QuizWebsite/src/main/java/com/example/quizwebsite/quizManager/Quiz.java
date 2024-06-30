package com.example.quizwebsite.quizManager;

import java.util.List;

public class Quiz {
    private int id;
    private String name;
    private String description;
    private String category;
    private boolean displayOnSinglePage;
    private boolean displayInRandomOrder;
    private boolean allowPracticeMode;
    private boolean correctImmediately;
   // private List<Question> questions; // Assuming you have a Question class to represent quiz questions

    public Quiz(String name, String description, String category, boolean displayOnSinglePage,
                boolean displayInRandomOrder, boolean allowPracticeMode, boolean correctImmediately) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.displayOnSinglePage = displayOnSinglePage;
        this.displayInRandomOrder = displayInRandomOrder;
        this.allowPracticeMode = allowPracticeMode;
        this.correctImmediately = correctImmediately;
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
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

   // public List<Question> getQuestions() {
   //     return questions;
   // }

   // public void setQuestions(List<Question> questions) {
    //    this.questions = questions;
    //}
}
