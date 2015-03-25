/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import com.crucialticketing.util.ActiveFlag;

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
    
    private ActiveFlag activeFlag;
    
    public ApplicationControl() {
        this.ticketType = new TicketType();
        this.application = new Application();
        this.workflow = new Workflow();
        this.severity = new Severity();
        this.role = new Role();
    }

    public ApplicationControl(int applicationControlId) {
        this.applicationControlId = applicationControlId;
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

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }

     
}

