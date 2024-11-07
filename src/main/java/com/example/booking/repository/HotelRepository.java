package com.example.booking.repository;

import com.example.booking.entity.Hotel;
import com.example.booking.entity.Room;
import com.example.booking.entity.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query("Select r " +
            "from Room r " +
            "where r.hotel.id = ?1 ")
    Page<Room> findAllRoomsByID(Pageable pageable, Integer hotelId);
    @Query("select distinct t " +
            "from RoomType t " +
            "where t.hotel.id = ?1"
             )
    Optional<List<RoomType>> findAllTypes(int hootelID);
}