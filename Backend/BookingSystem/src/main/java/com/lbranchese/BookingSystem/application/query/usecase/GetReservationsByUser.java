package com.lbranchese.BookingSystem.application.query.usecase;

import com.lbranchese.BookingSystem.application.port.query.ReservationQueryRepository;
import com.lbranchese.BookingSystem.application.query.model.ReservationByUserSummary;

import java.util.List;
import java.util.UUID;

public class GetReservationsByUser {

    private final ReservationQueryRepository reservationQueryRepository;

    public GetReservationsByUser(ReservationQueryRepository reservationQueryRepository) {
        this.reservationQueryRepository = reservationQueryRepository;
    }

    public List<ReservationByUserSummary> execute(UUID userId) {
        return reservationQueryRepository.findByUserId(userId);
    }
}
