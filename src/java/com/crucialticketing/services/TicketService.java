/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Ticket;
import java.util.List;

/**
 *
 * @author Owner
 */
public interface TicketService {
    public void insertTicket(Ticket ticket);

    public List<Ticket> getTicketList();

    public void updateTicket(Ticket ticket);

    public void deleteTicket(String id);

    public Ticket getTicketById(String id);
}
