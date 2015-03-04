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
    
    private Ticket ticket;
    private User requestor;
    private int stamp;
    
    private ActiveFlag activeFlag;

    public RoleChangeLog() {
        this.role = new Role();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public RoleChangeLog(Role role, Ticket ticket, User requestor, int stamp, ActiveFlag activeFlag) {
        this.role = role;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
        this.activeFlag = activeFlag;
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

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    

   
}
