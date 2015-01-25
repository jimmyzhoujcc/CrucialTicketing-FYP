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
public class LockEntry {
    private int lockId;
    private int ticketId;
    private User user;
    private int timeLocked;

    public LockEntry() {}
    
    public LockEntry(int lockId, int ticketId, User user, int timeLocked) {
        this.lockId = lockId;
        this.ticketId = ticketId;
        this.user = user;
        this.timeLocked = timeLocked;
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

    public int getTimeLocked() {
        return timeLocked;
    }

    public void setTimeLocked(int timeLocked) {
        this.timeLocked = timeLocked;
    }
}
