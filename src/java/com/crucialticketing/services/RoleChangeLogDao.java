/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.RoleChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface RoleChangeLogDao extends DatabaseService {
    public void insertRoleChange(int roleId, int userId);
    
    public List<RoleChangeLog> getChangeLogById(int roleId);
    
    public List<RoleChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
