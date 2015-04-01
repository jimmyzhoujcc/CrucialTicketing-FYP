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
public class TicketLink {

    private int ticketLinkId;
    private Ticket fromTicket;
    private Ticket toTicket;

    public TicketLink() {
    }

    public TicketLink(Ticket fromTicket, Ticket toTicket) {
        this.fromTicket = fromTicket;
        this.toTicket = toTicket;
    }

    public int getTicketLinkId() {
        return ticketLinkId;
    }

    public void setTicketLinkId(int ticketLinkId) {
        this.ticketLinkId = ticketLinkId;
    }

    public Ticket getFromTicket() {
        return fromTicket;
    }

    public void setFromTicket(Ticket fromTicket) {
        this.fromTicket = fromTicket;
    }

    public Ticket getToTicket() {
        return toTicket;
    }

    public void setToTicket(Ticket toTicket) {
        this.toTicket = toTicket;
    }
    
    

}
