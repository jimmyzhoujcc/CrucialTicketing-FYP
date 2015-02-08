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
public interface TicketLockRequestDao extends DatabaseService {
    public void addTicketLockRequest(int ticketId, int userId);
    
    public boolean ticketOpenForEditByUser(int ticketId, int userId);
    
    public void grantAccess(int ticketId, int userId);
    public void denyAccess(int ticketId, int userId);
    
    public List<TicketLockRequest> getOpenRequestList();
    
    public List<TicketLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
