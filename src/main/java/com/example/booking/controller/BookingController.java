package com.example.booking.controller;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Hotel;
import com.example.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping
    public ResponseEntity<Booking> saveBooking(@Valid @RequestBody Booking booking, Authentication connectedUser){
        return ResponseEntity.ok(bookingService.save(booking,connectedUser));
    }
    @GetMapping
    public ResponseEntity<List<Booking>> findAllBookings(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                       @RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                                       Authentication connectedUser) {
        return ResponseEntity.ok(bookingService.findAllBookings(page, size, connectedUser));
    }
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> findBookingById(@PathVariable int bookingId,Authentication connectedUser){
        return ResponseEntity.ok(bookingService.finaById(bookingId,connectedUser));
    }
    @PostMapping("/update")
    public ResponseEntity<Integer> updateBookign(){
        return null;//da implementare
    }
}
