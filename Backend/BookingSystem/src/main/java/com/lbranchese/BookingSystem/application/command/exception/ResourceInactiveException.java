package com.lbranchese.BookingSystem.application.command.exception;

import java.util.UUID;

public class ResourceInactiveException extends RuntimeException {

    public ResourceInactiveException(UUID resourceId) {
        super("Resource with id " + resourceId + " is not active");
    }
}

