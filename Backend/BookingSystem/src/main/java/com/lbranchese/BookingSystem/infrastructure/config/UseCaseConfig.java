package com.lbranchese.BookingSystem.infrastructure.config;

import com.lbranchese.BookingSystem.application.command.usecase.CancelReservation;
import com.lbranchese.BookingSystem.application.command.usecase.CreateReservation;
import com.lbranchese.BookingSystem.application.port.command.ReservationRepository;
import com.lbranchese.BookingSystem.application.port.command.ResourceRepository;
import com.lbranchese.BookingSystem.application.port.command.UserRepository;
import com.lbranchese.BookingSystem.application.port.query.ReservationQueryRepository;
import com.lbranchese.BookingSystem.application.query.usecase.GetReservationsByResource;
import com.lbranchese.BookingSystem.application.query.usecase.GetReservationsByUser;
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

    @Bean
    public CancelReservation cancelReservation(
            UserRepository userRepository,
            ReservationRepository reservationRepository
    ) {
        return new CancelReservation(
                userRepository,
                reservationRepository
        );
    }

    @Bean
    public GetReservationsByUser getReservationsByUser(
            ReservationQueryRepository reservationQueryRepository
    ) {
        return new GetReservationsByUser(reservationQueryRepository);
    }

    @Bean
    public GetReservationsByResource getReservationsByResource(
            ReservationQueryRepository reservationQueryRepository
    ) {
        return new GetReservationsByResource(reservationQueryRepository);
    }
}