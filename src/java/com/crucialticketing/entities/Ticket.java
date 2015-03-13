/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.List;

/**
 *
 * @author Owner
 */
public class Ticket {
    private int ticketId;
    
    private ApplicationControl applicationControl;
    
    private String shortDescription;
    
    private WorkflowStep currentWorkflowStep;
    
    private User lastProcessedBy;
    private User createdBy;
    private User reportedBy;
    
    private TicketLog ticketLog;
    private List<Attachment> attachmentList;
    private ChangeLog changeLog;
    
    public Ticket() {}

    public Ticket(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
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

    public WorkflowStep getCurrentWorkflowStep() {
        return currentWorkflowStep;
    }

    public void setCurrentWorkflowStep(WorkflowStep currentWorkflowStep) {
        this.currentWorkflowStep = currentWorkflowStep;
    }

    public User getLastProcessedBy() {
        return lastProcessedBy;
    }

    public void setLastProcessedBy(User lastProcessedBy) {
        this.lastProcessedBy = lastProcessedBy;
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

    public TicketLog getTicketLog() {
        return ticketLog;
    }

    public void setTicketLog(TicketLog ticketLog) {
        this.ticketLog = ticketLog;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public ChangeLog getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(ChangeLog changeLog) {
        this.changeLog = changeLog;
    }
    
    
}
