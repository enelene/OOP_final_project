package com.example.quizwebsite.notes;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    private BasicDataSource dataSource;

    public NoteDAO(BasicDataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource cannot be null");
        }
        this.dataSource = dataSource;
    }

    public boolean saveNote(Note note) {
        String sql = "INSERT INTO notes (sender_username, recipient_username, message, timestamp, is_read) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, note.getSenderUsername());
            stmt.setString(2, note.getRecipientUsername());
            stmt.setString(3, note.getMessage());
            stmt.setTimestamp(4, new Timestamp(note.getTimestamp().getTime()));
            stmt.setBoolean(5, note.isRead());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Note> getNotesForUser(String username, int page, int pageSize) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE recipient_username = ? ORDER BY timestamp DESC LIMIT ? OFFSET ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Note note = new Note(
                            rs.getInt("id"),
                            rs.getString("sender_username"),
                            rs.getString("recipient_username"),
                            rs.getString("message"),
                            rs.getTimestamp("timestamp"),
                            rs.getBoolean("is_read")
                    );
                    notes.add(note);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notes;
    }

    public boolean markNoteAsRead(int noteId) {
        String sql = "UPDATE notes SET is_read = true WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, noteId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteNote(int noteId) {
        String sql = "DELETE FROM notes WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, noteId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isNoteOwnedByUser(int noteId, String username) {
        String sql = "SELECT COUNT(*) FROM notes WHERE id = ? AND recipient_username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, noteId);
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}