package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Booking b set b.status =?1 where b.id = ?2")
    void updateStatus(Status status, Long id);

    Page<Booking> findByBooker_idOrderByIdDesc(Long id, Pageable pageable);

    List<Booking> findByStatusNotAndStatusNotOrderByIdDesc(Status rejected, Status canceled);

    List<Booking> findAllByBooker_IdAndStatusOrderByIdDesc(Long id, Status status);

    @Query("select b from Booking b where  b.end < ?2 and b.item.owner.id=?1")
    List<Booking> findByOrderPast(Long id, LocalDateTime localDateTime);

    @Query("select b from Booking b where  b.end < ?2 and b.booker.id =?1")
    List<Booking> findByUserPast(Long id, LocalDateTime localDateTime);

    List<Booking> findByStatusOrderByIdDesc(Status status);

    @Query("select b from Booking b where b.end > ?1 and b.start < ?1 and b.item.owner.id = ?2")
    List<Booking> findByOrderCurrent(LocalDateTime localDateTime, Long ownerId);

    @Query("select b from Booking b where b.end > ?1 and b.start < ?1 and b.booker.id = ?2")
    List<Booking> findByUserCurrent(LocalDateTime localDateTime, Long ownerId);

    @Query("select B from Booking B left join Item I on I.id = B.item.id where I.owner.id =?1 order by  B.id desc ")
    Page<Booking> findAllByOrderByIdDesc(Long ownerId, Pageable pageable);

    @Query(value = "" +
            "select b.booker.id " +
            "from Booking b " +
            "where b.item.id = ?1 " +
            "and b.status = 'APPROVED' " +
            "and b.start < ?2 group by b.booker.id")
    List<Long> findByItem_id(Long id, LocalDateTime dateTime);

    List<Booking> findByItem_idOrderByStartAsc(Long id);


}
