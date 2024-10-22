package com.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Room")
public class Room {
    @Id
    @Column(name = "RoomNumber")

    private Integer roomNumber;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "HotelID", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "TypeID", nullable = false)
    private RoomType roomType;

    @Column(name = "Status")
    private String status;

    @JsonProperty("hotelId")
    public Integer getHotelID() {
        return hotel.getId();
    }
}
