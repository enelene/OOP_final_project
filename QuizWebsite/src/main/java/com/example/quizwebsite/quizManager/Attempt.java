package com.example.quizwebsite.quizManager;

import java.time.LocalDateTime;

public class Attempt {
    private int id;
    private int userId;
    private int quizId;
    private int score;
    private LocalDateTime time;
    public Attempt(int userId, int quizId, int score, LocalDateTime time) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "id=" + id +
                ", userId=" + userId +
                ", quizId=" + quizId +
                ", score=" + score +
                ", time=" + time +
                '}';
    }
}
