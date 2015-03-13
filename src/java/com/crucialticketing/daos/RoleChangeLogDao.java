/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleChangeLog;
import com.crucialticketing.entities.Ticket;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface RoleChangeLogDao {
    public void insertRoleChange(RoleChangeLog roleChangeLog);
    
    public List<RoleChangeLog> getChangeLogByRoleId(Role role);
    
    public List<RoleChangeLog> getChangeLogByTicketId(Ticket ticket);
    
    public void removeChangeLogByRoleId(int roleId);
    
    public List<RoleChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
