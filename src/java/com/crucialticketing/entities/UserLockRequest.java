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
public class UserLockRequest {

    private int lockRequestId;
    private User user;
    private Ticket ticket;
    private User requestor;
    private int requestTime;
    private int requestPassTime;

    public UserLockRequest() {
        this.user = new User();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public UserLockRequest(User user, User requestor) {
        this.user = user;
        this.requestor = requestor;
    }

    public int getLockRequestId() {
        return lockRequestId;
    }

    public void setLockRequestId(int lockRequestId) {
        this.lockRequestId = lockRequestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getRequestor() {
        return requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
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
