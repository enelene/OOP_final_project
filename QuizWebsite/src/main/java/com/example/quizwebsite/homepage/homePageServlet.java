package com.example.quizwebsite.homepage;
import com.example.quizwebsite.userManager.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/HomepageServlet")
public class homePageServlet extends HttpServlet {

    public homePageServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the action parameter from the request
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        // Check which action to perform
        if (action == null) {
            // Default action if no action parameter is provided
            request.getRequestDispatcher("/homepage/home.jsp").forward(request, response);
        } else {
            switch (action) {
                case "logout":
                    // Handle logout action
                    request.getSession().invalidate();
                    response.sendRedirect("index.jsp");
                    break;
                case "home":
                    // Handle navigation to home page
                    request.getRequestDispatcher("/homepage/home.jsp").forward(request, response);
                    break;
                case "createQuiz":
                    // Handle navigation to create quiz page
                    request.getRequestDispatcher("/homepage/createQuiz.jsp").forward(request, response);
                    break;
                case "takeQuiz":
                    // Handle navigation to take quiz page
                    request.getRequestDispatcher("/homepage/takeQuiz.jsp").forward(request, response);
                    break;
                case "profile":
                    // Handle navigation to profile page
                    request.getRequestDispatcher("/homepage/userPage.jsp").forward(request, response);
                    break;
                default:
                    // Default action if the provided action is not recognized
                    request.getRequestDispatcher("/homepage/home.jsp").forward(request, response);
                    break;
            }
        }
    }
}
