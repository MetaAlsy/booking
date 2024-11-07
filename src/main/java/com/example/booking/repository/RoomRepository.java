package com.example.booking.repository;

import com.example.booking.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByHotel_IdAndAndRoomNumber(int hotelId, int roomId);


    @Query("select r " +
            "from Room r " +
            "where (r.hotel.id=?1 or ?1 is null) and " +
            "(lower(r.hotel.address) like LOWER( concat(?2,'%')) or ?2 is null) and " +
            "(r.roomType.pricePerNight >= ?3 or ?3 is null) and " +
            "(lower( r.roomType.name) like LOWER( concat(?4,'%')) or ?4 is null ) and " +
            "(r.roomType.capacity >= ?5 or ?5 is null) and r.status='available' " +
            "and exists (" +
            "       select ar " +
            "       from AvailableRooms ar " +
            "       where ar.room = r" +
            "           and  ar.aviableDate>=?6  " +
            "           and  ar.aviableDate<=?7 " +
            "           and ar.status = true)")
    Page<Room> findAvanzato(Pageable pageable, Integer hootelID, String address, Double prezzo, String nome, Integer posti, LocalDate dataIn, LocalDate dataFin);
}