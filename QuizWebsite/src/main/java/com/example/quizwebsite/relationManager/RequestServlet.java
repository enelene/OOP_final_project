package com.example.quizwebsite.relationManager;

import com.example.quizwebsite.userManager.User;
import com.example.quizwebsite.userManager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/requests"})
public class RequestServlet extends HttpServlet {
    //Sends request. Needs from_id and to_username as parameters.

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        if(id == to_id) {
            //cant send request to urself
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        rm.sendRequest(id, to_id);
        //todo redirect to somewhere
        response.getWriter().print("request sent");
    }

    //get all users that sent particular user requests.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        response.getWriter().print("checking requests");
        for (User user : rm.getRequests(id)){
            response.getWriter().print(user.getUsername());
        }
        //todo redirect to jsp
    }
}
