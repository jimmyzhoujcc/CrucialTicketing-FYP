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
    // DAO items
    private int ticketId;
    private String shortDescription;
    private WorkflowStep currentWorkflowStep;

    // Generated from creation
    private ApplicationControl applicationControl;
    private User reportedBy;
    private User createdBy;
    private User lastUpdatedBy;

    // Logs
    private TicketLog ticketLog;
    private List<Attachment> attachmentList;
    private List<TicketLink> ticketLinkList;
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

    public ApplicationControl getApplicationControl() {
        return applicationControl;
    }

    public void setApplicationControl(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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

    public List<TicketLink> getTicketLinkList() {
        return ticketLinkList;
    }

    public void setTicketLinkList(List<TicketLink> ticketLinkList) {
        this.ticketLinkList = ticketLinkList;
    }

    public ChangeLog getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(ChangeLog changeLog) {
        this.changeLog = changeLog;
    }

    
}
