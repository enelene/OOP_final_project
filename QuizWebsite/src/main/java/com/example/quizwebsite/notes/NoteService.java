package com.example.quizwebsite.notes;

import java.util.Date;
import java.util.List;

public class NoteService {
    private NoteDAO noteDAO;

    public NoteService(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    public void sendNote(String sender, String recipient, String message) {
        if (sender == null || sender.isEmpty() || recipient == null || recipient.isEmpty() || message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Sender, recipient, and message cannot be empty");
        }

        Note note = new Note(sender, recipient, message, new Date());
        boolean success = noteDAO.saveNote(note);

        if (success) {
            NoteSSEServlet.sendNoteToUser(recipient, String.format("New note from %s: %s", sender, message));
        } else {
            throw new RuntimeException("Failed to save note");
        }
    }

    public List<Note> getNotesForUser(String username, int page, int pageSize) {
        return noteDAO.getNotesForUser(username, page, pageSize);
    }

    public void markNoteAsRead(int noteId, String username) {
        if (!isNoteOwnedByUser(noteId, username)) {
            throw new IllegalArgumentException("You don't have permission to access this note.");
        }
        noteDAO.markNoteAsRead(noteId);
    }

    public void deleteNote(int noteId, String username) {
        if (!isNoteOwnedByUser(noteId, username)) {
            throw new IllegalArgumentException("You don't have permission to delete this note.");
        }
        noteDAO.deleteNote(noteId);
    }

    private boolean isNoteOwnedByUser(int noteId, String username) {
        // Implement this method to check if the note belongs to the user
        // This is a placeholder and should be replaced with actual logic
        return true;
    }
}