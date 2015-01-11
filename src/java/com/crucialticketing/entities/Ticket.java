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
    private String ticketType;
    private String shortDescription;
    private Status status;
    private System system;
    
    private User messageProcessor;
    private User createdBy;
    private User reportedBy;
    
    private int timeDateCreated;
    private int timeDateChanged;
    private int timeDateClosed;
    private int timeDateReopened;
    
    private Workflow workflow;
    
    public Ticket() {}
    
    public Ticket(String ticketId, String shortDescription) {
        this.ticketId = ticketId;
        this.shortDescription = shortDescription;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
