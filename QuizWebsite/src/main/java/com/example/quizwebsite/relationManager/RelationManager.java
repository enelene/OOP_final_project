package com.example.quizwebsite.relationManager;

import com.example.quizwebsite.userManager.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class RelationManager implements RelationInterface{

    private final DataSource dataSource;

    public RelationManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns false if request does not exist.
     * Checks if one of the users had sent request to other before.
     * @param user_id1
     * @param user_id2
     * @return
     */
    @Override
    public boolean addFriend(int user_id1, int user_id2) {
        RelationType cur = getRelation(user_id1, user_id2);
        if (cur != RelationType.REQUEST_FROM_USER_1
                && cur != RelationType.REQUEST_FROM_USER_2) {
            return false;
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            //add friend relation
            stmt = conn.prepareStatement("UPDATE `relations` SET status = 'friends' WHERE (id_1 = ?  AND id_2 = ? ) OR (id_1 = ?  AND id_2 = ?);\n");
            stmt.setInt(1, user_id1);
            stmt.setInt(2, user_id2);
            stmt.setInt(3, user_id2);
            stmt.setInt(4, user_id1);
            stmt.executeUpdate();
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
        return true;
    }

    /**
     * Returns false if no database change occurred.
     * @param user_id1
     * @param user_id2
     * @return
     */

    @Override
    public boolean removeFriend(int user_id1, int user_id2) {
        int rowCount = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("DELETE FROM `relations` WHERE id_1 = ? AND id_2 = ? AND status = 'friends';");
            stmt.setInt(1, user_id1);
            stmt.setInt(2, user_id2);
            stmt.addBatch();
            stmt.setInt(2, user_id1);
            stmt.setInt(1, user_id2);
            stmt.addBatch();
            rowCount = Arrays.stream(stmt.executeBatch()).sum();
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
     * Returns false if users were already friends, or there was a request sent between the two.
     * @param from_id
     * @param to_id
     */
    @Override
    public boolean sendRequest(int from_id, int to_id) {
        if (getRelation(from_id, to_id) != RelationType.NOT_FRIENDS) {
            return false;
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("INSERT INTO `relations` (id_1, id_2, status) VALUES (?, ?, 'request')");
            stmt.setInt(1, from_id);
            stmt.setInt(2, to_id);
            stmt.executeUpdate();
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
        return true;
    }

    /**
     * Deletes request between to users.
     * Returns false if no database change occurred.
     * @param user_id1
     * @param user_id2
     * @return
     */
    public boolean deleteRequest(int user_id1, int user_id2) {
        int rowCount = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("DELETE FROM `relations` WHERE id_1 = ? AND id_2 = ? AND status = 'request';");
            stmt.setInt(1, user_id2);
            stmt.setInt(2, user_id1);
            stmt.addBatch();
            stmt.setInt(2, user_id1);
            stmt.setInt(1, user_id2);
            stmt.addBatch();
            rowCount = Arrays.stream(stmt.executeBatch()).sum();
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

    @Override
    public Set<User> getFriends(int user_id) {
        Set<User> friends = new HashSet<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT u.id, u.username, u.is_admin, u.cookie_key " +
                                             "FROM users u JOIN relations r ON u.id = r.id_2 " +
                                             "WHERE r.id_1 = ? AND r.status = 'friends' " +
                                             "UNION " +
                                             "SELECT u.id, u.username, u.is_admin, u.cookie_key " +
                                             "FROM users u JOIN relations r ON u.id = r.id_1 " +
                                             "WHERE r.id_2 = ? AND r.status = 'friends';");
            stmt.setInt(1, user_id);
            stmt.setInt(2, user_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                User nextUser = new User(rs.getString(2),rs.getString(3));
                if(rs.getInt(3) > 0){
                    nextUser.makeAdmin();
                }
                //todo
                nextUser.setCookieKey();
                //nextUser.setCookieKey(rs.getString(4));
                nextUser.setId(rs.getInt(1));
                friends.add(nextUser);
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
        return friends;
    }


    /**
     * Returns set of all users who have sent requests to user to_id.
     * @param to_id
     * @return
     */
    @Override
    public Set<User> getRequests(int to_id) {
        Set<User> requests = new HashSet<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT u.id, u.username, u.is_admin, u.cookie_key FROM `relations` r JOIN `users` u ON r.id_1 = u.id WHERE r.id_2 = ? AND r.status = 'request';");
            stmt.setInt(1, to_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                User nextUser = new User(rs.getString(2),rs.getString(3));
                if(rs.getInt(3) > 0){
                    nextUser.makeAdmin();
                }
                //todo
                nextUser.setCookieKey();
                //nextUser.setCookieKey(rs.getString(4));
                nextUser.setId(rs.getInt(1));
                requests.add(nextUser);
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
        return requests;
    }

    @Override
    public RelationType getRelation(int from_id, int to_id) {
        Connection conn = null;
        PreparedStatement stmt0 = null;
        PreparedStatement stmt1 = null;
        try {
            conn = dataSource.getConnection();
            stmt0 = conn.prepareStatement("SELECT status FROM `relations` WHERE id_1 = ? AND id_2 = ?;");
            stmt0.setInt(1, from_id);
            stmt0.setInt(2, to_id);
            ResultSet rs = stmt0.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                String status = rs.getString(1);
                return stringToRelationType(status, false);
            }
            stmt1 = conn.prepareStatement("SELECT status FROM `relations` WHERE id_1 = ? AND id_2 = ?;");
            stmt1.setInt(2, from_id);
            stmt1.setInt(1, to_id);
            ResultSet rs0 = stmt1.executeQuery();
            if (rs0.isBeforeFirst()) {
                rs0.next();
                String status = rs0.getString(1);
                return stringToRelationType(status, true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt0 != null) {
                    stmt0.close();
                }
                if (stmt1 != null) {
                    stmt1.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return RelationType.NOT_FRIENDS;
    }

    @Override
    public int getFriendsNum(int user_id) {
        return getFriends(user_id).size();
    }

    @Override
    public int getRequestsNum(int user_id) {
        return getRequests(user_id).size();
    }


    private RelationType stringToRelationType(String status, boolean flipDirection) {
        switch (status) {
            case "friends":
                return RelationType.FRIENDS;
            case "request":
                return flipDirection ? RelationType.REQUEST_FROM_USER_2 : RelationType.REQUEST_FROM_USER_1;
            default:
                return RelationType.NOT_FRIENDS;
        }
    }
}
