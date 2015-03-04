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
    
    private String hash;
    private String emailAddress;
    private String contact;
    
    private Ticket ticket;
    
    private User requestor;
    private int stamp;
    
    private ActiveFlag activeFlag;

    public UserChangeLog() {
        this.user = new User();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public UserChangeLog(User user, String hash, String emailAddress, String contact, Ticket ticket, User requestor, int stamp, ActiveFlag activeFlag) {
        this.user = user;
        this.hash = hash;
        this.emailAddress = emailAddress;
        this.contact = contact;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
        this.activeFlag = activeFlag;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
