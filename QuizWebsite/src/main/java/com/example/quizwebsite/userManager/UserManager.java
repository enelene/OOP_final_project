package com.example.quizwebsite.userManager;

import org.apache.commons.dbcp2.BasicDataSource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//public class UserManager implements UserInterface {
public class UserManager {
    private static BasicDataSource dataSource;

    public UserManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Takes user object with null id, adds it to database.
     * Returns user object with id filled in.
     * Returns null if username was already in use.
     * @param user
     * @return
     */

    public static User addUser(User user) {
        if (userExists(user.getUsername())) {
            return null;
        }
        String sql = "INSERT INTO users (username, password, is_admin, cookie_key) VALUES (?, ?, ?, ?)";
        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
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
    public static boolean deleteUser(int id) {
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
    public static User getUserByUsername(String username) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `users` WHERE `username` = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boolean isAdmin = false;
                if (rs.getInt("is_admin") == 1) isAdmin = true;
                User user = new User(rs.getInt("id"),rs.getString("username"), rs.getString("password"),isAdmin,rs.getString("cookie_Key"));
                if (isAdmin) user.makeAdmin();
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
    public User getUserById(int id) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `users` WHERE `id` = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"),rs.getString("password"));
                user.setId(rs.getInt("id"));
                //todo
                user.setCookieKey();
                //user.setCookieKey(rs.getString("cookie_key"));
                if(rs.getInt("is_admin") == 1) { user.makeAdmin();}
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean userExists(String username) {
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

    public boolean isValidInput(String username, String password) {
        return username != "" && username != null && password != null && password != "";
    }

    /**
     * makes user with given id an admin.
     * returns true if user exists.
     * @param id
     * @return
     */
    public boolean makeUserAdmin(int id) {
        int rowCount = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE `users` SET `is_admin` = 1 WHERE `id` = ?");
            stmt.setInt(1, id);
            rowCount = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount > 0;
    }

    /**
     * generates string with given id for cookie usage .
     * @param id
     * @return
     */

    public String setCookieKey(int id) {
        String key = new BigInteger(140, new SecureRandom()).toString();
        String sql = "UPDATE `users` SET `cookie_key` = ? WHERE `id` = ?";
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, key);
            p.setInt(2, id);
            p.executeUpdate();
        } catch(SQLException ignored) {
            return null;
        }
        return key;
    }
    //todo use that

    /**
     * finds user with given cookie key for next usage .
     * @param key
     * @return
     */
    public static User getUserByCookieKey(String key) {
        ResultSet r;
        String sql = "SELECT * FROM `users` WHERE `cookie_key` = ?";
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, key);
            r = p.executeQuery();
            if(!r.next()) return null;
            User u = new User(r.getInt("id"), r.getString("username"),
                    r.getString("password"),
                    r.getInt("is_admin") == 1 ? true : false,
                    r.getString("cookie_key"));
            return u;
        } catch (SQLException e) {
            return null;
        }
    }
}
