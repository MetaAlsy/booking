package com.example.booking.service;

import com.example.booking.entity.AvailableRooms;
import com.example.booking.entity.Room;
import com.example.booking.repository.AvailableRoomsRepository;
import com.example.booking.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AvailableRoomsService {
    @Autowired
    private AvailableRoomsRepository availableRoomsRepository;
    @Autowired
    private RoomRepository roomRepository;
    public Integer addDate(Integer roomId, LocalDate iniDate, LocalDate finDate) {
        if (iniDate.isAfter(finDate)) {
            throw new IllegalArgumentException("Start after finish");
        }
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("No Room ID: " + roomId));
        List<AvailableRooms> availableRoomsList = new ArrayList<>();
        LocalDate currentDate = iniDate;
        while (!currentDate.isAfter(finDate)) {
            if (!availableRoomsRepository.existsByRoomAndAviableDate(room, currentDate)) {
                AvailableRooms availableRoom = AvailableRooms.builder().room(room).aviableDate(currentDate).status(true).build();
                availableRoomsList.add(availableRoom);
            }
            currentDate = currentDate.plusDays(1);
        }

        availableRoomsRepository.saveAll(availableRoomsList);
        return null;
    }

    public List<AvailableRooms> findeAllbyId(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("aviableDate").descending());
        Page<AvailableRooms> dates = availableRoomsRepository.findAllByRoomId(pageable,id);
        if ( dates.hasContent() ) {
            return dates.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }

    public Integer changeState(Integer id) {
        AvailableRooms ar = this.availableRoomsRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Date not exist"));
        ar.setStatus(!ar.getStatus());
        return this.availableRoomsRepository.save(ar).getId();
    }



    public List<AvailableRooms> findeAllbyIdFutureDates(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("aviableDate").descending());
        Page<AvailableRooms> dates = availableRoomsRepository.findAllByRoomId(pageable,id,LocalDate.now());
        if ( dates.hasContent() ) {
            return dates.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }
}
