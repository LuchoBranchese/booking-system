package com.lbranchese.BookingSystem.application.port.command;

import com.lbranchese.BookingSystem.domain.model.Reservation;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {

    Optional <Reservation> findById(UUID id);

    boolean existsByResourceAndDate(UUID resourceId, LocalDate date);

    void save(Reservation reservation);
}
