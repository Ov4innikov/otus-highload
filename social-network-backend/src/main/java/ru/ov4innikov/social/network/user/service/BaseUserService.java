package ru.ov4innikov.social.network.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ov4innikov.social.network.model.*;
import ru.ov4innikov.social.network.user.model.User;
import ru.ov4innikov.social.network.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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
                .interests(userRegisterPostRequest.getInterests().orElse(null))
                .isMale(userRegisterPostRequest.getGender().isEmpty() ? null : userRegisterPostRequest.getGender().get() == Gender.MALE)
                .password(passwordEncoder.encode(userRegisterPostRequest.getPassword()))
                .build();
        return new UserRegisterPost200Response().userId(userRepository.save(user));
    }

    @Override
    public ru.ov4innikov.social.network.model.User getById(String id) {
        User userFromDb = userRepository.getById(id);
        return convert(userFromDb);
    }

    @Override
    public ru.ov4innikov.social.network.model.User getCurrentUser() {
        var username = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getName();
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
        return usersByFirstNameAndSecondName.stream().map(this::convert).toList();
    }

    private ru.ov4innikov.social.network.model.User convert(User userFromDb) {
        ru.ov4innikov.social.network.model.User user = new ru.ov4innikov.social.network.model.User();
        user.setId(Optional.of(userFromDb.getId()));
        user.setFirstName(Optional.of(userFromDb.getFirstName()));
        user.setMiddleName(Optional.ofNullable(userFromDb.getMiddleName()));
        user.setSecondName(Optional.of(userFromDb.getSecondName()));
        user.setBirthdate(Optional.ofNullable(userFromDb.getBirthdate()));
        user.setBiography(Optional.ofNullable(userFromDb.getBiography()));
        user.setCity(Optional.ofNullable(userFromDb.getCity()));
        user.setInterests(Optional.ofNullable(userFromDb.getInterests()));
        user.setGender(Optional.ofNullable(userFromDb.getIsMale() == null ? null : userFromDb.getIsMale() ? Gender.MALE : Gender.FEMALE));
        return user;
    }
}
