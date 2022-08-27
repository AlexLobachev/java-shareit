package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exteption.NullAllowed;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
@Valid
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
    public User addUser(@Validated(NullAllowed.class) @RequestBody User user) {
        return userServiceImpl.addUser(user);
    }

    @PatchMapping(value = "{id}")
    public User updateUser(@Valid @PathVariable Long id, @RequestBody User user) {

        return userServiceImpl.updateUser(id, user);
    }

    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
    }


}
