/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.UserRoleConChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserRoleConChangeLogDao {
    public void insertChangeLog(UserRoleConChangeLog changeLog);
    
    public List<UserRoleConChangeLog> getChangeLogByUserId(int userId);
    
    public List<UserRoleConChangeLog> getChangeLogByRoleId(int roleId);
    
    public List<UserRoleConChangeLog> getChangeLogByTicketId(int ticketId);
    
    public List<UserRoleConChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
