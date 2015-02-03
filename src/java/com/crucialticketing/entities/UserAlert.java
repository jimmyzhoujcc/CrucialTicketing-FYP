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
public class UserAlert {
    private int userAlertId;
    private int userId;
    private int ticketId;
    private String message;
    private int stamp;
    private boolean read;

    public UserAlert() {
    }

    public UserAlert(int userAlertId, int userId, int ticketId, String message, int stamp, boolean read) {
        this.userAlertId = userAlertId;
        this.userId = userId;
        this.ticketId = ticketId;
        this.message = message;
        this.stamp = stamp;
        this.read = read;
    }

    public int getUserAlertId() {
        return userAlertId;
    }

    public void setUserAlertId(int userAlertId) {
        this.userAlertId = userAlertId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
    
    
    
    
}
