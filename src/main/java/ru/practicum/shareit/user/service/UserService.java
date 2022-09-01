package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserService {
    Collection<User> getAllUsers();

    User addUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

}
