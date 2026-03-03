package com.lbranchese.BookingSystem.application.query.model;


import java.time.LocalDate;
import java.util.UUID;

public class ReservationByUserSummary {

    private final UUID resourceId;
    private final LocalDate date;
    private final Status status;

    public ReservationByUserSummary(UUID resourceId, LocalDate date, Status status) {
        this.resourceId = resourceId;
        this.date = date;
        this.status = status;
    }


    public enum Status {
        ACTIVE,
        CANCELLED
    }

    public UUID getResourceId() {
        return resourceId;
    }
    public LocalDate getDate() {
        return date;
    }
    public Status getStatus() {
        return status;
    }
}
