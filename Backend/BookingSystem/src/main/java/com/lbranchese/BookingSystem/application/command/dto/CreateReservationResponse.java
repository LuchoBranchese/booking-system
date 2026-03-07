package com.lbranchese.BookingSystem.application.command.dto;

import java.util.UUID;

public class CreateReservationResponse {

    private UUID reservationId;

    public CreateReservationResponse() {
    }

    public CreateReservationResponse(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }
}

