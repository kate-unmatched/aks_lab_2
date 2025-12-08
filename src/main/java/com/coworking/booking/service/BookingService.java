package com.coworking.booking.service;

import com.coworking.booking.entity.Booking;

import java.util.List;

public interface BookingService extends BaseService<Booking, Long> {

    List<Booking> getByRoom(Long roomId);

    Booking create(Booking booking, Long roomId);
}
