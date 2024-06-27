package com.example.quizwebsite.userManager;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;

public class userManager implements userInterface {
    private static BasicDataSource dataSource;

    public userManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public boolean accountExists(User user) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
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
    public boolean validatePassword(User user,String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
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
    public boolean createAccount(User user) {
        if (accountExists(user)) {
            return false;
        }
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
//            stmt.setInt(3, is_admin ? 1 : 0);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUser(String username) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `users` WHERE `username` = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"),rs.getString("password"));
//                        rs.getBoolean("is_admin"),
//                        rs.getString("cookie_key"))
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//todo
//    public static int numAllUsers() {
//        try {
//            ResultSet chals = db.prepareStatement("SELECT COUNT(*) FROM `user`").executeQuery();
//            int numUsers = 0;
//            if (chals.next()) {
//                numUsers += chals.getInt(1);
//            }
//            return numUsers;
//        } catch (SQLException e) {
//            return 0;
//        }
//    }
//    public static void removeUser(User u) {
//        try {
//            db.prepareStatement("DELETE FROM `user` WHERE `user_id` = " + u.user_id).executeUpdate();
//            db.prepareStatement("DELETE FROM `friend` WHERE `user_id_1` = " + u.user_id + " OR `user_id_2` = " + u.user_id).executeUpdate();
//            ResultSet rs = db.prepareStatement("SELECT `quiz_id` FROM `quiz` WHERE `user_id` = " + u.user_id).executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void makeUserAdmin(int user_id) {
        try {
            Connection conn = dataSource.getConnection();
            conn.prepareStatement("UPDATE `user` SET `is_admin` = 1 WHERE `user_id` = " + user_id).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
