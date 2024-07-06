package com.example.quizwebsite.userManager;

public class User {
    private Integer id;
    private String username;
    private String password;
    private boolean isAdmin;
    private String cookieKey;



    public User(Integer id, String username, String password, boolean isAdmin, String cookieKey) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.cookieKey = cookieKey;
    }
    public User(String username,String password) {
        this(null, username, password, false, null);
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {return password;}
    public void setPassword(String password) { this.password = password;}
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
    public String getCookieKey() {return cookieKey;}
    public void setCookieKey(String key) { this.cookieKey = key;}

    //todo solve relation problem
    public void setCookieKey() {this.cookieKey = "";}
}
