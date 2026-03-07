package com.lbranchese.BookingSystem.application.command.usecase;

import com.lbranchese.BookingSystem.application.command.exception.ResourceAlreadyReservedException;
import com.lbranchese.BookingSystem.application.command.exception.ResourceInactiveException;
import com.lbranchese.BookingSystem.application.command.exception.ResourceNotFoundException;
import com.lbranchese.BookingSystem.application.command.exception.UserBlockedException;
import com.lbranchese.BookingSystem.application.command.exception.UserNotFoundException;
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

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!user.canReserve(today)) {
            throw new UserBlockedException(user.getId(), user.getBlockedUntil());
        }

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(resourceId));

        if (!resource.isActive()) {
            throw new ResourceInactiveException(resourceId);
        }

        if (reservationRepository.existsByResourceAndDate(resourceId, date)) {
            throw new ResourceAlreadyReservedException(resourceId, date);
        }
        Reservation reservation = Reservation.create(userId, resourceId, date, today);

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}

