package ru.ov4innikov.social.network.user.repository;

import ru.ov4innikov.social.network.user.model.User;

import java.util.List;

public interface UserRepository {

    String save(User user);

    User getById(String id);

    long clean();

    List<User> findUsersByFirstNameAndSecondName(String firstName, String secondName);
}