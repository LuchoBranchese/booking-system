package com.lbranchese.BookingSystem.application.command.exception;

import java.time.LocalDate;
import java.util.UUID;

public class UserBlockedException extends RuntimeException {

    public UserBlockedException(UUID userId, LocalDate blockedUntil) {
        super("User with id " + userId + " is blocked until " + blockedUntil);
    }
}

