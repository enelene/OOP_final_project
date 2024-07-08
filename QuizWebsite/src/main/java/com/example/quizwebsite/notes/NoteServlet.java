package com.example.quizwebsite.notes;

import com.example.quizwebsite.userManager.User;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/NoteServlet")
public class NoteServlet extends HttpServlet {
    private NoteDAO noteDAO;
    private NoteService noteService;

    @Override
    public void init() throws ServletException {
        super.init();
        BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource is not initialized in the ServletContext");
        }
        this.noteDAO = new NoteDAO(dataSource);
        this.noteService = new NoteService(noteDAO);
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "viewNotes":
                    int page = getIntParameter(request, "page", 1);
                    int pageSize = getIntParameter(request, "pageSize", 10);
                    List<Note> notes = noteService.getNotesForUser(user.getUsername(), page, pageSize);
                    request.setAttribute("notes", notes);
                    request.getRequestDispatcher("notes.jsp").forward(request, response);
                    break;
                case "markAsRead":
                    int noteId = getIntParameter(request, "noteId", -1);
                    noteService.markNoteAsRead(noteId, user.getUsername());
                    response.sendRedirect("NoteServlet?action=viewNotes");
                    break;
                case "deleteNote":
                    noteId = getIntParameter(request, "noteId", -1);
                    noteService.deleteNote(noteId, user.getUsername());
                    response.sendRedirect("NoteServlet?action=viewNotes");
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request");
        }
    }

    private int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
        String paramValue = request.getParameter(paramName);
        if (paramValue == null || paramValue.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
