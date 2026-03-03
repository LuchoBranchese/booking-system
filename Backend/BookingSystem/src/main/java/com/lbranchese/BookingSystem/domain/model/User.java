package com.lbranchese.BookingSystem.domain.model;

import tools.jackson.databind.DatabindException;

import java.time.LocalDate;
import java.util.UUID;

public class User {

    private UUID id;
    private Rol rol;
    private LocalDate bloqueadoHasta;

    private User(Rol rol){

        if (rol==null){
            throw new RuntimeException();
        }

        this.id = UUID.randomUUID();
        this.rol = rol;
    }

    public static User create(Rol rol){
        User user = new User(rol);
        return user;
    }

    public boolean puedeReservar(LocalDate hoy){
        if (this.bloqueadoHasta!=null && !hoy.isAfter(this.bloqueadoHasta) ){
            return false;
        }
        return true;
    }

    public boolean isAdmin(){
        if (this.rol == Rol.ADMIN){
            return true;
        }
        else return false;
    }

    public UUID getId(){
        return this.id;
    }

    public void aplicarPenalizacion(LocalDate fecha){
        this.bloqueadoHasta = fecha.plusDays(3);
    }

    public enum Rol{ADMIN, USER}

}
