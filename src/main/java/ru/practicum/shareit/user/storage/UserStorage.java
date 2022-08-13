package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getAllUsers();
    User addUser(User user) ;
    User updateUser(Integer id,User user);
}
