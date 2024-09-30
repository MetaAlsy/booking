package com.example.booking.service;

import com.example.booking.entity.Customer;
import com.example.booking.entity.Hotel;
import com.example.booking.entity.Room;
import com.example.booking.exception.OperationNotPermittedException;
import com.example.booking.repository.CustomerRepository;
import com.example.booking.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Room> findAllById(Integer hootelID, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("roomNumber").descending());
        Page<Room> rooms = hotelRepository.findAllRoomsByID(pageable,hootelID);
        if ( rooms.hasContent() ) {
            return rooms.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }

    public Hotel save(Hotel hotel,Authentication autenticatedCustomer) {
        Customer c = customerRepository.findById(autenticatedCustomer.getName())
                .orElseThrow(()->new EntityNotFoundException("Utente non esiste"));
        hotel.setOwner(c);
        return hotelRepository.save(hotel);
    }

    public List<Hotel> findAllHotels(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Hotel> pagedResult = hotelRepository.findAll(paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }

    public Hotel findById(int hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("No hotel found with ID :: " + hotelId));
    }

    public Integer updateHotel(Hotel hotel, Authentication connectedUser) {
        if (!Objects.equals(hotel.getOwner().getId(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot update others hotels");
        }
        return hotelRepository.save(hotel).getId();
    }
}
