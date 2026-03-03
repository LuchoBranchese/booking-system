package com.lbranchese.BookingSystem.application.command.usecase;

import com.lbranchese.BookingSystem.application.port.command.ReservationRepository;
import com.lbranchese.BookingSystem.application.port.command.ResourceRepository;
import com.lbranchese.BookingSystem.application.port.command.UserRepository;
import com.lbranchese.BookingSystem.domain.model.Reservation;
import com.lbranchese.BookingSystem.domain.model.Resource;
import com.lbranchese.BookingSystem.domain.model.User;

import java.time.LocalDate;
import java.util.UUID;

public class CrearReserva {

    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final ReservationRepository reservationRepository;

    public CrearReserva(UserRepository userRepository, ResourceRepository resourceRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
        this.reservationRepository = reservationRepository;
    }

    public UUID execute(UUID userId, UUID resourceId, LocalDate fecha, LocalDate hoy) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());

        if (!user.puedeReservar(hoy)) {
            throw new RuntimeException();
        }

        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new RuntimeException());

        if (!resource.estaActivo()) {
            throw new RuntimeException();
        }

        if (reservationRepository.existsByResourceAndDate(resourceId, fecha)) {
            throw new RuntimeException();
        }
        Reservation reservation = Reservation.create(userId, resourceId, fecha, hoy);

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}
