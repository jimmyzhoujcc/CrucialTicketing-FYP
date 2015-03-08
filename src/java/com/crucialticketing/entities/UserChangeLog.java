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
public class UserChangeLog {

    private int userChangeLogId;
    private User user;
    
    private Ticket ticket;
    
    private User requestor;
    private int stamp;

    public UserChangeLog() {
        this.user = new User();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public UserChangeLog(User user, Ticket ticket, User requestor, int stamp) {
        this.user = user;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
    }

    public int getUserChangeLogId() {
        return userChangeLogId;
    }

    public void setUserChangeLogId(int userChangeLogId) {
        this.userChangeLogId = userChangeLogId;
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

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

    
}
