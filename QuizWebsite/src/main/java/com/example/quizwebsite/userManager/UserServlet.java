//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.userManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@WebServlet({"/users"})
public class UserServlet extends HttpServlet {

    // Endpoint for viewing user's page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("user");
        if(username == "" || username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User user = um.getUserByUsername(username);
        if(user == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("viewingUser", user);
        request.getRequestDispatcher("/viewUser.jsp").forward(request, response);
    }

    // Endpoint for creating a new user.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hashedPassword;
        try {
            hashedPassword = HashingManager.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Error hashing password", e);
        }

        // ignore cookies part yet
        // false cause we dont have administrator configuration yet
        //todo this is a temporary fix for unique cookies
        Random r = new Random();
        User newUser = new User(null, username,false, "test" + r.nextInt());
        newUser = um.addUser(newUser, hashedPassword);
        if (newUser != null) {
            request.getSession().setAttribute("user", newUser);
            request.getRequestDispatcher("/homepage/home.jsp").forward(request, response);
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
