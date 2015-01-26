/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface TicketLockRequestDao {
    public TicketLockRequest getTicketLockRequestByTicketAndUser(int ticketId, int userId);
    
    public List<TicketLockRequest> getTicketList();
    
    public List<TicketLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
