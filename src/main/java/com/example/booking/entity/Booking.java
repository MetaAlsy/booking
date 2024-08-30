package com.example.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @Column(name = "BookingID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;  // Cliente che ha effettuato la prenotazione

    @ManyToOne
    @JoinColumn(name = "RoomNumber", nullable = false)
    private Room room;  // Camera prenotata

    @Column(name = "CheckinDate", nullable = false)
    private LocalDate checkinDate;  // DATE in SQL maps to LocalDate

    @Column(name = "CheckoutDate", nullable = false)
    private LocalDate checkoutDate;  // DATE in SQL maps to LocalDate

    @Column(name = "TotalPrice", nullable = false)
    private Double totalPrice;
}
