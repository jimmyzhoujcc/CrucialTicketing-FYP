/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.UserQueueConChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserQueueConChangeLogDao {
    public void insertChangeLog(UserQueueConChangeLog changeLog);
    
    public List<UserQueueConChangeLog> getChangeLogByUserId(int userId);
    
    public List<UserQueueConChangeLog> getChangeLogByUserQueueConId(int userQueueConId);
    
    public List<UserQueueConChangeLog> getChangeLogByTicketId(int ticketId);
    
    public List<UserQueueConChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
