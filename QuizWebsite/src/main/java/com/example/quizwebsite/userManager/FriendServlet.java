package com.example.quizwebsite.userManager;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/friends"})
public class FriendServlet extends HttpServlet {
    //Accept request. Needs from_id and to_username as parameters.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        // aq ubralod responsshi iwereba is info rac shemdegshi useris friendebis pageis dasagenerireblad dagvchirdeba
        response.getWriter().print("checking friends");
        for (User user : rm.getFriends(id)){
            response.getWriter().print(user.getUsername());
        }
        //todo redirect to jsp
    }
}