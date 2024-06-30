package com.example.quizwebsite.userManager;

public interface userInterface {
    boolean accountExists(User user);
    boolean validatePassword(User user,String password);
    //    public userManager createAccount(String username, String password, boolean is_admin) might be changed like this
    boolean createAccount(User user);
    void makeUserAdmin(int user_id);

}