package com.lbranchese.BookingSystem.application.port.command;

import com.lbranchese.BookingSystem.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional <User> findById (UUID id);

//    void save (User user);

}
