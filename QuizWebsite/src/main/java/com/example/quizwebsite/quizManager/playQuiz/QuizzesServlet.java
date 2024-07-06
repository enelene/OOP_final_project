package com.example.quizwebsite.quizManager.playQuiz;
import com.example.quizwebsite.quizManager.Quiz;
import com.example.quizwebsite.quizManager.QuizManager;
import com.example.quizwebsite.userManager.RelationManager;
import com.example.quizwebsite.userManager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/QuizzesServlet")
public class QuizzesServlet extends HttpServlet {
    private QuizManager quizManager;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the list of quizzes

        RelationManager rm = (RelationManager) getServletContext().getAttribute("relationManager");
        UserManager um = (UserManager) getServletContext().getAttribute("userManager");
        //above two might not be needed
        QuizManager qm = (QuizManager) getServletContext().getAttribute("quizManager");
        List<Quiz> quizzes = quizManager.getAllQuizzes();

        // Add quizzes to the request attributes
        request.setAttribute("quizzes", quizzes);

        // Forward to the playQuiz.jsp page
        request.getRequestDispatcher("/playQuiz.jsp").forward(request, response);
    }
}
