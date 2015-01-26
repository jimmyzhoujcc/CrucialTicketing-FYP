/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Ticket;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface TicketDao extends DatabaseService {
    public Ticket getTicketById(int ticketId, boolean populateInternalData);
    
    public List<Ticket> getTicketList(boolean populateInternalData);
    
    public List<Ticket> rowMapper(List<Map<String, Object>> resultSet, boolean populateInternalData);
}
