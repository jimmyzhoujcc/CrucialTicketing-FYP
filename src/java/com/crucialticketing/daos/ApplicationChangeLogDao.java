/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.ApplicationChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ApplicationChangeLogDao {
    public void insertApplicationChangeLog(ApplicationChangeLog applicationChangeLog);
    
    public List<ApplicationChangeLog> getApplicationChangeLogByApplicationId(int applicationId);
    
    public List<ApplicationChangeLog> getApplicationChangeLogByTicketId(int ticketId);
    
    public List<ApplicationChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
