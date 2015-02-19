/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.TicketLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface TicketLogDao {
    public TicketLog getTicketLogByTicketId(int ticketId);

    public void addTicketLog(int ticketId, int userId, String logEntry);
    
    public List<TicketLog> rowMapper(List<Map<String, Object>> resultSet);
}
