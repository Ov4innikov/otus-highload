package ru.ov4innikov.social.network.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ov4innikov.social.network.model.LoginPost200Response;
import ru.ov4innikov.social.network.model.LoginPostRequest;
import ru.ov4innikov.social.network.model.UserRegisterPost200Response;
import ru.ov4innikov.social.network.model.UserRegisterPostRequest;
import ru.ov4innikov.social.network.user.model.User;
import ru.ov4innikov.social.network.user.repository.UserRepository;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class BaseUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserRegisterPost200Response registerUser(UserRegisterPostRequest userRegisterPostRequest) {
        User user = User.builder()
                .firstName(userRegisterPostRequest.getFirstName().orElse(null))
                .middleName(userRegisterPostRequest.getMiddleName().orElse(null))
                .secondName(userRegisterPostRequest.getSecondName().orElse(null))
                .birthdate(userRegisterPostRequest.getBirthdate().orElse(null))
                .biography(userRegisterPostRequest.getBiography().orElse(null))
                .city(userRegisterPostRequest.getCity().orElse(null))
                .password(passwordEncoder.encode(userRegisterPostRequest.getPassword()))
                .build();
        return new UserRegisterPost200Response().userId(userRepository.save(user));
    }

    @Override
    public ru.ov4innikov.social.network.model.User getById(String id) {
        User userFromDb = userRepository.getById(id);
        return new ru.ov4innikov.social.network.model.User()
                .id(userFromDb.getId())
                .firstName(userFromDb.getFirstName())
                .middleName(userFromDb.getMiddleName())
                .secondName(userFromDb.getSecondName())
                .birthdate(userFromDb.getBirthdate())
                .biography(userFromDb.getBiography())
                .city(userFromDb.getCity());
    }

    @Override
    public ru.ov4innikov.social.network.model.User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getById(username);
    }

    @Override
    public void getAdmin() {
        var user = getCurrentUser();
    }

    @Override
    public LoginPost200Response login(LoginPostRequest loginPostRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginPostRequest.getId().get(),
                loginPostRequest.getPassword().get()
        ));

        var user = userRepository.getById(loginPostRequest.getId().get());

        var jwt = jwtService.generateToken(user);
        return new LoginPost200Response().token(jwt);
    }

    @Override
    public List<ru.ov4innikov.social.network.model.User> searchUserByFirstNameAndSecondName(String firstName, String secondName) {
        List<User> usersByFirstNameAndSecondName = userRepository.findUsersByFirstNameAndSecondName(firstName, secondName);
        return usersByFirstNameAndSecondName.stream().map(userFromDb -> new ru.ov4innikov.social.network.model.User()
                .id(String.valueOf(userFromDb.getId()))
                .firstName(userFromDb.getFirstName())
                .middleName(userFromDb.getMiddleName())
                .secondName(userFromDb.getSecondName())
                .birthdate(userFromDb.getBirthdate())
                .biography(userFromDb.getBiography())
                .city(userFromDb.getCity())).toList();
    }
}
