package com.lbranchese.BookingSystem.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void create_shouldThrow_whenEstadoIsNull() {
        assertThrows(RuntimeException.class, () -> Resource.create(null));
    }

    @Test
    void create_shouldCreateResourceWithGivenEstado() {
        Resource resourceActivo = Resource.create(Resource.Estado.ACTIVO);
        Resource resourceInactivo = Resource.create(Resource.Estado.INACTIVO);

        assertTrue(resourceActivo.estaActivo());
        assertFalse(resourceInactivo.estaActivo());
    }

    @Test
    void estaActivo_shouldReturnTrueOnlyWhenEstadoEsActivo() {
        Resource resource = Resource.create(Resource.Estado.ACTIVO);
        assertTrue(resource.estaActivo());
    }

    @Test
    void cambiarEstado_shouldChangeEstado_whenSolicitanteEsAdmin() {
        User admin = User.create(User.Rol.ADMIN);
        Resource resource = Resource.create(Resource.Estado.ACTIVO);

        resource.cambiarEstado(admin, Resource.Estado.INACTIVO);

        assertFalse(resource.estaActivo());
    }

    @Test
    void cambiarEstado_shouldThrow_whenSolicitanteNoEsAdmin() {
        User user = User.create(User.Rol.USER);
        Resource resource = Resource.create(Resource.Estado.ACTIVO);

        assertThrows(RuntimeException.class,
                () -> resource.cambiarEstado(user, Resource.Estado.INACTIVO));
    }

}

