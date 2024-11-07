package com.example.booking.Dto;

import com.example.booking.entity.Booking;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;


@Getter

public class BookingDto implements Serializable {
    private Integer id;
    private String checkinDate;
    private String checkoutDate;
    private Double totalPrice;
    private String staus;
    private Integer hotelId;
    private Integer roomId;


}