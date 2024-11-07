package com.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Setter
@Getter
@Table(name = "AvailableRooms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"Room", "Aviable_Date"}))

public class AvailableRooms {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "Room", nullable = false)
    private Room room;

    @Column(name = "AviableDate", nullable = false)
    private LocalDate aviableDate;
    @Column(name = "Status")
    private Boolean status;

    @JsonIgnore
    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

}
