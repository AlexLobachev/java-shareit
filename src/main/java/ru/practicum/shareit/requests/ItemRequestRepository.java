package ru.practicum.shareit.requests;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequestorId(Long userId);

    List<ItemRequest> findAllByRequestorIdNot(Long userId, Pageable pageable);

    List<ItemRequest> findAllById(Long userId);
}
