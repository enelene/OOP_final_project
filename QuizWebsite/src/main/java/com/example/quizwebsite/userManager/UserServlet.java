//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.userManager;

import com.example.quizwebsite.relationManager.RelationManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet({"/users"})
public class UserServlet extends HttpServlet {
    /**
     * Handles GET requests and redirects the user to the other user's page.
     *
     * <p>This method forwards the request and response to the "/viewOtherUser.jsp" page,
     * allowing the user to view the profile or details of another user.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException      if an input or output error occurs while handling the request
     * @throws ServletException if the request could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        String friendUsername = request.getParameter("friendUsername");
        User friend = um.getUserByUsername(friendUsername);
        if (friend == null) {
            request.getRequestDispatcher("/usernameDoesNotExist.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/viewOtherUser.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST requests for creating a new user.
     *
     * <p>This method processes the form data to create a new user.
     * It retrieves the username and password from the request,
     * hashes the password,
     * validates the input,
     * attempts to add the new user to the system.
     * If the user is successfully created, it redirects to the index page.
     * If the username is already in use or input is invalid, it redirects to an error page.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while handling the request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        com.example.quizwebsite.userManager.UserManager um = (com.example.quizwebsite.userManager.UserManager) getServletContext().getAttribute("userManager");
        String username = request.getParameter("username");
        String passwordBeforeHash = request.getParameter("password");
        String password;
        try {
            password = HashingManager.generateHash(passwordBeforeHash);
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Error hashing password", e);
        }
        //prohibited actions
        if (!um.isValidInput(username, passwordBeforeHash)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User newUser = new User(null, username, password, false, null);
        newUser = um.addUser(newUser);
        if (newUser != null) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/nameInUse.jsp").forward(request, response);
    }

    /**
     * Handles DELETE requests for deleting a user account.
     *
     * <p>This method processes the request to delete a user account. It retrieves the user ID from the
     * request parameters, validates it, and then attempts to delete the user account from the system.
     * If the user ID is invalid, it returns a bad request status. Upon successful deletion, it redirects
     * to a confirmation page.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while handling the request
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        int id = user.getId();
        um.deleteUser(id);
        response.sendRedirect(request.getContextPath() + "/homepage/userDeleted.jsp?id=" + id);
    }
}
