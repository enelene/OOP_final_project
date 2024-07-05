package com.example.quizwebsite.authentication;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// todo - no usages yet 
@WebServlet("/LogoutServlet")
public class LogoutServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("currentUser");

        Cookie c = new Cookie("user_key", "");
        c.setMaxAge(0);
        c.setHttpOnly(true);
        c.setPath("/");
        response.addCookie(c);
        response.sendRedirect("../");
    }
}
