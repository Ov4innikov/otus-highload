package ru.ov4innikov.social.network.user.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.ov4innikov.social.network.api.server.LoginApi;
import ru.ov4innikov.social.network.model.LoginPost200Response;
import ru.ov4innikov.social.network.model.LoginPostRequest;
import ru.ov4innikov.social.network.user.service.UserService;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Observed
@Controller
public class LoginController implements LoginApi {

    private final UserService userRegisterService;

    @Override
    public ResponseEntity<LoginPost200Response> loginPost(Optional<LoginPostRequest> loginPostRequest) {
        return loginPostRequest.map(postRequest -> new ResponseEntity<>(userRegisterService.login(postRequest), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }
}
