package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exсeption.ExceptionNotFoundUser;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ExceptionNotFoundUser("Пользователя не существует");
        }
        return userRepository.findById(id).orElse(new User());
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        if (user.getEmail() != null && user.getName() != null) {
            userRepository.updateUserByAll(user.getName(), user.getEmail(), id);
            log.debug("Пользователь {} успешно изменен", id);
        } else if (user.getEmail() != null) {
            userRepository.updateUserByEmail(user.getEmail(), id);
            log.debug("Email пользователя {} успешно изменен", id);
        } else if (user.getName() != null) {
            userRepository.updateUserByName(user.getName(), id);
            log.debug("Имя пользователя {} успешно изменено", id);
        }
        return getUser(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
