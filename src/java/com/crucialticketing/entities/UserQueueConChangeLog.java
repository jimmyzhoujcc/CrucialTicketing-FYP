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
public class UserQueueConChangeLog {
    private int userQueueConChangeLogId;
    private UserQueueCon userQueueCon;
    
    private Ticket ticket;
    private User requestor;
    private int stamp;

    public UserQueueConChangeLog() {
        this.userQueueCon = new UserQueueCon();
        this.ticket = new Ticket();
        this.requestor = new User();
    }
    
    public UserQueueConChangeLog(UserQueueCon userQueueCon, Ticket ticket, User requestor, int stamp) {
        this.userQueueCon = userQueueCon;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
    }

    public int getUserQueueConChangeLogId() {
        return userQueueConChangeLogId;
    }

    public void setUserQueueConChangeLogId(int userQueueConChangeLogId) {
        this.userQueueConChangeLogId = userQueueConChangeLogId;
    }

    public UserQueueCon getUserQueueCon() {
        return userQueueCon;
    }

    public void setUserQueueCon(UserQueueCon userQueueCon) {
        this.userQueueCon = userQueueCon;
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

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }
    
    
    
}
