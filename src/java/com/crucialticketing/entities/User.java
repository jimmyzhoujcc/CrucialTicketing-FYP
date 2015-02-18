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
 * @author Daniel Foley
 */
public class User {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private Secure secure;
    private List<UserRoleConnection> roleList;
    private String emailAddress;
    private String contact;
    private Ticket ticket;
    private int activatedTime;
    private int deactivatedTime;
    

    public User() {
        this.ticket = new Ticket();
        this.secure = new Secure();
        this.roleList = new ArrayList<>();
    }

    public User(int userId) {
        this.userId = userId;
    }
    
    public User(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setRoleList(List<UserRoleConnection> roleList) {
        this.roleList = roleList;
    }
    
    public List<UserRoleConnection> getRoleList() {
        return this.roleList;
    }

    public boolean hasRole(String role) {
        for (UserRoleConnection roleCon : roleList) {
            if(roleCon.getRole().getRoleName().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Secure getSecure() {
        return secure;
    }

    public void setSecure(Secure secure) {
        this.secure = secure;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public int getActivatedTime() {
        return activatedTime;
    }

    public void setActivatedTime(int activatedTime) {
        this.activatedTime = activatedTime;
    }

    public int getDeactivatedTime() {
        return deactivatedTime;
    }

    public void setDeactivatedTime(int deactivatedTime) {
        this.deactivatedTime = deactivatedTime;
    }

    
   
}
