package com.lbranchese.BookingSystem.application.command.usecase;

import com.lbranchese.BookingSystem.application.port.command.ReservationRepository;
import com.lbranchese.BookingSystem.application.port.command.ResourceRepository;
import com.lbranchese.BookingSystem.application.port.command.UserRepository;
import com.lbranchese.BookingSystem.domain.model.Reservation;
import com.lbranchese.BookingSystem.domain.model.Resource;
import com.lbranchese.BookingSystem.domain.model.User;

import java.time.LocalDate;
import java.util.UUID;


public class CreateReservation {

    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final ReservationRepository reservationRepository;

    public CreateReservation(UserRepository userRepository, ResourceRepository resourceRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
        this.reservationRepository = reservationRepository;
    }

    public UUID execute(UUID userId, UUID resourceId, LocalDate date, LocalDate today) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());

        if (!user.canReserve(today)) {
            throw new RuntimeException();
        }

        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new RuntimeException());

        if (!resource.isActive()) {
            throw new RuntimeException();
        }

        if (reservationRepository.existsByResourceAndDate(resourceId, date)) {
            throw new RuntimeException();
        }
        Reservation reservation = Reservation.create(userId, resourceId, date, today);

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}

