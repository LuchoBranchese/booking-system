package com.lbranchese.BookingSystem.application.query.model;


import java.time.LocalDate;
import java.util.UUID;

public class ReservationByResourceSummary {

    private final UUID userId;
    private final LocalDate date;
    private final Status status;

    public ReservationByResourceSummary(UUID userId, LocalDate date, Status status) {
        this.userId = userId;
        this.date = date;
        this.status = status;
    }

    public enum Status {
        ACTIVE,
        CANCELLED;

        public static Status fromDb(String value) {
            return Status.valueOf(value.toUpperCase());
        }
    }

    public UUID getUserId() {
        return userId;
    }
    public LocalDate getDate() {
        return date;
    }
    public Status getStatus() {
        return status;
    }

}
