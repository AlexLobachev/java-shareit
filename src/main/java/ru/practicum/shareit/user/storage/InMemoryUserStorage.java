package ru.practicum.shareit.user.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exteption.ExceptionInvalidEmail;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {


    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, String> listEmail = new HashMap();

    private Integer id = 0;

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUser(Integer id) {
        return users.get(id);
    }

    public User addUser(User user) {
        duplicateEmail(user);
        user.setId(++id);

        addEmailList(user);
        users.put(user.getId(), user);

        log.debug("Пользователь {} добавлен", id);
        return user;
    }

    public User updateUser(Integer id, User user) {

        if (users.containsKey(id)) {
            user.setId(id);
            User userPatch = users.get(id);
            if (user.getEmail() != null && user.getName() != null) {
                duplicateEmail(user);
                addEmailList(user);
                users.put(id, user);
                log.debug("Пользователь {} успешно изменен", id);
            } else if (user.getEmail() != null) {
                duplicateEmail(user);
                addEmailList(user);
                userPatch.setEmail(user.getEmail());
                users.put(id, userPatch);
                log.debug("Email пользователя {} успешно изменен", id);
                return userPatch;
            } else if (user.getName() != null) {
                userPatch.setName(user.getName());
                users.put(id, userPatch);
                log.debug("Имя пользователя {} успешно изменено", id);
                return userPatch;
            }
        } else {
            log.debug("Пользователь {} не найден", id);
        }
        return user;
    }

    public void deleteUser(Integer id) {
        users.remove(id);
        listEmail.remove(id);
    }

    private void duplicateEmail(User user) {
        if (listEmail.containsValue(user.getEmail())) {
            throw new ExceptionInvalidEmail("Email существует");
        }

    }

    private void addEmailList(User user) {
        listEmail.put(user.getId(), user.getEmail());
    }

    public boolean containsKey(Object key) {
        return users.containsKey(key);
    }


}
