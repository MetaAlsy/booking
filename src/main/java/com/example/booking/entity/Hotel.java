package com.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(name = "checkin_Time")
    private String checkinTime;  // TIME in SQL can be mapped to String or LocalTime || prima era string

    @Column(name = "checkout_Time")
    private String checkoutTime;  // TIME in SQL can be mapped to String or LocalTime

    @ManyToOne
    @JoinColumn(name = "OwnerID")//aggiutare nullable false
    @JsonIgnore
    private Customer owner;  // Proprietario dell'hotel

    @OneToMany(mappedBy = "hotel")
    @JsonIgnore
    private List<Room> rooms;

}
