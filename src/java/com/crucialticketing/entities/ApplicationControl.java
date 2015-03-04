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
    private Role role;
    private int slaClock;
    
    public ApplicationControl() {}

    public ApplicationControl(TicketType ticketType, Application application, Workflow workflow, Severity severity, Role role, int slaClock) {
        this.ticketType = ticketType;
        this.application = application;
        this.workflow = workflow;
        this.severity = severity;
        this.role = role;
        this.slaClock = slaClock;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getSlaClock() {
        return slaClock;
    }

    public void setSlaClock(int slaClock) {
        this.slaClock = slaClock;
    } 
}

