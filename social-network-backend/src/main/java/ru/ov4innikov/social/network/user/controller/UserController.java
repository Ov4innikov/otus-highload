package ru.ov4innikov.social.network.user.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.ov4innikov.social.network.api.server.UserApi;
import ru.ov4innikov.social.network.model.User;
import ru.ov4innikov.social.network.model.UserRegisterPost200Response;
import ru.ov4innikov.social.network.model.UserRegisterPostRequest;
import ru.ov4innikov.social.network.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Observed
@Controller
public class UserController implements UserApi {

    private final UserService userRegisterService;

    @Override
    public ResponseEntity<User> userGetIdGet(String id) {
        return new ResponseEntity<>(userRegisterService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRegisterPost200Response> userRegisterPost(Optional<UserRegisterPostRequest> userRegisterPostRequest) {
        if (userRegisterPostRequest.isEmpty()) new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(userRegisterService.registerUser(userRegisterPostRequest.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> userSearchGet(String firstName, String secondName) {
        return new ResponseEntity<>(userRegisterService.searchUserByFirstNameAndSecondName(firstName, secondName), HttpStatus.OK);
    }
}
