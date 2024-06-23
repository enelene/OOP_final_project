//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.userManager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    public CreateAccountServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountManager accountManager = (AccountManager)this.getServletContext().getAttribute("accountManager");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (accountManager.createAccount(username, password)) {
            request.getRequestDispatcher("/welcome.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/nameInUse.jsp").forward(request, response);
        }

    }
}
