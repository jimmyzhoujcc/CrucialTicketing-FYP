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
public class ChangeLogEntry {
    private int changeLogEntryId;
    private Ticket ticket;
    private ApplicationControl applicationControl;
    private User user;
    private WorkflowStatus workflowStatus;
    private int stamp;
    
    public ChangeLogEntry() {
        this.ticket = new Ticket();
        this.applicationControl = new ApplicationControl();
        this.user = new User();
        this.workflowStatus = new WorkflowStatus();
    }

    public int getChangeLogEntryId() {
        return changeLogEntryId;
    }

    public void setChangeLogEntryId(int changeLogEntryId) {
        this.changeLogEntryId = changeLogEntryId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public ApplicationControl getApplicationControl() {
        return applicationControl;
    }

    public void setApplicationControl(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WorkflowStatus getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(WorkflowStatus workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }
    
    
}
