package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserStorage {
    public Collection<User> getAllUsers();
    public User addUser(User user) ;
    public User updateUser(Integer id,User user);
}
