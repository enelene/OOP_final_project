package com.example.quizwebsite.userManager;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager implements UserInterface {
    private static BasicDataSource dataSource;

    public UserManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Takes user object with null id, adds it to database.
     * Returns user object with id filled in.
     * Returns null if username was already in use.
     * @param user
     * @param hashedPassword
     * @return
     */
    @Override
    public User addUser(User user, String hashedPassword) {
        if (userExists(user.getUsername())) {
            return null;
        }
        String sql = "INSERT INTO users (username, password, is_admin, cookie_key) VALUES (?, ?, ?, ?)";
        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setInt(3, user.isAdmin() ? 1 : 0);
            stmt.setString(4, user.getCookieKey());
            stmt.executeUpdate();
            return getUserByUsername(user.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * deletes user given the id.
     * returns true if user existed before the change.
     * @param id
     * @return
     */
    @Override
    public boolean deleteUser(int id) {
        //todo delete from friends
        int rowCount = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM `users` WHERE `id` = ?;");
            stmt.setInt(1, id);
            rowCount = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount > 0;
    }

    /**
     * Returns user with given username from database.
     * Returns null if no user found.
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `users` WHERE `username` = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"));
                user.setId(rs.getInt("id"));
                user.setCookieKey(rs.getString("cookie_key"));
                if(rs.getInt("is_admin") == 1) { user.makeAdmin();}
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns user with given id from database.
     * Returns null if no user found.
     * @param id
     * @return
     */
    @Override
    public User getUserById(int id) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `users` WHERE `id` = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"));
                user.setId(rs.getInt("id"));
                user.setCookieKey(rs.getString("cookie_key"));
                if(rs.getInt("is_admin") == 1) { user.makeAdmin();}
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean userExists(String username) {
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
    public boolean validateUser(String username, String password) {
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

    /**
     * makes user with given id an admin.
     * returns true if user exists.
     * @param id
     * @return
     */
    @Override
    public boolean makeUserAdmin(int id) {
        int rowCount = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE `user` SET `is_admin` = 1 WHERE `id` = ?");
            stmt.setInt(1, id);
            rowCount = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount > 0;
    }

}
