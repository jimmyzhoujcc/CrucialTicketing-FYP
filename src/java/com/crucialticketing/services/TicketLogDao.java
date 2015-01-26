/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface TicketLogDao extends DatabaseService {
    public TicketLog getTicketLogByTicketId(int ticketId);

    public List<TicketLog> rowMapper(List<Map<String, Object>> resultSet);
}
