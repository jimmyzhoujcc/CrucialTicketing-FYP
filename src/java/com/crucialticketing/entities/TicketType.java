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
public class TicketType {
    private int ticketTypeId;
    private String ticketTypeName;

    public TicketType() {}
    
    public TicketType(int ticketTypeId, String ticketTypeName) {
        this.ticketTypeId = ticketTypeId;
        this.ticketTypeName = ticketTypeName;
    }

    public int getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(int ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }
    
    
    
}
