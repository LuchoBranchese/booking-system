package com.lbranchese.BookingSystem.infrastructure.config;

import com.lbranchese.BookingSystem.application.command.usecase.CreateReservation;
import com.lbranchese.BookingSystem.application.port.command.ReservationRepository;
import com.lbranchese.BookingSystem.application.port.command.ResourceRepository;
import com.lbranchese.BookingSystem.application.port.command.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateReservation createReservation(
            UserRepository userRepository,
            ResourceRepository resourceRepository,
            ReservationRepository reservationRepository
    ) {
        return new CreateReservation(
                userRepository,
                resourceRepository,
                reservationRepository
        );
    }
}