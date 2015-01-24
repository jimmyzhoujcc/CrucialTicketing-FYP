/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author Owner
 */
public class Ticket {
    private String ticketId;
    
    private ApplicationControl applicationControl;
    
    private String shortDescription;
    
    private WorkflowStage currentWorkflowStage;
    
    private User messageProcessor;
    private User createdBy;
    private User reportedBy;
    
    private int timeDateCreated;
    private int timeDateChanged;
    private int timeDateClosed;
    private int timeDateReopened;  
    
    private TicketLog ticketLog;
    
    private boolean lock;
    
    public Ticket() {}

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public ApplicationControl getApplicationControl() {
        return applicationControl;
    }

    public void setApplicationControl(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public WorkflowStage getCurrentWorkflowStage() {
        return currentWorkflowStage;
    }

    public void setCurrentWorkflowStage(WorkflowStage workflowStage) {
        this.currentWorkflowStage = workflowStage;
    }

    public User getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(User messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public int getTimeDateCreated() {
        return timeDateCreated;
    }

    public void setTimeDateCreated(int timeDateCreated) {
        this.timeDateCreated = timeDateCreated;
    }

    public int getTimeDateChanged() {
        return timeDateChanged;
    }

    public void setTimeDateChanged(int timeDateChanged) {
        this.timeDateChanged = timeDateChanged;
    }

    public int getTimeDateClosed() {
        return timeDateClosed;
    }

    public void setTimeDateClosed(int timeDateClosed) {
        this.timeDateClosed = timeDateClosed;
    }

    public int getTimeDateReopened() {
        return timeDateReopened;
    }

    public void setTimeDateReopened(int timeDateReopened) {
        this.timeDateReopened = timeDateReopened;
    } 

    public TicketLog getTicketLog() {
        return ticketLog;
    }

    public void setTicketLog(TicketLog ticketLog) {
        this.ticketLog = ticketLog;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
    
    
    
}
