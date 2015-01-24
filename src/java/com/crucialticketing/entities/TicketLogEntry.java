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
public class TicketLogEntry {
    private int ticketId;
    private int ticketLogId;
    private User user;
    private String logEntry;
    private int stamp;
    
    public TicketLogEntry() {}

    public TicketLogEntry(int ticketId, int ticketLogId, User user, String logEntry, int entryStamp) {
        this.ticketId = ticketId;
        this.ticketLogId = ticketLogId;
        this.user = user;
        this.logEntry = logEntry;
        this.stamp = entryStamp;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    
    public int getTicketLogId() {
        return ticketLogId;
    }

    public void setTicketLogId(int ticketLogId) {
        this.ticketLogId = ticketLogId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public void setLogEntry(String logEntry) {
        this.logEntry = logEntry;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

    
    
}
