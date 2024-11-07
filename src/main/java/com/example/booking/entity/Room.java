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
@Table(name = "Room",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"RoomNumber", "HotelID"})})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

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

    @Column(name = "RoomFoto")
    private String roomFoto;//path alle foto

    @JsonProperty("hotelId")
    public Integer getHotelID() {
        return hotel.getId();
    }
}
