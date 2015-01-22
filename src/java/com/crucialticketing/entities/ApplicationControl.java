/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author Daniel Foley
 */
public class ApplicationControl {
    private int applicationControlId;
    private TicketType ticketType;
    private Application application;
    private Workflow workflow;
    private Severity severity;
    
    public ApplicationControl() {}

    public ApplicationControl(int applicationControlId, TicketType ticketType, Application application, Workflow workflow, Severity severity) {
        this.applicationControlId = applicationControlId;
        this.ticketType = ticketType;
        this.application = application;
        this.workflow = workflow;
        this.severity = severity;
    }

    public int getApplicationControlId() {
        return applicationControlId;
    }

    public void setApplicationControlId(int applicationControlId) {
        this.applicationControlId = applicationControlId;
    }

    
    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    } 

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
    
    
}

