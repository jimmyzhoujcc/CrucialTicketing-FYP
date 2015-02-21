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
public class UserRoleConRequest {

    private int userRoleConRequestId;
    private User user;
    private Role role;
    private int validFrom;
    private int validTo;
    private Ticket ticket;
    private User requestor;

    public UserRoleConRequest() {
        this.user = new User();
        this.role = new Role();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public UserRoleConRequest(int userRoleConRequestId, User user, Role role, int validFrom, int validTo, Ticket ticket, User requestor) {
        this.userRoleConRequestId = userRoleConRequestId;
        this.user = user;
        this.role = role;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.ticket = ticket;
        this.requestor = requestor;
    }

    public int getUserRoleConRequestId() {
        return userRoleConRequestId;
    }

    public void setUserRoleConRequestId(int userRoleConRequestId) {
        this.userRoleConRequestId = userRoleConRequestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(int validFrom) {
        this.validFrom = validFrom;
    }

    public int getValidTo() {
        return validTo;
    }

    public void setValidTo(int validTo) {
        this.validTo = validTo;
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
}
