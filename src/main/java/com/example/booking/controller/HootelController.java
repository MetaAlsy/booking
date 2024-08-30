package com.example.booking.controller;

import com.example.booking.entity.Hotel;
import com.example.booking.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hotels")
public class HootelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<Hotel> saveHotel(@Valid @RequestBody Hotel hotel){
        return ResponseEntity.ok(hotelService.save(hotel));
    }
    @GetMapping
    public ResponseEntity<List<Hotel>> findAllHotels( @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                      @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        return ResponseEntity.ok(hotelService.findAllHotels(page,size));
    }
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> findHotelById(@PathVariable int hotelId){
        return ResponseEntity.ok(hotelService.findById(hotelId));
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<Integer> updateHotel (@Valid @RequestBody Hotel hotel,
                                               Authentication connectedUser){
        return ResponseEntity.ok(hotelService.updateHotel(hotel,connectedUser));
    }
}
