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
    private Role role;
    private int activeFlag;
    private Ticket ticket;
    private User requestor;
    private int stamp;
    

    public RoleChangeLog() {
        this.role = new Role();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public RoleChangeLog(Role role, int activeFlag, Ticket ticket, User requestor, int stamp) {
        this.role = role;
        this.activeFlag = activeFlag;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
    }

    public int getRoleChangeLogId() {
        return roleChangeLogId;
    }

    public void setRoleChangeLogId(int roleChangeLogId) {
        this.roleChangeLogId = roleChangeLogId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
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
