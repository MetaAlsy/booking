package com.example.booking.repository;

import com.example.booking.entity.AvailableRooms;
import com.example.booking.entity.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface AvailableRoomsRepository extends JpaRepository<AvailableRooms, Integer> {


    boolean existsByRoomAndAviableDate(Room room, LocalDate currentDate);
    @Query("SELECT d " +
            "FROM AvailableRooms d " +
            "WHERE d.room.id = :roomId " +
            "AND d.aviableDate BETWEEN :startDate AND :endDate AND d.status = true ")
    List<AvailableRooms> findAviabilityDateRange(Integer roomId, LocalDate startDate, LocalDate endDate);

    Page<AvailableRooms> findAllByRoomId(Pageable pageable, Integer id);
    @Query("SELECT ar " +
            "FROM AvailableRooms ar " +
            "WHERE ar.room.id = :roomId AND ar.aviableDate >= :today and ar.status = true")
    Page<AvailableRooms> findAllByRoomId(Pageable pageable, Integer roomId, LocalDate today);

    @Query("SELECT d " +
            "FROM AvailableRooms d " +
            "WHERE d.room.id = :roomId " +
            "AND d.aviableDate BETWEEN :checkinDate AND :checkoutDate  ")
    List<AvailableRooms> findDateRange(Integer roomId, LocalDate checkinDate, LocalDate checkoutDate);
}
