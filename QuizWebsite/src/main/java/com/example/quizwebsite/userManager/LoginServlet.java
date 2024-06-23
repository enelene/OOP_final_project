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

@WebServlet({"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    public LoginServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountManager accountManager = (AccountManager)this.getServletContext().getAttribute("accountManager");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            String hashedPassword = HashingManager.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if (accountManager.validatePassword(username, password)) {
            request.getRequestDispatcher("/welcome.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/tryagain.jsp").forward(request, response);
        }

    }
}
