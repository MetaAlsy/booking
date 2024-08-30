package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Room")
public class Room {
    @Id
    @Column(name = "RoomNumber")
    private Integer roomNumber;

    @ManyToOne
    @JoinColumn(name = "HotelID", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "TypeID", nullable = false)
    private RoomType roomType;

    @Column(name = "Status")
    private String status;
}
