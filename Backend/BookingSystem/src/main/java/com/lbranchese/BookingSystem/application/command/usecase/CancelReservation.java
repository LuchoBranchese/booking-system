package com.lbranchese.BookingSystem.application.command.usecase;

import com.lbranchese.BookingSystem.application.port.command.ReservationRepository;
import com.lbranchese.BookingSystem.application.port.command.UserRepository;
import com.lbranchese.BookingSystem.domain.model.Reservation;
import com.lbranchese.BookingSystem.domain.model.User;

import java.time.LocalDate;
import java.util.UUID;


public class CancelReservation {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public CancelReservation(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public void execute(UUID userId, UUID reservationId, LocalDate today) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException());


        boolean shouldPenalize = reservation.cancel(user.getId(), user.isAdmin(), today);

        if (shouldPenalize) {
            user.applyPenalty(today);
        }
        reservationRepository.save(reservation);


    }

}
