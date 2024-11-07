package com.example.booking.repository;

import com.example.booking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select b " +
            "from  Booking b " +
            "where b.customer.id = :customerId  and b.staus = 'attivo'")
    Page<Booking> findAllBookings(Pageable paging, @Param("customerId") String customerId);
}