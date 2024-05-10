package ru.ov4innikov.social.network.friend.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendService {

    List<String> getFriendIds();

    void addFriend(String friendUserId);

    void deleteFriend(String friendUserId);
}
