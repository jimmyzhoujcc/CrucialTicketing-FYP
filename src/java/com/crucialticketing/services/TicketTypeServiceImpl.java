/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Daniel Foley
 */
public class TicketTypeServiceImpl implements TicketTypeService {
    
    @Autowired
    TicketTypeDao ticketTypeDao;
    
    @Override
    public void insertTicketType(TicketType ticketType) {
        
    }

    @Override
    public List<TicketType> getTicketTypeList() {
        List<TicketType> ticketTypeList = new ArrayList<>();
        return ticketTypeList;
    }

    @Override
    public void updateTicketType(TicketType ticketType) {
        
    }

    @Override
    public void deleteTicketType(String id) { 
        
    }

    @Override
    public TicketType getTicketTypeById(String id) {
        return new TicketType();
    }
}
