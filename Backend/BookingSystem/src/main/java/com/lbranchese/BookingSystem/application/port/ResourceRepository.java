package com.lbranchese.BookingSystem.application.port;

import com.lbranchese.BookingSystem.domain.model.Resource;

import java.util.Optional;
import java.util.UUID;

public interface ResourceRepository {

    Optional <Resource> findById(UUID id);

}
