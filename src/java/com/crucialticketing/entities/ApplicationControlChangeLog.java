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
public class ApplicationControlChangeLog {
    private int applicationControlChangeLogId;
    private ApplicationControl applicationControl;

    private Ticket ticket;
    private User requestor;
    
    private int stamp;

    private ActiveFlag activeFlag;
    
    public ApplicationControlChangeLog() {
        this.applicationControl = new ApplicationControl();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public ApplicationControlChangeLog(ApplicationControl applicationControl, Ticket ticket, User requestor, int stamp, ActiveFlag activeFlag) {
        this.applicationControl = applicationControl;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
        this.activeFlag = activeFlag;
    }

    public int getApplicationControlChangeLogId() {
        return applicationControlChangeLogId;
    }

    public void setApplicationControlChangeLogId(int applicationControlChangeLogId) {
        this.applicationControlChangeLogId = applicationControlChangeLogId;
    }

    public ApplicationControl getApplicationControl() {
        return applicationControl;
    }

    public void setApplicationControl(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
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
