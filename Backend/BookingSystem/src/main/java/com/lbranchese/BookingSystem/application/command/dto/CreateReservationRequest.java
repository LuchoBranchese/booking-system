package com.lbranchese.BookingSystem.application.command.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreateReservationRequest {

    private UUID userId;
    private UUID resourceId;
    private LocalDate date;

    public CreateReservationRequest() {
    }

    public CreateReservationRequest(UUID userId, UUID resourceId, LocalDate date) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.date = date;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

