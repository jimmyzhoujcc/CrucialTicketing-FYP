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
public class RoleChangeLog {

    private int roleChangeLogId;
    private User user;
    private int stamp;
    private int flag;
    private Ticket ticket;

    public RoleChangeLog() {
    }

    public RoleChangeLog(int roleChangeLogId, User user, int stamp, int flag, Ticket ticket) {
        this.roleChangeLogId = roleChangeLogId;
        this.user = user;
        this.stamp = stamp;
        this.flag = flag;
        this.ticket = ticket;
    }

    public int getRoleChangeLogId() {
        return roleChangeLogId;
    }

    public void setRoleChangeLogId(int roleChangeLogId) {
        this.roleChangeLogId = roleChangeLogId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
