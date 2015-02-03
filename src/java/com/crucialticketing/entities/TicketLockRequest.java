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
    private int userId;
    private int requestTime;
    private int requestPassTime;
    
    public TicketLockRequest() {}

    public TicketLockRequest(int lockId, int ticketId, int userId, int requestTime, int requestPassTime) {
        this.lockId = lockId;
        this.ticketId = ticketId;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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