package com.example.booking.Dto;

import com.example.booking.entity.Room;
import com.example.booking.entity.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomDtoResponse {
    private int id;
    private int roomNumber;
    private RoomType roomType;
    private String status;
    private int hotelId;
    private byte[] foto;// solo una
    private String hotelName;
    public static RoomDtoResponse toResponse(Room room){
        return RoomDtoResponse.builder().id(room.getId()).roomNumber(room.getRoomNumber()).roomType(room.getRoomType())
                .status(room.getStatus()).hotelId(room.getHotelID()).foto(FileUtils.readFileFromLocation(room.getRoomFoto())).hotelName(room.getHotel().getName()).build();
    }
}
