package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Hotel")
public class Hotel {
    @Id
    @Column(name = "HotelID")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Address")
    private String address;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Stars")
    private Integer stars;

    @Column(name = "CheckinTime")
    private String checkinTime;  // TIME in SQL can be mapped to String or LocalTime

    @Column(name = "CheckoutTime")
    private String checkoutTime;  // TIME in SQL can be mapped to String or LocalTime

    @ManyToOne
    @JoinColumn(name = "OwnerID", nullable = false)
    private Customer owner;  // Proprietario dell'hotel

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;
}
