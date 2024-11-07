package com.example.booking.controller;

import com.example.booking.Dto.RoomDTO;
import com.example.booking.Dto.RoomDtoResponse;
import com.example.booking.entity.AvailableRooms;
import com.example.booking.entity.Room;
import com.example.booking.entity.RoomType;
import com.example.booking.service.AvailableRoomsService;
import com.example.booking.service.HotelService;
import com.example.booking.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private AvailableRoomsService availableRoomsService;
    @PostMapping
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<Integer> saveRoom(
            @Valid @RequestBody RoomDTO request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(roomService.save(request, connectedUser));
    }
    @PostMapping("/update")
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<Integer> updateRoom(
            @Valid @RequestBody RoomDTO request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(roomService.updateRoom(request, connectedUser));
    }
    @PostMapping("/aviable/{id}")
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<Integer> addAvaibleDateToRoom(@PathVariable Integer id,@RequestParam(name = "iniDate", required = true) LocalDate iniDate,
                                                        @RequestParam(name = "finDate", required = false) LocalDate finDate) {
        if(finDate!=null)
            return ResponseEntity.ok(availableRoomsService.addDate(id, iniDate,finDate));
        else
            return ResponseEntity.ok(availableRoomsService.addDate(id, iniDate,null));
    }
    @GetMapping("/dates/{id}")
    //@PreAuthorize("hasRole('owner')")
    public ResponseEntity<List<AvailableRooms>> findAviableDates(@PathVariable Integer id,
                                                                 @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                 @RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                                                 @RequestParam(name = "onlyFutureDates", defaultValue = "false", required = false) boolean onlyFutureDates
                                                                 ) {
        if(onlyFutureDates)
            return ResponseEntity.ok(availableRoomsService.findeAllbyIdFutureDates(id,page,size));
        else
            return ResponseEntity.ok(availableRoomsService.findeAllbyId(id,page,size));//roomID
    }
    @PostMapping("/stateDate/{id}")
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<Integer> changeStateOfDate(@PathVariable Integer id) {

        return ResponseEntity.ok(availableRoomsService.changeState(id));
    }
    @GetMapping
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<List<RoomDtoResponse>> findAllRooms(@RequestParam(name = "hotel",defaultValue = "53") int hootelID,
                                                              @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        return ResponseEntity.ok(hotelService.findAllById(hootelID,page,size));
    }
    @GetMapping("/types")
    public ResponseEntity<List<RoomType>> findAllRooms(@RequestParam(name = "hotel",defaultValue = "53") int hotelID){
        return ResponseEntity.ok(hotelService.findAllTypes(hotelID));
    }
    @GetMapping("/avanzato")
    ResponseEntity<List<RoomDtoResponse>> findAllRoomsAvanzato(@RequestParam(name = "hotel", required = false) Integer hootelID,
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
//    @GetMapping("/{hotelId}/{roomId}")
//    public ResponseEntity<Room> getRoomById(@PathVariable int roomId,
//                                            @PathVariable int hotelId){
//        return ResponseEntity.ok(roomService.findRoomById(hotelId,roomId));
//    }

    @PostMapping(value = "foto/{roomId}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<?> uploudRoomFoto(@PathVariable("roomId") Integer roomId, @RequestPart("file")MultipartFile file,Authentication connectedUser){
        this.roomService.uploadRoomFoto(file,roomId,connectedUser);
        return ResponseEntity.accepted().build();
    }
    @DeleteMapping("delete/{roomId}")
    @PreAuthorize("hasRole('owner')")
    public ResponseEntity<?> uploudRoomFoto(@PathVariable("roomId") Integer roomId,Authentication connectedUser){
        return ResponseEntity.ok(roomService.delete(roomId,connectedUser));
    }
}
