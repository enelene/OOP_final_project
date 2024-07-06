package com.example.quizwebsite.authentication;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet  extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Handle logout action
        //request.getSession().invalidate();
        request.getSession().removeAttribute("user");
        Cookie c = new Cookie("user_key", "");
        c.setMaxAge(0);
        c.setHttpOnly(true);
        c.setPath("/");
        response.addCookie(c);
        response.sendRedirect("index.jsp");
    }
}
