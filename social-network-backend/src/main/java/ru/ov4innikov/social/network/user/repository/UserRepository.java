package ru.ov4innikov.social.network.user.repository;

import ru.ov4innikov.social.network.user.model.User;

import java.util.List;

public interface UserRepository {

    long save(User user);

    User getById(long id);

    long clean();

    List<User> findUsersByFirstNameAndSecondName(String firstName, String secondName);
}