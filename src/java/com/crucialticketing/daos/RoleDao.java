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
    
    public int insertRole(Role role);

    public Role getRoleById(int RoleId);
    
    public Role getRoleByRoleName(String roleName);

    public List<Role> getIncompleteRoleList();

    public List<Role> getUnprocessedRoleList();
    
    public List<Role> getOnlineRoleList();
    
    public List<Role> getOfflineRoleList();
    
    public void updateToUnprocessed(Role role);
    
    public void updateToOnline(Role role);
    
    public void updateToOffline(Role role);
    
    public void removeRoleEntry(Role role);
    
    public boolean doesRoleExist(String roleName);
    
    public boolean doesRoleExistInOnline(int roleId);
    
    public boolean doesRoleExistInOnline(String roleName);
    
    public boolean doesRoleExistInOnlineOrOffline(String roleName);
    
    public List<Role> rowMapper(List<Map<String, Object>> resultSet);
}
