package com.example.booking.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "RoomType")
public class RoomType {
    @Id
    @Column(name = "TypeID")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "PricePerNight", nullable = false)
    private Double pricePerNight;

    @Column(name = "Capacity", nullable = false)
    private Integer capacity;
}
