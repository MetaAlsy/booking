package com.example.booking.service;

import com.example.booking.entity.Hotel;
import com.example.booking.entity.Room;
import com.example.booking.exception.OperationNotPermittedException;
import com.example.booking.repository.HotelRepository;
import com.example.booking.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;



    public Integer save(Room request, Authentication connectedUser) {
        if (!Objects.equals(request.getHotel().getOwner().getId(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot cerate room in this hotel");
        }
        return roomRepository.save(request).getRoomNumber();
    }

    public Room findRoomById(int hotelID, int roomId) {
       return roomRepository.findByHotel_IdAndAndRoomNumber(hotelID,roomId)
               .orElseThrow(() -> new EntityNotFoundException("No room found with ID :: " + roomId));
    }

    public int updateRoom(int hotelID, int roomId,String status, Authentication connectedUser) {
        Room room = roomRepository.findByHotel_IdAndAndRoomNumber(hotelID,roomId)
                .orElseThrow(() -> new EntityNotFoundException("No room found with ID :: " + roomId));
        if(!Objects.equals(room.getHotel().getOwner().getId(), connectedUser.getName()))
            throw new OperationNotPermittedException("You cannot cerate room in this hotel");
        room.setStatus(status);
        return roomRepository.save(room).getRoomNumber();
    }
}
