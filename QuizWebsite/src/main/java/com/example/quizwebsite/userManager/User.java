package com.example.quizwebsite.userManager;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    public boolean isAdmin;
    private String cookieKey;
    private String password;

    public User(String username, boolean isAdmin, String password, String cookieKey) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.password = password;
        this.cookieKey = cookieKey;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // haven't created yet friends table todo
//    public List<User> getFriends(Connection db) {
//        List<User> friends = new ArrayList<>();
//        try {
//            PreparedStatement stmt = db.prepareStatement("SELECT * FROM `friends` WHERE `user_id_1` = ? OR `user_id_2` = ?");
//            stmt.setInt(1, userId);
//            stmt.setInt(2, userId);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                String friendName = rs.getString("user_id_1") == username ? rs.getString("user_id_2") : rs.getString("user_id_1");
//                User friend = userManager.getUser(friendName);
//                if (friend != null) {
//                    friends.add(friend);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return friends;
//    }

    // todo
//    public static User getUserByCookieKey(Connection db, String key) {
//        try {
//            PreparedStatement stmt = db.prepareStatement("SELECT * FROM `users` WHERE `cookie_key` = ?");
//            stmt.setString(1, key);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return new User(rs.getString("username"),
//                        rs.getBoolean("is_admin"),
//                        rs.getString("password"),
//                        rs.getString("cookie_key"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public String setCookieKey(Connection db) {
//        String key = new BigInteger(140, new SecureRandom()).toString();
//        try {
//            PreparedStatement stmt = db.prepareStatement("UPDATE `users` SET `cookie_key` = ? WHERE `id` = ?");
//            stmt.setString(1, key);
//            stmt.setInt(2, userId);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        this.cookieKey = key;
//        return key;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
