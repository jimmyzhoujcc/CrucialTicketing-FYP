/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.SeverityChangeLog;
import com.crucialticketing.entities.Ticket;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface SeverityChangeLogDao {
    public void insertSeverityChangeLog(SeverityChangeLog severityChangeLog);
    
    public List<SeverityChangeLog> getChangeLogBySeverityId(int severityId);
    
    public List<SeverityChangeLog> getChangeLogByTicketId(Ticket ticket);
    
    public List<SeverityChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
