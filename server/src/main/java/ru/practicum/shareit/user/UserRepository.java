package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("update User u set u.name = ?1, u.email = ?2 where u.id = ?3")
    void updateUserByAll(String name, String email, Long userId);

    @Modifying
    @Query("update User u set u.email = ?1 where u.id = ?2")
    void updateUserByEmail(String email, Long userId);

    @Modifying
    @Query("update User u set u.name = ?1 where u.id = ?2")
    void updateUserByName(String name, Long userId);

}
