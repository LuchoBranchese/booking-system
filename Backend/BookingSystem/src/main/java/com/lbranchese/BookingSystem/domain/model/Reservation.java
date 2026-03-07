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
    private Status status;

    private Reservation(UUID userId, UUID resourceId, LocalDate date){

        this.id = UUID.randomUUID();
        this.userId = userId;
        this.resourceId = resourceId;
        this.date = date;
        this.status = Status.ACTIVE;
    }

    protected Reservation() {}

    public static Reservation create(UUID userId, UUID resourceId, LocalDate date, LocalDate today){
        if (date.isBefore(today)){
            throw new RuntimeException();
        }
        return new Reservation(userId, resourceId, date);
    }

    public static Reservation rehydrate(UUID id, UUID userId, UUID resourceId, LocalDate date, Status status) {
        Reservation reservation = new Reservation();
        reservation.id = id;
        reservation.userId = userId;
        reservation.resourceId = resourceId;
        reservation.date = date;
        reservation.status = status;
        return reservation;
    }

    public boolean cancel(UUID actorId, boolean isAdmin, LocalDate today){
        if (!this.isActive()){
            throw new RuntimeException();
        }
        if (today.isAfter(this.date)){
            throw new RuntimeException();
        }

        if (!actorId.equals(this.userId) && !isAdmin) {
            throw new RuntimeException();
        }


        this.status = Status.CANCELLED;

        return today.isEqual(this.date);
    }

    public boolean isActive(){
        return this.status == Status.ACTIVE;
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


    public enum Status {ACTIVE, CANCELLED;
    }

    public Status getStatus() {
        return this.status;
    }
}
