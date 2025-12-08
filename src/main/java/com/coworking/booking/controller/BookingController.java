package com.coworking.booking.controller;

import com.coworking.booking.entity.Booking;
import com.coworking.booking.entity.Room;
import com.coworking.booking.service.BookingService;
import com.coworking.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rooms/{roomId}/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping
    public String list(@PathVariable Long roomId, Model model) {
        Room room = roomService.getById(roomId);

        model.addAttribute("room", room);
        model.addAttribute("bookings", bookingService.getBookingsByRoom(roomId));

        return "bookings/list";
    }

    @GetMapping("/create")
    public String createForm(@PathVariable Long roomId, Model model) {
        Room room = roomService.getById(roomId);

        Booking booking = new Booking();
        booking.setRoom(room); // <--- ВАЖНО!

        model.addAttribute("room", room);
        model.addAttribute("booking", booking);

        return "bookings/create";
    }

    @PostMapping
    public String create(@PathVariable Long roomId,
                         @ModelAttribute Booking booking,
                         Model model) {

        Room room = roomService.getById(roomId);
        booking.setRoom(room); // <--- ОБЯЗАТЕЛЬНО!

        try {
            bookingService.create(roomId, booking);
            return "redirect:/rooms/" + roomId + "/bookings";

        } catch (IllegalStateException ex) {
            // Перехват ошибки бронирования
            model.addAttribute("room", room);
            model.addAttribute("booking", booking);
            model.addAttribute("errorMessage", ex.getMessage());

            return "bookings/create"; // возвращаем обратно на форму
        }
    }

    @PostMapping("/{bookingId}/delete")
    public String delete(@PathVariable Long roomId, @PathVariable Long bookingId) {
        bookingService.delete(bookingId);
        return "redirect:/rooms/" + roomId + "/bookings";
    }
}
