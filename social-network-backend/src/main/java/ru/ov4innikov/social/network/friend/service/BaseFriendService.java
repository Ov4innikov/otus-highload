package ru.ov4innikov.social.network.friend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import ru.ov4innikov.social.network.friend.repository.FriendRepository;
import ru.ov4innikov.social.network.model.User;
import ru.ov4innikov.social.network.user.service.UserService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class BaseFriendService implements FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;

    @Override
    public List<String> getFriendIds() {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getId().isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return friendRepository.getFriendIds(currentUser.getId().get());
    }

    @Override
    public void addFriend(String friendUserId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getId().isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        friendRepository.addFriend(currentUser.getId().get(), friendUserId);
    }

    @Override
    public void deleteFriend(String friendUserId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getId().isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        friendRepository.deleteFriend(currentUser.getId().get(), friendUserId);
    }
}
