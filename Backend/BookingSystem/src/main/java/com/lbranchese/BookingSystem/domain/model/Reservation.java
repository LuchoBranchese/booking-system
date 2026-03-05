package com.lbranchese.BookingSystem.domain.model;

import org.jspecify.annotations.Nullable;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

public class Reservation {
    private UUID id;
    private UUID userId;
    private UUID resourceId;
    private LocalDate date;
    private Estado status;

    private Reservation(UUID userId, UUID resourceId, LocalDate fecha){

        this.id = UUID.randomUUID();
        this.userId = userId;
        this.resourceId = resourceId;
        this.date = fecha;
        this.status = Estado.ACTIVE;
    }

    protected Reservation() {}

    public static Reservation create(UUID userId, UUID resourceId, LocalDate fecha, LocalDate hoy){
        if (fecha.isBefore(hoy)){
            throw new RuntimeException();
        }
        return new Reservation(userId, resourceId, fecha);
    }

    public static Reservation rehydrate(UUID id, UUID userId, UUID resourceId, LocalDate date, Estado status) {
        Reservation reservation = new Reservation();
        reservation.id = id;
        reservation.userId = userId;
        reservation.resourceId = resourceId;
        reservation.date = date;
        reservation.status = status;
        return reservation;
    }

    public boolean cancelar(UUID actorId, boolean esAdmin, LocalDate hoy){
        if (!this.isActive()){
            throw new RuntimeException();
        }
        if (hoy.isAfter(this.date)){
            throw new RuntimeException();
        }

        if (!actorId.equals(this.userId) && !esAdmin) {
            throw new RuntimeException();
        }


        this.status = Estado.CANCELLED;

        return hoy.isEqual(this.date);
    }

    public boolean isActive(){
        return this.status == Estado.ACTIVE;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public UUID getResourceId() {
        return resourceId;
    }


    public enum Estado{ACTIVE, CANCELLED;
    }

    public Estado getStatus() {
        return this.status;
    }
}
