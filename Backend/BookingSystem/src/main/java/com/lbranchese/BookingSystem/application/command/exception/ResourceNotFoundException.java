package com.lbranchese.BookingSystem.application.command.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(UUID resourceId) {
        super("Resource with id " + resourceId + " was not found");
    }
}

