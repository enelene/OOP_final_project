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

@WebServlet({"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userManager um = (userManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hashedPassword;
        try {
            hashedPassword = HashingManager.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Error hashing password", e);
        }

        // ignore cookies part yet
        User newUser = new User(username,false, hashedPassword, "test");
        try {
            // false cause we dont have administrator configuration yet
            // todo
            if (um.createAccount(newUser)) {
                request.getSession().setAttribute("user", newUser);
                request.getRequestDispatcher("/welcome.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/nameInUse.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error creating account", e);
        }

    }
}
