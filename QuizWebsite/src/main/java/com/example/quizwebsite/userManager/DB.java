package com.example.quizwebsite.userManager;

public interface DB {
    boolean accountExists(String username);
    boolean validatePassword(String username, String password);
    boolean createAccount(String username, String password);

}
