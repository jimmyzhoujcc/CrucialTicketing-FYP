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
public class WorkflowChangeLog {
    private int workflowChangeLogId;
    private Workflow workflow;
    
    private Ticket ticket;
    private User requestor;
    
    private int stamp;
    
    public WorkflowChangeLog() {
        this.workflow = new Workflow();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public WorkflowChangeLog(Workflow workflow, Ticket ticket, User requestor, int stamp) {
        this.workflow = workflow;
        this.ticket = ticket;
        this.requestor = requestor;
        this.stamp = stamp;
    }

    public int getWorkflowChangeLogId() {
        return workflowChangeLogId;
    }

    public void setWorkflowChangeLogId(int workflowChangeLogId) {
        this.workflowChangeLogId = workflowChangeLogId;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
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
