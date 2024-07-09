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
     *
     * @param user
     * @return User
     */
    public static User addUser(User user) {
        if (userExists(user.getUsername())) {
            return null;
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO users (username, password, is_admin, cookie_key) VALUES (?, ?, ?, ?)";
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.isAdmin() ? 1 : 0);
            stmt.setString(4, user.getCookieKey());
            stmt.executeUpdate();
            return getUserByUsername(user.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
    }

    /**
     * deletes user given the id.
     * returns true if user existed before the change.
     *
     * @param id
     * @return boolean
     */
    public static boolean deleteUser(int id) {
        //todo delete from friends
        int rowCount = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("DELETE FROM `users` WHERE `id` = ?;");
            stmt.setInt(1, id);
            rowCount = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return rowCount > 0;
    }

    /**
     * Returns user with given username from database.
     * Returns null if no user found.
     *
     * @param username
     * @return User
     */
    public static User getUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM `users` WHERE `username` = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boolean isAdmin = false;
                if (rs.getInt("is_admin") == 1) isAdmin = true;
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), isAdmin, rs.getString("cookie_Key"));
                if (isAdmin) user.makeAdmin();
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Returns user with given id from database.
     * Returns null if no user found.
     *
     * @param id
     * @return User
     */
    public User getUserById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM `users` WHERE `id` = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"));
                user.setId(rs.getInt("id"));
                //todo
                user.setCookieKey();
                //user.setCookieKey(rs.getString("cookie_key"));
                if (rs.getInt("is_admin") == 1) {
                    user.makeAdmin();
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return null;
    }

    /**
     * checks if user exists in database.
     *
     * @param username
     * @return boolean
     */
    public static boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return false;
    }

    /**
     * checks if user exists in database.
     *
     * @param username
     * @param password
     * @return boolean
     */
    public boolean validateUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password").equals(password);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return false;
    }

    /**
     * checks that user is not trying to
     * create account with empty credentials
     *
     * @param username
     * @param password
     * @return boolean
     */

    public boolean isValidInput(String username, String password) {
        return username != "" && username != null && password != null && password != "";
    }

    /**
     * makes user with given id an admin.
     * returns true if user exists.
     *
     * @param id
     * @return boolean
     */
    public boolean makeUserAdmin(int id) {
        int rowCount = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("UPDATE `users` SET `is_admin` = 1 WHERE `id` = ?");
            stmt.setInt(1, id);
            rowCount = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return rowCount > 0;
    }

    /**
     * generates string with given id for cookie usage .
     *
     * @param id
     * @return String
     */

    public String setCookieKey(int id) {
        String key = new BigInteger(140, new SecureRandom()).toString();
        String sql = "UPDATE `users` SET `cookie_key` = ? WHERE `id` = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, key);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException ignored) {
            return null;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return key;
    }
    //todo use that

    /**
     * finds user with given cookie key for next usage .
     *
     * @param key
     * @return User
     */
    public static User getUserByCookieKey(String key) {
        ResultSet r;
        String sql = "SELECT * FROM `users` WHERE `cookie_key` = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, key);
            r = stmt.executeQuery();
            if (!r.next()) return null;
            User u = new User(r.getInt("id"), r.getString("username"),
                    r.getString("password"),
                    r.getInt("is_admin") == 1 ? true : false,
                    r.getString("cookie_key"));
            return u;
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
    }
}
