//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.authentication;

import com.example.quizwebsite.userManager.HashingManager;
import com.example.quizwebsite.userManager.User;
import com.example.quizwebsite.userManager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@WebServlet({"/users"})
public class CreateAccountServlet extends HttpServlet {



    // Endpoint for viewing user's page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        Integer friendId = Integer.parseInt(request.getParameter("friendId"));
        request.getRequestDispatcher("/viewOtherUser.jsp").forward(request, response);
    }

    // Endpoint for creating a new user.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("username");
        String passwordBeforeHash = request.getParameter("password");
        String password;
        try {
            password = HashingManager.generateHash(passwordBeforeHash);
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Error hashing password", e);
        }
        //prohibited actions
        if(!um.isValidInput(username,passwordBeforeHash)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // here we don't get user permission to set cookie key , it will be only available on login page
        //todo add administrator page creating option
        User newUser = new User(null, username, password,false, null);
        newUser = um.addUser(newUser);
        if (newUser != null) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/nameInUse.jsp").forward(request, response);
    }

    //Endpoint for deleting an account.
    // not needed until we add administrator privileges
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo check if you are logged in as the user you are trying to delete.
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        int id;
        try{
            id = Integer.parseInt(request.getParameter("user"));
        } catch(NumberFormatException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        um.deleteUser(id);
        response.sendRedirect(request.getContextPath() + "/homepage/userDeleted.jsp?id=" + id);
    }

}
