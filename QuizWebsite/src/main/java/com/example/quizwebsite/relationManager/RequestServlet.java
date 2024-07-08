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
    //Sends request. Needs from_id and to_username as parameters.

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParameter("method").equals("delete")){
            doDelete(request, response);
            return;
        }
        // aqac methodad rame unda hqondes jsps rom null ar gamoyves shemtxvevit
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();

//        int to_id = um.getUserByUsername(user).getId();
//        rm.sendRequest(id, to_id);
        //todo redirect to somewhere
        response.getWriter().print("request sent");
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        User user = (User) request.getSession().getAttribute("user");
        String senderUsername = request.getParameter("senderUsername");
        User sender = um.getUserByUsername(senderUsername);
        rm.deleteRequest(user.getId(), sender.getId());

        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
            public String getMethod(){
                return "GET";
            }
        };
        request.getRequestDispatcher("/requests").forward(requestWrapper, response);
    }
}
