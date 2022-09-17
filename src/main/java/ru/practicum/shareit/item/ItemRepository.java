package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.name = ?1, i.description = ?2, i.available = ?3 where i.id = ?4")
    void updateItemByAll(String name, String description, Boolean available, Long itemId);

    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.name = ?1 where i.id = ?2")
    void updateItemByName(String name, Long itemId);

    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.description = ?1 where i.id = ?2")
    void updateItemByDescription(String description, Long itemId);

    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.available = ?1 where i.id = ?2")
    void updateItemByAvailable(Boolean available, Long itemId);

    List<Item> findByOwner_id(Long owner);

    @Query("SELECT i FROM Item i WHERE lower (i.description)  like  %:description% AND i.available = true")
    List<Item> findAllByName(@Param("description") String description);

    List<Item> findAllByRequestId(Long userId);

    List<Item> findAllByRequestIdNotNull();
}
