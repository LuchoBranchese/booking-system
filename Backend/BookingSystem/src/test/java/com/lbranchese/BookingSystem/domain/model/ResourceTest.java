package com.lbranchese.BookingSystem.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void create_shouldThrow_whenStatusIsNull() {
        assertThrows(RuntimeException.class, () -> Resource.create(null));
    }

    @Test
    void create_shouldCreateResourceWithGivenStatus() {
        Resource activeResource = Resource.create(Resource.Status.ACTIVE);
        Resource inactiveResource = Resource.create(Resource.Status.INACTIVE);

        assertTrue(activeResource.isActive());
        assertFalse(inactiveResource.isActive());
    }

    @Test
    void isActive_shouldReturnTrueOnlyWhenStatusIsActive() {
        Resource resource = Resource.create(Resource.Status.ACTIVE);
        assertTrue(resource.isActive());
    }

    @Test
    void changeStatus_shouldChangeStatus_whenRequesterIsAdmin() {
        User admin = User.create(User.Role.ADMIN);
        Resource resource = Resource.create(Resource.Status.ACTIVE);

        resource.changeStatus(admin, Resource.Status.INACTIVE);

        assertFalse(resource.isActive());
    }

    @Test
    void changeStatus_shouldThrow_whenRequesterIsNotAdmin() {
        User user = User.create(User.Role.USER);
        Resource resource = Resource.create(Resource.Status.ACTIVE);

        assertThrows(RuntimeException.class,
                () -> resource.changeStatus(user, Resource.Status.INACTIVE));
    }

}

