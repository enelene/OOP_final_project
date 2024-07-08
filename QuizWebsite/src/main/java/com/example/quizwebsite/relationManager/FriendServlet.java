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
    //Accept request. Needs from_id and to_username as parameters.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParameter("method").equals("delete")){
            doDelete(request, response);
            return;
        }

        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        String senderUsername = request.getParameter("senderUsername");
        User sender = um.getUserByUsername(senderUsername);
        rm.addFriend(user.getId(), sender.getId());
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
            public String getMethod(){
                return "GET";
            }
        };
        request.getRequestDispatcher("/requests").forward(requestWrapper, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        int id = user.getId();
        Set<User> friends = rm.getFriends(id);
        request.setAttribute("friends", friends);
        request.getRequestDispatcher("/viewFriends.jsp").forward(request, response);
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        String friendName = request.getParameter("friendName");
        User friend = um.getUserByUsername(friendName);
        rm.removeFriend(user.getId(), friend.getId());
        response.sendRedirect(request.getContextPath() +"/friends");
    }
}