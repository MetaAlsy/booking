package com.example.booking.controller;

import com.example.booking.entity.Room;
import com.example.booking.service.HotelService;
import com.example.booking.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelService hotelService;
    @PostMapping
    @PreAuthorize("hasRole('owner')")

    public ResponseEntity<Integer> saveRoom(
            @Valid @RequestBody Room request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(roomService.save(request, connectedUser));
    }
    @GetMapping
    public ResponseEntity<List<Room>> findAllRooms(@RequestParam(name = "hotel") int hootelID,
                                                   @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                   @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        return ResponseEntity.ok(hotelService.findAllById(hootelID,page,size));
    }
    @GetMapping("/{hotelId}/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable int roomId,
                                            @PathVariable int hotelId){
        return ResponseEntity.ok(roomService.findRoomById(hotelId,roomId));
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<Integer> updateRoom (@RequestParam(name = "roomId") int roomId,
                                            @RequestParam(name = "hotel") int hotelID,
                                            @RequestParam(name = "status") String status,
                                            Authentication connectedUser){
        return ResponseEntity.ok(roomService.updateRoom(hotelID,roomId,status,connectedUser));
    }
}
