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
public class RoleRequest {

    private int roleRequestId;
    private Role role;
    private User requestor;
    private Ticket ticket;

    public RoleRequest() {
        this.role = new Role();
        this.requestor = new User();
        this.ticket = new Ticket();
    }

    public RoleRequest(Role role, User requestor, Ticket ticket) {
        this.role = role;
        this.requestor = requestor;
        this.ticket = ticket;
    }

    public int getRoleRequestId() {
        return roleRequestId;
    }

    public void setRoleRequestId(int roleRequestId) {
        this.roleRequestId = roleRequestId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getRequestor() {
        return requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
