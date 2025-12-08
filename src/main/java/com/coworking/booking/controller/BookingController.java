package com.coworking.booking.controller;

import com.coworking.booking.entity.Booking;
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
        model.addAttribute("room", roomService.getById(roomId));
        model.addAttribute("bookings", bookingService.getByRoom(roomId));
        model.addAttribute("newBooking", new Booking());
        return "bookings/list";
    }

    @PostMapping
    public String create(@PathVariable Long roomId, @ModelAttribute Booking booking) {
        booking.setRoom(roomService.getById(roomId));

        try {
            bookingService.create(booking);
        } catch (RuntimeException e) {
            return "redirect:/rooms/" + roomId + "/bookings?error=" + e.getMessage();
        }

        return "redirect:/rooms/" + roomId + "/bookings";
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long roomId,
            @PathVariable Long id
    ) {
        bookingService.delete(id);
        return "redirect:/rooms/" + roomId + "/bookings";
    }
}
