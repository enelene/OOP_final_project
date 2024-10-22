package com.example.quizwebsite.relationManager;

import com.example.quizwebsite.userManager.User;

import java.util.Set;

public interface RelationInterface {
    boolean addFriend(int user_id1, int user_id2);
    boolean removeFriend(int user_id1, int user_id2);
    boolean sendRequest(int from_id, int to_id);
    boolean deleteRequest(int user_id1, int user_id2);

    Set<User> getFriends(int user_id);
    Set<User> getRequests(int user_id);
    RelationType getRelation(int user_id1, int user_id2);

    int getFriendsNum(int user_id);
    int getRequestsNum(int user_id);
}
