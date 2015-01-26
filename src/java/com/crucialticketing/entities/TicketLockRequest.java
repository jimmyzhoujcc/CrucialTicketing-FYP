/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author DanFoley
 */
public class TicketLockRequest {
    private int lockId;
    private int ticketId;
    private User user;
    private int requestTime;
    private int requestPassTime;
    
    public TicketLockRequest() {}

    public TicketLockRequest(int lockId, int ticketId, User user, int requestTime, int requestPassTime) {
        this.lockId = lockId;
        this.ticketId = ticketId;
        this.user = user;
        this.requestTime = requestTime;
        this.requestPassTime = requestPassTime;
    }

    public int getLockId() {
        return lockId;
    }

    public void setLockId(int lockId) {
        this.lockId = lockId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequestPassTime() {
        return requestPassTime;
    }

    public void setRequestPassTime(int requestPassTime) {
        this.requestPassTime = requestPassTime;
    }
    
    
}
