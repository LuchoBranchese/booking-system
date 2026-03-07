package com.lbranchese.BookingSystem.application.command.exception;

import java.time.LocalDate;
import java.util.UUID;

public class ResourceAlreadyReservedException extends RuntimeException {

    public ResourceAlreadyReservedException(UUID resourceId, LocalDate date) {
        super("Resource with id " + resourceId + " is already reserved for date " + date);
    }
}

