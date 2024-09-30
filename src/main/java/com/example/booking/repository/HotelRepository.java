package com.example.booking.repository;

import com.example.booking.entity.Hotel;
import com.example.booking.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query("Select r " +
            "from Room r " +
            "where r.hotel.id = ?1 ")
    Page<Room> findAllRoomsByID(Pageable pageable, Integer hotelId);
}