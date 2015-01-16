/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketType;
import java.util.List;

/**
 *
 * @author Daniel Foley
 */
public interface TicketTypeDao {
    public void insertTicketType(TicketType ticketType);

    public List<TicketType> getTicketTypeList();

    public void updateTicketType(TicketType ticketType);

    public void deleteTicketType(String id);

    public TicketType getTicketTypeById(String id);
}
