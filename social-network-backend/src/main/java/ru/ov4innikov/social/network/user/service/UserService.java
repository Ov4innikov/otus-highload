package ru.ov4innikov.social.network.user.service;

import ru.ov4innikov.social.network.model.*;

import java.util.List;

public interface UserService {

    UserRegisterPost200Response registerUser(UserRegisterPostRequest userRegisterPostRequest);

    User getById(String id);

    User getCurrentUser();

    void getAdmin();

    LoginPost200Response login(LoginPostRequest loginPostRequest);

    List<User> searchUserByFirstNameAndSecondName(String firstName, String secondName);
}
