/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Role;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface RoleDao {
    
    public void insertRole(Role role);
    
    public Role getRoleById(int roleId);
    
    public Role getRoleByRoleName(String roleName);
    
    public boolean doesRoleExist(int roleId);
    
    public boolean doesRoleExist(String roleName);
    
    public List<Role> getRoleList();
    
    public List<Role> rowMapper(List<Map<String, Object>> resultSet);
}
