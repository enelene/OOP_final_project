package com.example.quizwebsite.quizManager;

import com.example.quizwebsite.userManager.User;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/aboutQuiz")
public class QuizSummaryServlet extends HttpServlet {

    private QuizManager quizManager;
    /**
     * Initializes the servlet by setting up the QuizManager with the data source.
     * @throws ServletException if the data source is not initialized in the ServletContext
     */
    @Override
    public void init() throws ServletException {
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource is not initialized in the ServletContext");
        }
        this.quizManager = new QuizManager(dataSource);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int quizId = Integer.parseInt(req.getParameter("quizId"));
        String board = req.getParameter("board");
        List<Attempt> attempts = null;
        if (board == null || board.equals("highscore")) {
            attempts = quizManager.getAttemptsByQuizId(quizId, true, false, false, -1, -1);
        } else if (board.equals("recent")) {
            attempts = quizManager.getAttemptsByQuizId(quizId, false, true, false, 24, -1);
        } else if (board.equals("topToday")) {
            attempts = quizManager.getAttemptsByQuizId(quizId, true, false, false, 24, -1);
        } else if (board.equals("myAttempts")) {
            attempts = quizManager.getAttemptsByQuizId(quizId, true, false, false, -1, ((User)req.getSession().getAttribute("user")).getId());
        }
        req.setAttribute("attempts", attempts);
        try {
            req.setAttribute("quiz", quizManager.getQuizById(quizId));
            req.setAttribute("averageScore", quizManager.getAverageScoreOfQuiz(quizId));
            req.setAttribute("attemptNum", quizManager.getAttemptCountOfQuiz(quizId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/quizSummary.jsp").forward(req, resp);
    }
}
