package com.example.quizwebsite.userManager;

public class User {
    private Integer id;
    private String username;
    private boolean isAdmin;
    private String cookieKey;

    public User(Integer id, String username, boolean isAdmin, String cookieKey) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
        this.cookieKey = cookieKey;
    }

    public User(String username) {
        this(null, username, false, null);
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public void makeAdmin(){
        isAdmin = true;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCookieKey() {
        return cookieKey;
    }
    public void setCookieKey(String cookieKey) {
        this.cookieKey = cookieKey;
    }
}
