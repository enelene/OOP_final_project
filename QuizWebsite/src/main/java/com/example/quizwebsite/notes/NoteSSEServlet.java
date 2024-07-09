package com.example.quizwebsite.notes;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/NoteSSEServlet")
public class NoteSSEServlet extends HttpServlet {
    private static final ConcurrentHashMap<String, PrintWriter> clients = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");

        String username = request.getParameter("username");
        PrintWriter writer = response.getWriter();
        clients.put(username, writer);

        try {
            while (true) {
                writer.write("data: ping\n\n");
                writer.flush();
                Thread.sleep(30000); // Send a ping every 30 seconds to keep the connection alive
            }
        } catch (InterruptedException e) {
            // Handle exception
        } finally {
            clients.remove(username);
        }
    }

    public static void sendNoteToUser(String username, String message) {
        PrintWriter writer = clients.get(username);
        if (writer != null) {
            writer.write("data: " + message + "\n\n");
            writer.flush();
        }
    }
}
