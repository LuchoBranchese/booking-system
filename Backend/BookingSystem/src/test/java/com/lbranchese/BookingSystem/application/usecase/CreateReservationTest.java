package com.lbranchese.BookingSystem.application.usecase;

import com.lbranchese.BookingSystem.application.command.usecase.CreateReservation;
import com.lbranchese.BookingSystem.application.port.command.ReservationRepository;
import com.lbranchese.BookingSystem.application.port.command.ResourceRepository;
import com.lbranchese.BookingSystem.application.port.command.UserRepository;
import com.lbranchese.BookingSystem.domain.model.Reservation;
import com.lbranchese.BookingSystem.domain.model.Resource;
import com.lbranchese.BookingSystem.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateReservationTest {

    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private ResourceRepository resourceRepository;

    private CreateReservation useCase;

    @BeforeEach
    void setup() {
        reservationRepository = Mockito.mock(ReservationRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        resourceRepository = Mockito.mock(ResourceRepository.class);

        useCase = new CreateReservation(
                userRepository,
                resourceRepository,
                reservationRepository
        );
    }

    @Test
    void shouldCreateReservationSuccessfully() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate date = LocalDate.now().plusDays(1);

        User user = User.create(User.Role.USER);
        Resource resource = Resource.create(Resource.Status.ACTIVE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        when(reservationRepository.existsByResourceAndDate(resourceId, date)).thenReturn(false);

        LocalDate today = LocalDate.now();
        useCase.execute(userId, resourceId, date, today);

        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void shouldFailIfUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate date = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId, resourceId, date, today)
        );

        verifyNoInteractions(resourceRepository);
        verifyNoInteractions(reservationRepository);
    }

    @Test
    void shouldFailIfResourceDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate date = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();

        User user = User.create(User.Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId, resourceId, date, today)
        );

        verify(reservationRepository, never()).save(any());
    }

    @Test
    void shouldFailIfResourceAlreadyReserved() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate date = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();

        User user = User.create(User.Role.USER);
        Resource resource = Resource.create(Resource.Status.ACTIVE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        when(reservationRepository.existsByResourceAndDate(resourceId, date)).thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId, resourceId, date, today)
        );

        verify(reservationRepository, never()).save(any());
    }
}

