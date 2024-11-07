package com.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Booking")
public class Booking {
    @Id
    @Column(name = "BookingID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;  // Cliente che ha effettuato la prenotazione

    @ManyToOne
    @JoinColumn(name = "RoomNumber", nullable = false)
    @JsonIgnore
    private Room room;  // Camera prenotata

    @Column(name = "CheckinDate", nullable = false)
    private LocalDate checkinDate;  // DATE in SQL maps to LocalDate

    @Column(name = "CheckoutDate", nullable = false)
    private LocalDate checkoutDate;  // DATE in SQL maps to LocalDate

    @Column(name = "TotalPrice", nullable = false)
    private Double totalPrice;
    @Column(name = "Status", nullable = false)
    private String staus;//annulato,attivo,chiuso

    @JsonProperty("hotelId")
    public Integer getHotelID() {
        return room.getHotelID();
    }
    @JsonProperty("roomId")
    public Integer getRoomNumber() {
        return room.getRoomNumber();
    }

    @JsonProperty("hotelName")
    public String getHotelName() {
        return room.getHotel().getName();
    }
}
