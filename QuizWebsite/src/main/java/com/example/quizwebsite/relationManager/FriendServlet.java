package com.example.quizwebsite.relationManager;

import com.example.quizwebsite.userManager.User;
import com.example.quizwebsite.userManager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet({"/friends"})
public class FriendServlet extends HttpServlet {

    /**
     * Handles POST requests to unfriend someone or send a friend request.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     * @throws ServletException if the request for the POST could not be handled
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("method").equals("unfriend")) {
            doUnfriend(request, response);
            return;
        }
        if (request.getParameter("method").equals("sendRequest")) {
            doSendRequest(request, response);
        }
    }

    /**
     * Handles GET requests to retrieve the set of the user's friends.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        User user = (User) request.getSession().getAttribute("user");
        int id = user.getId();

        Set<User> friends = rm.getFriends(id);

        request.setAttribute("friends", friends);
        request.getRequestDispatcher("/viewFriends.jsp").forward(request, response);
    }

    /**
     * Sends a friend request to another user.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    private void doSendRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        String friendUsername = request.getParameter("friendUsername");
        User friend = um.getUserByUsername(friendUsername);
        rm.sendRequest(user.getId(), friend.getId());

        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
            public String getMethod() {
                return "GET";
            }
        };

        request.getRequestDispatcher("/users?friendUsername=" + friendUsername).forward(requestWrapper, response);

    }

    /**
     * Handles the request to unfriend a user.
     *
     * @param request the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     */
    protected void doUnfriend(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        String friendUsername = request.getParameter("friendUsername");
        User friend = um.getUserByUsername(friendUsername);

        rm.removeFriend(user.getId(), friend.getId());

        String cameFrom = request.getParameter("from");
        if (cameFrom.equals("friends")) {
            doGet(request, response);
        }
        if (cameFrom.equals("users")) {
            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
                public String getMethod() {
                    return "GET";
                }
            };
            request.getRequestDispatcher("/users?friendUsername=" + friendUsername).forward(requestWrapper, response);
        }
    }
}