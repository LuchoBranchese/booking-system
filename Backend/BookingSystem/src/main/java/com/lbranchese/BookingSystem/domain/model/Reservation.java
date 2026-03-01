package com.lbranchese.BookingSystem.domain.model;

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

    public static Reservation create(UUID userId, UUID resourceId, LocalDate fecha, LocalDate hoy){
        if (fecha.isBefore(hoy)){
            throw new RuntimeException();
        }
        return new Reservation(userId, resourceId, fecha);
    }

    public boolean cancelar(LocalDate hoy){
        if (this.status!=Estado.ACTIVE){
            throw new RuntimeException();
        }
        if (hoy.isAfter(this.date)){
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

    public enum Estado{ACTIVE, CANCELLED}
}
