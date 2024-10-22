package com.example.booking.controller;

import com.example.booking.Dto.RoomDTO;
import com.example.booking.entity.Room;
import com.example.booking.entity.RoomType;
import com.example.booking.service.HotelService;
import com.example.booking.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
            @Valid @RequestBody RoomDTO request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(roomService.save(request, connectedUser));
    }
    @GetMapping
    public ResponseEntity<List<Room>> findAllRooms(@RequestParam(name = "hotel",defaultValue = "53") int hootelID,
                                                   @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                   @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        return ResponseEntity.ok(hotelService.findAllById(hootelID,page,size));
    }
    @GetMapping("/types")
    public ResponseEntity<List<RoomType>> findAllRooms(@RequestParam(name = "hotel",defaultValue = "53") int hotelID){
        return ResponseEntity.ok(hotelService.findAllTypes(hotelID));
    }
    @GetMapping("/avanzato")
    ResponseEntity<List<Room>> findAllRoomsAvanzato(@RequestParam(name = "hotel", required = false) Integer hootelID,
                                                    @RequestParam(name = "address", required = false) String address,
                                                    @RequestParam(name = "price", required = false) Double prezzo,
                                                    @RequestParam(name = "name", required = false) String nome,
                                                    @RequestParam(name = "capacity", required = false) Integer posti,
                                                    @RequestParam(name = "chekIn", required = false) LocalDate dataIn,
                                                    @RequestParam(name = "chekOut", required = false) LocalDate dataFin,
                                                    @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                    @RequestParam(name = "size", defaultValue = "10", required = false) Integer size){
        return ResponseEntity.ok(roomService.findAllAvanzato(hootelID,address,prezzo,nome,posti,dataIn,dataFin,page,size));
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
