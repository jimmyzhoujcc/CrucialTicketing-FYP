/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Role;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface RoleDao extends DatabaseService {
    public Role getRoleById(int roleId);
    
    public List<Role> getRoleList();
    
    public List<Role> rowMapper(List<Map<String, Object>> resultSet);
}
