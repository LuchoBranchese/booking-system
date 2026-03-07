package com.lbranchese.BookingSystem.application.query.usecase;

import com.lbranchese.BookingSystem.application.port.query.ReservationQueryRepository;
import com.lbranchese.BookingSystem.application.query.model.ReservationByResourceSummary;
import com.lbranchese.BookingSystem.application.query.model.ReservationByUserSummary;

import java.util.List;
import java.util.UUID;


public class GetReservationsByResource {

    private final ReservationQueryRepository reservationQueryRepository;

    public GetReservationsByResource(ReservationQueryRepository reservationQueryRepository) {
        this.reservationQueryRepository = reservationQueryRepository;
    }

    public List <ReservationByResourceSummary> execute(UUID resourceId) {
        return reservationQueryRepository.findByResourceId(resourceId);
    }

}
