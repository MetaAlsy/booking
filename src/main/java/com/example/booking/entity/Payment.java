package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @Column(name = "PaymentID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "BookingID", nullable = false)
    private Booking booking;  // Prenotazione associata al pagamento

    @Column(name = "Amount", nullable = false)
    private Double amount;

    @Column(name = "PaymentDate", nullable = false)
    private java.time.LocalDate paymentDate;  // DATE in SQL maps to LocalDate

    @Column(name = "PaymentMethod")
    private String paymentMethod;
}
