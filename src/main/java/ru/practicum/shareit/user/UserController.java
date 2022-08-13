package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exteption.NullAllowed;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
@Valid
public class UserController {
    private InMemoryUserStorage inMemoryUserStorage;

    @GetMapping
    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @GetMapping(value = "{id}")
    public User getUser(@PathVariable Integer id) {
        return inMemoryUserStorage.getUser(id);
    }

    @PostMapping
    public User addUser(@Validated(NullAllowed.class) @RequestBody User user) {
        return inMemoryUserStorage.addUser(user);
    }

    @PatchMapping(value = "{id}")
    public User updateUser(@Valid @PathVariable Integer id, @RequestBody User user) {
        return inMemoryUserStorage.updateUser(id, user);
    }

    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable Integer id) {
        inMemoryUserStorage.deleteUser(id);
    }


}
