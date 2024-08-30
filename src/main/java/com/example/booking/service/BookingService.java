package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.exception.OperationNotPermittedException;
import com.example.booking.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    public Booking save(Booking booking, Authentication connectedUser) {
        if (!Objects.equals(booking.getCustomer().getId(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot update others books shareable status");
        }
        //altri controlli
        return bookingRepository.save(booking);
    }

    public List<Booking> findAllBookings(int page, int size, Authentication connectedUser) {
        Pageable paging = PageRequest.of(page, size, Sort.by("checkinDate").descending());
        Page<Booking> pagedResult = bookingRepository.findAllBookings(paging,connectedUser.getName());
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
}
