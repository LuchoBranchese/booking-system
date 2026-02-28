package com.lbranchese.BookingSystem.domain.model;

import java.util.UUID;

public class Resource {

    private UUID id;
    private Estado estado;

    private Resource(Estado estado){
        this.id = UUID.randomUUID();
        this.estado= estado;
    }

    public static Resource create(Estado estado){
        if (estado == null){
            throw new RuntimeException();
        }
        Resource resource = new Resource(estado);
        return resource;
    }

    public boolean estaActivo(){
        if (this.estado == Estado.ACTIVO){
            return true;
        }
        return false;
    }

    public void cambiarEstado(User solicitante, Estado nuevoEstado){

        if (solicitante.isAdmin()){
            this.estado = nuevoEstado;

        }
        else throw new RuntimeException();

    }

    public enum Estado {ACTIVO, INACTIVO}
}
