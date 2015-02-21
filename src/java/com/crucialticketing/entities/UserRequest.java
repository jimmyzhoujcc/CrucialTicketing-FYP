/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DanFoley
 */
public class UserRequest {

    private int userRequestId;
    private User user;
    private List<UserRoleConRequest> userRoleConRequestList;
    private Ticket ticket;
    private User requestor;

    public UserRequest() {
        this.user = new User();
        this.userRoleConRequestList = new ArrayList<>();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public UserRequest(User user, Ticket ticket, User requestor) {
        this.user = user;
        this.ticket = ticket;
        this.requestor = requestor;
    }

    public int getUserRequestId() {
        return userRequestId;
    }

    public void setUserRequestId(int userRequestId) {
        this.userRequestId = userRequestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserRoleConRequest> getUserRoleConRequestList() {
        return userRoleConRequestList;
    }

    public void setUserRoleConRequestList(List<UserRoleConRequest> userRoleConRequestList) {
        this.userRoleConRequestList = userRoleConRequestList;
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
