package com.lbranchese.BookingSystem.domain.model;

import java.util.UUID;

public class Resource {

    private UUID id;
    private Status status;

    private Resource(Status status){
        this.id = UUID.randomUUID();
        this.status = status;
    }

    protected Resource (){

    }

    public static Resource create(Status status){
        if (status == null){
            throw new RuntimeException();
        }
        Resource resource = new Resource(status);
        return resource;
    }

    public static Resource rehydrate(UUID id, Status status){
        Resource resource = new Resource();
        resource.id = id;
        resource.status = status;
        return resource;
    }

    public boolean isActive(){
        if (this.status == Status.ACTIVE){
            return true;
        }
        return false;
    }

    public void changeStatus(User requester, Status newStatus){

        if (requester.isAdmin()){
            this.status = newStatus;

        }
        else throw new RuntimeException();

    }

    public enum Status {ACTIVE, INACTIVE}
}
