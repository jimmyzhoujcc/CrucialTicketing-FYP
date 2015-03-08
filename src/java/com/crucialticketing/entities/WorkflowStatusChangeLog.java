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
public class WorkflowStatusChangeLog {
     private int workflowStatusChangeLogId;
    private WorkflowStatus workflowStatus;
    
    private Ticket ticket;
    private User requestor;
    
    private int stamp;
    
    public WorkflowStatusChangeLog() {
        this.workflowStatus = new WorkflowStatus();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public WorkflowStatusChangeLog(WorkflowStatus workflowStatus, Ticket ticket, User requestor, int stamp) {
        this.workflowStatus = workflowStatus;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
    }
    
    

    public int getWorkflowStatusChangeLogId() {
        return workflowStatusChangeLogId;
    }

    public void setWorkflowStatusChangeLogId(int workflowStatusChangeLogId) {
        this.workflowStatusChangeLogId = workflowStatusChangeLogId;
    }

    public WorkflowStatus getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(WorkflowStatus workflowStatus) {
        this.workflowStatus = workflowStatus;
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
