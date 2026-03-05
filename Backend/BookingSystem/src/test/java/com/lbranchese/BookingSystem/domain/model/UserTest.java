package com.lbranchese.BookingSystem.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void create_shouldThrow_whenRolIsNull() {
        assertThrows(RuntimeException.class, () -> User.create(null));
    }

    @Test
    void isAdmin_shouldReturnTrueOnlyForAdminRole() {
        User admin = User.create(User.Rol.ADMIN);
        User user = User.create(User.Rol.USER);

        assertTrue(admin.isAdmin());
        assertFalse(user.isAdmin());
    }

    @Test
    void puedeReservar_shouldReturnTrue_whenUserIsNotBlocked() {
        User user = User.create(User.Rol.USER);
        LocalDate today = LocalDate.now();

        assertTrue(user.puedeReservar(today));
    }

    @Test
    void puedeReservar_shouldReturnFalse_untilThreeDaysAfterPenalizacion() {
        User user = User.create(User.Rol.USER);
        LocalDate today = LocalDate.now();

        user.aplicarPenalizacion(today);

        assertFalse(user.puedeReservar(today));
        assertFalse(user.puedeReservar(today.plusDays(1)));
        assertFalse(user.puedeReservar(today.plusDays(2)));
        assertFalse(user.puedeReservar(today.plusDays(3)));
        assertTrue(user.puedeReservar(today.plusDays(4)));
    }

}

