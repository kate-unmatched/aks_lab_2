package com.coworking.booking.service;

import com.coworking.booking.entity.Booking;
import com.coworking.booking.entity.Room;
import com.coworking.booking.repository.BookingRepository;
import com.coworking.booking.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking, Long>
        implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomRepository roomRepository) {
        super(bookingRepository);
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Booking> getByRoom(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public Booking create(Booking booking, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        booking.setRoom(room);
        return bookingRepository.save(booking);
    }
}
