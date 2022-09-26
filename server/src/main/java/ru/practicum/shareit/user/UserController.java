package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {
    private UserServiceImpl userServiceImpl;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping(value = "{id}")
    public User getUser(@PathVariable Long id) {
        return userServiceImpl.getUser(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userServiceImpl.addUser(user);
    }

    @PatchMapping(value = "{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {

        return userServiceImpl.updateUser(id, user);
    }

    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
    }


}
