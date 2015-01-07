/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Ticket;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Owner
 */
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketDao ticketDao;

    @Override
    public void insertTicket(Ticket ticket) {
        ticketDao.insertTicket(ticket);
    }

    @Override
    public List<Ticket> getTicketList() {
        return ticketDao.getTicketList();
    }

    @Override
    public void deleteTicket(String id) {
        ticketDao.deleteTicket(id);

    }

    @Override
    public Ticket getTicketById(String id) {
        return ticketDao.getTicketById(id);
    }

    @Override
    public void updateTicket(Ticket ticket) {
        ticketDao.updateTicket(ticket);

    }

}

