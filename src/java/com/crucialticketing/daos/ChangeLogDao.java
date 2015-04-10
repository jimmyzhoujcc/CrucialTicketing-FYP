/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.TicketChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ChangeLogDao {
    public void addChangeLogEntry(int ticketId, int applicationControlId, int userId, int statusId);
    
    public TicketChangeLog getChangeLogByTicketId(int ticketId, boolean populateAll);

    public TicketChangeLog rowMapper(List<Map<String, Object>> resultSet, boolean populateAll);
}
