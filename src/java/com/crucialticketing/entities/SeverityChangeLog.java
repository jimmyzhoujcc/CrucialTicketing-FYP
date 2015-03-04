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
public class SeverityChangeLog {
    private int severityChangeLogId;
    private Severity severity;
    
    private int severityLevel;
    private String severityName;
    
    private Ticket ticket;
    private User requestor;
    
    private int stamp;
    
    private ActiveFlag activeFlag;

    public SeverityChangeLog() {
        this.severity = new Severity();
        this.ticket = new Ticket();
        this.requestor = new User();
    }
    
    public SeverityChangeLog(Severity severity, Ticket ticket, User requestor, int stamp, ActiveFlag activeFlag) {
        this.severity = severity;
        this.severityLevel = severityLevel;
        this.severityName = severityName;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
        this.activeFlag = activeFlag;
    }

    public int getSeverityChangeLogId() {
        return severityChangeLogId;
    }

    public void setSeverityChangeLogId(int severityChangeLogId) {
        this.severityChangeLogId = severityChangeLogId;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getSeverityName() {
        return severityName;
    }

    public void setSeverityName(String severityName) {
        this.severityName = severityName;
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
