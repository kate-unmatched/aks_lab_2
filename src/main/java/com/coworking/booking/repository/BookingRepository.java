package com.coworking.booking.repository;

import com.coworking.booking.entity.Booking;
import com.coworking.booking.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends BaseRepository<Booking, Long> {

    List<Booking> findByRoomId(Long roomId);

    @Query("""
           SELECT b FROM Booking b
           WHERE b.room.id = :roomId
             AND b.startTime < :end
             AND b.endTime > :start
           """)
    List<Booking> findConflicts(
            @Param("roomId") Long roomId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
