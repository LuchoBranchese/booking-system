package com.lbranchese.BookingSystem.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class User {

    private UUID id;
    private Role role;
    private LocalDate blockedUntil;

    private User(Role role){

        if (role == null){
            throw new RuntimeException();
        }

        this.id = UUID.randomUUID();
        this.role = role;
    }

    protected User(){

    }

    public static User create(Role role){
        User user = new User(role);
        return user;
    }

    public static User rehydrate(UUID id, LocalDate penalizedUntil, Role role) {
        User user = new User();
        user.id = id;
        user.blockedUntil = penalizedUntil;
        user.role = role;
        return user;
    }

    public boolean canReserve(LocalDate today){
        if (this.blockedUntil != null && !today.isAfter(this.blockedUntil) ){
            return false;
        }
        return true;
    }

    public boolean isAdmin(){
        if (this.role == Role.ADMIN){
            return true;
        }
        else return false;
    }

    public Role getRole(){
        return this.role;
    }


    public UUID getId(){
        return this.id;
    }

    public void applyPenalty(LocalDate date){
        this.blockedUntil = date.plusDays(3);
    }

    public enum Role {ADMIN, USER}

    public LocalDate getBlockedUntil(){
        return this.blockedUntil;
    }
}
