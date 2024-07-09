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

@WebServlet({"/requests"})
public class RequestServlet extends HttpServlet {

    // Responds to a request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParameter("method").equals("decline")){
            doDecline(request, response);
            return;
        }
        if(request.getParameter("method").equals("accept")){
            doAccept(request, response);
        }
    }

    //get all users that sent particular user requests.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        int id = user.getId();
        Set<User> requests = rm.getRequests(id);
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/viewRequests.jsp").forward(request, response);
    }
    // deletes a request sent by sender to the user
    protected void doDecline(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        Integer friendId = Integer.parseInt(request.getParameter("friendId"));
        String friendUsername = um.getUserById(friendId).getUsername();
        rm.deleteRequest(user.getId(), friendId);

        String cameFrom = request.getParameter("from");
        if (cameFrom.equals("requests")) {
            doGet(request, response);
        }
        if (cameFrom.equals("users")) {
            request.getRequestDispatcher("/users?searchUsername=" + friendUsername).forward(request, response);
        }
    }
    protected void doAccept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        Integer friendId = Integer.parseInt(request.getParameter("friendId"));

        rm.addFriend(user.getId(), friendId);

        String cameFrom = request.getParameter("from");
        if (cameFrom.equals("requests")) {
            doGet(request, response);
        }
        if (cameFrom.equals("users")) {
            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
                public String getMethod(){
                    return "GET";
                }
            };
            request.getRequestDispatcher("/users?friendId=" + friendId).forward(requestWrapper, response);
        }
    }
}
