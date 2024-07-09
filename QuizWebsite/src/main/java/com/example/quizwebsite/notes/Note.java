package com.example.quizwebsite.notes;

import java.util.Date;

public class Note {
    private int id;
    private String senderUsername;
    private String recipientUsername;
    private String message;
    private Date timestamp;
    private boolean isRead;

    // Default constructor
    public Note() {
    }

    // Constructor without id (for creating new notes)
    public Note(String senderUsername, String recipientUsername, String message, Date timestamp) {
        this.senderUsername = senderUsername;
        this.recipientUsername = recipientUsername;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = false;
    }

    // Full constructor
    public Note(int id, String senderUsername, String recipientUsername, String message, Date timestamp, boolean isRead) {
        this.id = id;
        this.senderUsername = senderUsername;
        this.recipientUsername = recipientUsername;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", senderUsername='" + senderUsername + '\'' +
                ", recipientUsername='" + recipientUsername + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                '}';
    }
}