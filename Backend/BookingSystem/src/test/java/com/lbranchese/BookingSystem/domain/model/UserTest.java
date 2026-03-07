package com.lbranchese.BookingSystem.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void create_shouldThrow_whenRoleIsNull() {
        assertThrows(RuntimeException.class, () -> User.create(null));
    }

    @Test
    void isAdmin_shouldReturnTrueOnlyForAdminRole() {
        User admin = User.create(User.Role.ADMIN);
        User user = User.create(User.Role.USER);

        assertTrue(admin.isAdmin());
        assertFalse(user.isAdmin());
    }

    @Test
    void canReserve_shouldReturnTrue_whenUserIsNotBlocked() {
        User user = User.create(User.Role.USER);
        LocalDate today = LocalDate.now();

        assertTrue(user.canReserve(today));
    }

    @Test
    void canReserve_shouldReturnFalse_untilThreeDaysAfterPenalty() {
        User user = User.create(User.Role.USER);
        LocalDate today = LocalDate.now();

        user.applyPenalty(today);

        assertFalse(user.canReserve(today));
        assertFalse(user.canReserve(today.plusDays(1)));
        assertFalse(user.canReserve(today.plusDays(2)));
        assertFalse(user.canReserve(today.plusDays(3)));
        assertTrue(user.canReserve(today.plusDays(4)));
    }

}

