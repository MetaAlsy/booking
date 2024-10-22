package com.example.booking.service;

import com.example.booking.Dto.RoomDTO;
import com.example.booking.entity.Hotel;
import com.example.booking.entity.Room;
import com.example.booking.entity.RoomType;
import com.example.booking.exception.OperationNotPermittedException;
import com.example.booking.repository.RoomRepository;
import com.example.booking.repository.RoomTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomTypeRepository roomTypeRepository;


    public Integer save(RoomDTO request, Authentication connectedUser) {
        Hotel h = hotelService.findById(request.getHotelID());
        RoomType rt = request.mapToRoomType();
        if( roomTypeRepository.findById(request.getRoomTypeID()).isEmpty()){
             roomTypeRepository.save(rt);
        }

        if (h!=null &&!Objects.equals(h.getOwner().getId(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot cerate room in this hotel");
        }
        Room r = request.mapToRoom();
        r.setHotel(h);
        r.setRoomType(rt);

        return roomRepository.save(r).getRoomNumber();
    }

//    private RoomType findType(Integer roomTypeID) {
//        return roomTypeRepository.findById(roomTypeID).
//
//               orElseThrow(() -> new EntityNotFoundException("No roomType found with ID :: " + roomTypeID));
//    }

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
    public List<Room> findAllAvanzato(Integer hootelID, String address, Double prezzo, String nome, Integer posti, LocalDate dataIn, LocalDate dataFin, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("roomNumber").descending());
        Page<Room> rooms = roomRepository.findAvanzato(pageable,hootelID,address,prezzo,nome,posti,dataIn,dataFin);
        if ( rooms.hasContent() ) {
            return rooms.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }
}
