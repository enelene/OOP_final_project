package com.example.quizwebsite.userManager;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;

public class MySQLDb implements DB {
    private BasicDataSource dataSource;
    public MySQLDb (BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public boolean accountExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
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

    @Override
    public boolean validatePassword(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password").equals(password);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    @Override
    public boolean createAccount(String username, String password) {
        if (accountExists(username)) {
            return false;
        }
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
