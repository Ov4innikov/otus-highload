package ru.ov4innikov.social.network.friend.repository;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendRepository {

    List<String> getFriendIds(String currentUserId);

    void addFriend(String currentUserId, String friendUserId);

    void deleteFriend(String currentUserId, String friendUserId);

    long clean();
}
