/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.ChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ChangeLogDao {
    public void addChangeLogEntry(int ticketId, int applicationControlId, int userId, int statusId);
    
    public ChangeLog getChangeLogByTicketId(int ticketId, boolean populateAll);

    public ChangeLog rowMapper(List<Map<String, Object>> resultSet, boolean populateAll);
}
