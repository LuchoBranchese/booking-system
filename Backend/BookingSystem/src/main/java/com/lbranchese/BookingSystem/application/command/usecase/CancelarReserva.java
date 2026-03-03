package com.lbranchese.BookingSystem.application.command.usecase;

import com.lbranchese.BookingSystem.application.port.command.ReservationRepository;
import com.lbranchese.BookingSystem.application.port.command.UserRepository;
import com.lbranchese.BookingSystem.domain.model.Reservation;
import com.lbranchese.BookingSystem.domain.model.User;

import java.time.LocalDate;
import java.util.UUID;

public class CancelarReserva {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public CancelarReserva(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public void execute(UUID userId, UUID reservationId, LocalDate hoy) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException());


        boolean penaliza = reservation.cancelar(user.getId(), user.isAdmin(), hoy);

        if (penaliza) {
            user.aplicarPenalizacion(hoy);
        }
        reservationRepository.save(reservation);
        userRepository.save(user);


    }

}
