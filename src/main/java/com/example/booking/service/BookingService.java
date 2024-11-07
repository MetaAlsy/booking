package com.example.booking.service;

import com.example.booking.Dto.BookingDto;
import com.example.booking.entity.*;
import com.example.booking.exception.OperationNotPermittedException;
import com.example.booking.repository.AvailableRoomsRepository;
import com.example.booking.repository.BookingRepository;
import com.example.booking.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service

public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired HotelService hotelService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired CustomerService customerService;
    @Autowired
    private AvailableRoomsRepository availableRoomsRepository;

    @Transactional
    public Booking save(BookingDto booking, Authentication connectedUser) {

        LocalDate dataIni = LocalDate.parse(booking.getCheckinDate());
        LocalDate dataFin = LocalDate.parse(booking.getCheckoutDate());
        List<AvailableRooms> dateChek =  availableRoomsRepository.findAviabilityDateRange(booking.getRoomId(),dataIni,dataFin);

        Room r = this.roomRepository.findById(booking.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("No room found with ID :: " + booking.getRoomId()));;
        Customer c = this.customerService.findCustomerById(connectedUser.getName()).orElseThrow(() -> new EntityNotFoundException("No such customer  :: " + connectedUser.getName()));
        if(dateChek.size() != (dataFin.toEpochDay() - dataIni.toEpochDay() + 1))
            throw new IllegalStateException("Le date richieste non sono completamente disponibili");

        for (AvailableRooms date : dateChek)
            date.setStatus(false);
        try {
            availableRoomsRepository.saveAll(dateChek); // Aggiornamento con lock ottimistico
        }catch (OptimisticLockException e){
            throw new IllegalStateException("Prenotazione fallita: la stanza è stata modificata da un'altra transazione.", e);
        }
        Booking b = Booking.builder().room(r).customer(c).checkoutDate(dataFin).checkinDate(dataIni).totalPrice(r.getRoomType().getPricePerNight()*dateChek.size()).staus("attivo").build();
        return bookingRepository.save(b);
    }

    public List<Booking> findAllBookings(int page, int size, Authentication connectedUser) {
        Pageable paging = PageRequest.of(page, size, Sort.by("checkinDate").descending());
        Page<Booking> pagedResult = bookingRepository.findAllBookings(paging,connectedUser.getName(),LocalDate.now());
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }

    public Booking finaById(int bookingId, Authentication connectedUser) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("No such booking  :: " + bookingId));
        if (!Objects.equals(booking.getCustomer().getId(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cant see other bookigs");
        }
        return booking;
    }
    @Transactional
    public Integer cancelBooking(int bookingId, Authentication connectedUser) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("No such booking  :: " + bookingId));
        if (!Objects.equals(booking.getCustomer().getId(), connectedUser.getName()) &&
                (booking.getStaus().equals("annullato") || booking.getStaus().equals("chiuso"))) {
            throw new OperationNotPermittedException("You cant see other bookigs");
        }
        booking.setStaus("annullato");

        List<AvailableRooms> dateChek =  availableRoomsRepository.findDateRange(booking.getRoom().getId(),booking.getCheckinDate(),booking.getCheckoutDate());
        for (AvailableRooms date : dateChek)
            date.setStatus(true);
        availableRoomsRepository.saveAll(dateChek);

        return bookingRepository.save(booking).getId();//booking.getId();
    }
}
