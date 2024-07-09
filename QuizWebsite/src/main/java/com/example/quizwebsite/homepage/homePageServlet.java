package com.example.quizwebsite.homepage;

import com.example.quizwebsite.quizManager.Quiz;
import com.example.quizwebsite.quizManager.QuizManager;
import com.example.quizwebsite.userManager.User;
import com.example.quizwebsite.notes.Note;
import com.example.quizwebsite.notes.NoteService;
import com.example.quizwebsite.notes.NoteDAO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/HomepageServlet")
public class homePageServlet extends HttpServlet {
    private NoteService noteService;
    private QuizManager quizManager;

    public homePageServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource is not initialized in the ServletContext");
        }
        NoteDAO noteDAO = new NoteDAO(dataSource);
        this.noteService = new NoteService(noteDAO);
        this.quizManager = new QuizManager(dataSource);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("sendNote".equals(action)) {
            String message = request.getParameter("message");
            String recipient = request.getParameter("recipient");

            try {
                noteService.sendNote(user.getUsername(), recipient, message);
                request.getSession().setAttribute("noteMessage", "Note sent successfully!");
            } catch (IllegalArgumentException e) {
                request.getSession().setAttribute("noteMessage", e.getMessage());
            } catch (Exception e) {
                request.getSession().setAttribute("noteMessage", "Failed to send note. Please try again.");
            }
        }

        response.sendRedirect("HomepageServlet?action=home");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the action parameter from the request
        HttpSession session = request.getSession();
        //QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quizManager");
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (action == null || action.equals("home")) {
            try {
                List<Note> recentNotes = noteService.getNotesForUser(user.getUsername(), 1, 5);
                System.out.println("Recent notes for " + user.getUsername() + ": " + recentNotes); // Debug print
                request.setAttribute("recentNotes", recentNotes);

                List<Quiz> userQuizzes = quizManager.getQuizzesByUser(user.getUsername());
                System.out.println("Quizzes for user" + userQuizzes);
                request.setAttribute("userQuizzes", userQuizzes);

                // Add this line to include any note sending messages
                request.setAttribute("noteMessage", session.getAttribute("noteMessage"));
                session.removeAttribute("noteMessage");
            } catch (Exception e) {
                System.out.println("Error fetching recent notes: " + e.getMessage()); // Debug print
                e.printStackTrace();
            }
            request.getRequestDispatcher("/homepage/home.jsp").forward(request, response);
        } else {
            switch (action) {
                case "logout":
                    response.sendRedirect(request.getContextPath() + "/LogoutServlet");
                    break;
                case "home":
                    // Handle navigation to home page
                    request.getRequestDispatcher("/homepage/home.jsp").forward(request, response);
                    break;
                case "createQuiz":
                    System.out.println("Forwarding to create quiz page");
                    request.getRequestDispatcher("/create/createQuiz.jsp").forward(request, response);
                    break;
                case "takeQuiz":
                    // Handle navigation to take quiz page
                    request.getRequestDispatcher( "/QuizzesServlet").forward(request, response);
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