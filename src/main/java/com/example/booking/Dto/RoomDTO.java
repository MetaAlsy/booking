package com.example.booking.Dto;

import com.example.booking.entity.Room;
import com.example.booking.entity.RoomType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoomDTO implements Serializable {
    private Integer roomNumber;
    private Integer hotelID;
    private Integer roomTypeID;
    private String name;
    private String description;
    private Double pricePerNight;
    private Integer capacity;
    private String status;

    public RoomType mapToRoomType() {
        return  RoomType.builder().id((this.roomTypeID!=0)?this.roomTypeID:null).name(this.name).description(this.description).capacity(this.capacity).pricePerNight(this.pricePerNight).build();
    }

    public Room mapToRoom() {
        return Room.builder().status(this.status).roomNumber(this.roomNumber).build();
    }
}
