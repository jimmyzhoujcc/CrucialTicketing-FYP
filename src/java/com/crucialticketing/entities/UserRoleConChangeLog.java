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
public class UserRoleConChangeLog {
     private int userRoleConChangeLogId;
    private UserRoleCon userRoleCon;
    
    private Ticket ticket;
    private User requestor;
    private int stamp;
    
    public UserRoleConChangeLog() {
        this.userRoleCon = new UserRoleCon();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public UserRoleConChangeLog(UserRoleCon userRoleCon, Ticket ticket, User requestor, int stamp) {
        this.userRoleCon = userRoleCon;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
    }

    public int getUserRoleConChangeLogId() {
        return userRoleConChangeLogId;
    }

    public void setUserRoleConChangeLogId(int userRoleConChangeLogId) {
        this.userRoleConChangeLogId = userRoleConChangeLogId;
    }

    public UserRoleCon getUserRoleCon() {
        return userRoleCon;
    }

    public void setUserRoleCon(UserRoleCon userRoleCon) {
        this.userRoleCon = userRoleCon;
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
