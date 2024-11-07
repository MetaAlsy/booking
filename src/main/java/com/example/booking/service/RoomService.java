package com.example.booking.service;

import com.example.booking.Dto.RoomDTO;
import com.example.booking.Dto.RoomDtoResponse;
import com.example.booking.entity.Hotel;
import com.example.booking.entity.Room;
import com.example.booking.entity.RoomType;
import com.example.booking.exception.OperationNotPermittedException;
import com.example.booking.repository.RoomRepository;
import com.example.booking.repository.RoomTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private FileStorageService fileStorageService;



    @Transactional
    public Integer save(RoomDTO request, Authentication connectedUser) {
        Hotel h = hotelService.findById(request.getHotelID());
        RoomType rt= request.mapToRoomType();
        rt.setHotel(h);

        if (roomTypeRepository.findById(request.getRoomTypeID()).isEmpty()) {
            roomTypeRepository.save(rt);
        } else {
            rt = roomTypeRepository.findById(request.getRoomTypeID()).get();
        }

        if (h!=null &&!Objects.equals(h.getOwner().getId(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot cerate room in this hotel");
        }
        Room r = request.mapToRoom();
        r.setHotel(h);
        r.setRoomType(rt);

        return roomRepository.save(r).getId();
    }
    @Transactional
    public Integer updateRoom(RoomDTO request, Authentication connectedUser) {
        Hotel h = hotelService.findById(request.getHotelID());
        if (h!=null &&!Objects.equals(h.getOwner().getId(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot Update room in this hotel");
        }


        Room r = roomRepository.findByHotel_IdAndAndRoomNumber(request.getHotelID(), request.getRoomNumber())
                .orElseThrow(()->new EntityNotFoundException("Room non trovata"));

        RoomType rt= request.mapToRoomType();
        rt.setHotel(h);
        roomTypeRepository.save(rt);
        r.setRoomType(rt);
        r.setStatus(request.getStatus());

        return roomRepository.save(r).getId();
    }

    public List<RoomDtoResponse> findAllAvanzato(Integer hootelID, String address, Double prezzo, String nome, Integer posti, LocalDate dataIn, LocalDate dataFin, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("roomNumber").descending());
        Page<Room> rooms = roomRepository.findAvanzato(pageable,hootelID,address,prezzo,nome,posti,dataIn,dataFin);
        if ( rooms.hasContent() ) {
            return rooms.getContent().stream().map(RoomDtoResponse::toResponse).collect(Collectors.toList());
        }
        else {
            return new ArrayList<>();
        }
    }


    @Transactional
    public void uploadRoomFoto(MultipartFile file, Integer roomId, Authentication connectedUser) {
        Room r = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("No room found with ID :: " + roomId));
        var fotoRoom = fileStorageService.saveFile(file,r,connectedUser.getName());
        r.setRoomFoto(fotoRoom);
        this.roomRepository.save(r);
    }

    @Transactional
    public boolean delete(Integer roomId, Authentication connectedUser) {
        Room r = roomRepository.findById(roomId).orElseThrow(()->new EntityNotFoundException("No room found with Id :: "+roomId));
        Hotel h = hotelService.findById(r.getHotelID());
        if(!h.getOwner().getId().equals(connectedUser.getName()))
            throw new OperationNotPermittedException("Non puoi eliminare stanze dei altri");
        roomRepository.delete(r);
        return true;
    }
}
