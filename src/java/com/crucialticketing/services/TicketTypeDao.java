/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface TicketTypeDao extends DatabaseService {
    public TicketType getTicketTypeById(int ticketTypeId);
    
    public List<TicketType> getTicketTypeList();
    
    public boolean doesTicketTypeExist(int ticketTypeId);
    
    public List<TicketType> rowMapper(List<Map<String, Object>> resultSet);
}
