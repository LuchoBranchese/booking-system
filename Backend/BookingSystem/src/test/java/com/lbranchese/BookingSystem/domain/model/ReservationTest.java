package com.lbranchese.BookingSystem.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void create_shouldCreateActiveReservation_whenDateIsTodayOrInFuture() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(1);

        Reservation todayReservation = Reservation.create(userId, resourceId, today, today);
        Reservation futureReservation = Reservation.create(userId, resourceId, futureDate, today);

        assertNotNull(todayReservation.getId());
        assertEquals(userId, todayReservation.getUserId());
        assertEquals(resourceId, todayReservation.getResourceId());
        assertEquals(today, todayReservation.getDate());
        assertTrue(todayReservation.isActive());
        assertEquals(Reservation.Estado.ACTIVE, todayReservation.getStatus());

        assertNotNull(futureReservation.getId());
        assertEquals(futureDate, futureReservation.getDate());
        assertTrue(futureReservation.isActive());
    }

    @Test
    void create_shouldThrow_whenDateIsBeforeToday() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        LocalDate pastDate = today.minusDays(1);

        assertThrows(RuntimeException.class,
                () -> Reservation.create(userId, resourceId, pastDate, today));
    }

    @Test
    void cancelar_shouldCancel_whenActorIsOwnerBeforeReservationDate() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        LocalDate reservationDate = today.plusDays(1);

        Reservation reservation = Reservation.create(userId, resourceId, reservationDate, today);

        boolean sameDay = reservation.cancelar(userId, false, today);

        assertFalse(sameDay);
        assertFalse(reservation.isActive());
        assertEquals(Reservation.Estado.CANCELLED, reservation.getStatus());
    }

    @Test
    void cancelar_shouldCancel_whenActorIsAdminEvenIfNotOwner() {
        UUID ownerId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        LocalDate reservationDate = today.plusDays(2);

        Reservation reservation = Reservation.create(ownerId, resourceId, reservationDate, today);

        boolean sameDay = reservation.cancelar(adminId, true, today);

        assertFalse(sameDay);
        assertEquals(Reservation.Estado.CANCELLED, reservation.getStatus());
    }

    @Test
    void cancelar_shouldReturnTrue_whenCancelledSameDay() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate today = LocalDate.now();

        Reservation reservation = Reservation.create(userId, resourceId, today, today);

        boolean sameDay = reservation.cancelar(userId, false, today);

        assertTrue(sameDay);
        assertEquals(Reservation.Estado.CANCELLED, reservation.getStatus());
    }

    @Test
    void cancelar_shouldThrow_whenReservationIsNotActive() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        LocalDate reservationDate = today.plusDays(1);

        Reservation cancelledReservation = Reservation.rehydrate(
                UUID.randomUUID(),
                userId,
                resourceId,
                reservationDate,
                Reservation.Estado.CANCELLED
        );

        assertThrows(RuntimeException.class,
                () -> cancelledReservation.cancelar(userId, false, today));
    }

    @Test
    void cancelar_shouldThrow_whenTodayIsAfterReservationDate() {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate reservationDate = LocalDate.now();
        LocalDate afterReservationDate = reservationDate.plusDays(1);

        Reservation reservation = Reservation.create(userId, resourceId, reservationDate, reservationDate);

        assertThrows(RuntimeException.class,
                () -> reservation.cancelar(userId, false, afterReservationDate));
    }

    @Test
    void cancelar_shouldThrow_whenActorIsNotOwnerAndNotAdmin() {
        UUID ownerId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        LocalDate reservationDate = today.plusDays(1);

        Reservation reservation = Reservation.create(ownerId, resourceId, reservationDate, today);

        assertThrows(RuntimeException.class,
                () -> reservation.cancelar(otherUserId, false, today));
    }

}

