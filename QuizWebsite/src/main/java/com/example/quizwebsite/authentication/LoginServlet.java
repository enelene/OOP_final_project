//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.authentication;

import com.example.quizwebsite.userManager.HashingManager;
import com.example.quizwebsite.userManager.User;
import com.example.quizwebsite.userManager.UserManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import static com.example.quizwebsite.userManager.HashingManager.generateHash;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            password = HashingManager.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        boolean rememberMe = request.getParameter("rememberMe") != null;

        // Input validation
        if (!um.isValidInput(username, password)) {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        try {
            User user = um.getUserByUsername(username);
            if (user != null && um.validateUser(username, password)) {
                request.getSession().setAttribute("user", user);
                Cookie c;
                if (rememberMe) {
                    String cookie_key = um.setCookieKey(user.getId());
                    user.setCookieKey(cookie_key);
                    c = new Cookie("user_key", user.getCookieKey());
                    c.setMaxAge(365 * 86400);
                }
                else {
                    c = new Cookie("user_key", "");
                    c.setMaxAge(0);
                }
                c.setHttpOnly(true);
                c.setPath("/");
                response.addCookie(c);
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
}
