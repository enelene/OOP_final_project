package com.example.quizwebsite.relationManager;

import com.example.quizwebsite.userManager.User;
import com.example.quizwebsite.userManager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet({"/friends"})
public class FriendServlet extends HttpServlet {
    //Accept request. Needs from_id and to_username as parameters.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParameter("method").equals("delete")){
            System.out.println("Inside delete");
            doDelete(request, response);
            return;
        }

        System.out.println("mevedit?");
        //todo change this method so u send request from the logged in account instead of passing id as a parameter
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        int id;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch(NumberFormatException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String user = request.getParameter("user");
        if(user == "" || user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int to_id = um.getUserByUsername(user).getId();
        response.getWriter().print(id + " and " + to_id + " became friends!");
        if(id == to_id) {
            //cant send request to urself
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!rm.addFriend(id, to_id)){
            response.getWriter().print("Can't accept nonexistent request.");
        }
        //todo redirect to somewhere
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