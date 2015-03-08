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
public class ApplicationChangeLog {

    private int applicationChangeLogId;
    private Application application;

    private Ticket ticket;
    private User requestor;
    private int stamp;
   
    public ApplicationChangeLog() {
        this.application = new Application();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public ApplicationChangeLog(Application application, Ticket ticket, User requestor, int stamp) {
        this.application = application;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
    }
    
    

    public int getApplicationChangeLogId() {
        return applicationChangeLogId;
    }

    public void setApplicationChangeLogId(int applicationChangeLogId) {
        this.applicationChangeLogId = applicationChangeLogId;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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
