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

import static com.example.quizwebsite.userManager.HashingManager.generateHash;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userManager userManager = (userManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Input validation
        if (!isValidInput(username, password)) {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try {
            String hashedPassword = HashingManager.generateHash(password);
            User user = userManager.getUser(username);

            if (user != null && userManager.validatePassword(user, hashedPassword)) {
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("/homepage/home.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error during login: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private boolean isValidInput(String username, String password) {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }
}
