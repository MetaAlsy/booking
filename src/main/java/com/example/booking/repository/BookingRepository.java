package com.example.booking.repository;

import com.example.booking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select b " +
            "from  Booking b " +
            "where b.customer.id = ?1 ")
    Page<Booking> findAllBookings(Pageable paging, String name);
}