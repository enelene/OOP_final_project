//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.userManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 4;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userManager um = (userManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Input validation
        if (!isValidInput(username, password)) {
            request.setAttribute("error", "Invalid input. Username must be 3-20 characters and password at least 6 characters.");
            request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
            return;
        }

        try {
            String hashedPassword = HashingManager.generateHash(password);
            User newUser = new User(username, false, hashedPassword, "test");

            if (um.createAccount(newUser)) {
                request.getSession().setAttribute("user", newUser);
                request.setAttribute("message", "Account created successfully. Please log in.");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Username already in use");
                request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error creating account: " + e.getMessage());
            request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
        }
    }

    private boolean isValidInput(String username, String password) {
        return username != null && password != null &&
                username.length() >= MIN_USERNAME_LENGTH && username.length() <= MAX_USERNAME_LENGTH &&
                password.length() >= MIN_PASSWORD_LENGTH;
    }
}
