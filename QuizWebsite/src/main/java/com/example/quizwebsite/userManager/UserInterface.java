package com.example.quizwebsite.userManager;

public interface UserInterface {
    User addUser(User user, String password);
    boolean deleteUser(int id);

    User getUserById(int id);
    User getUserByUsername(String username);
    boolean userExists(String username);
    boolean validateUser(String username, String password);

    //    public userManager createAccount(String username, String password, boolean is_admin) might be changed like this
    boolean makeUserAdmin(int id);
}
