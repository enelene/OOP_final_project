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

@WebServlet({"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    public LoginServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        userManager userManager = (userManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            password = generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Error hashing password", e);
        }
        try {
            User user = userManager.getUser(username);
            if (userManager.validatePassword(user,password)) {
                request.getSession().setAttribute("user", user);
                //request.getRequestDispatcher("/welcome.jsp").forward(request, response);
                request.getRequestDispatcher("/homepage/home.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/tryagain.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error validating password", e);
        }

    }

}
