/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface RoleDao {
    
    public int insertRole(Role role, Ticket ticket, User requestor);

    public Role getRoleById(int RoleId);
    
    public Role getRoleByRoleName(String roleName);
    
    public boolean doesRoleExist(String roleName);
    
    public boolean doesRoleExistInOnline(int roleId);
    
    public boolean doesRoleExistInOnline(String roleName);
    
    public boolean doesRoleExistInOnlineOrOffline(String roleName);

    public List<Role> getIncompleteRoleList();

    public List<Role> getUnprocessedRoleList();
    
    public List<Role> getOnlineRoleList();
    
    public List<Role> getOfflineRoleList();
    
    public void updateToUnprocessed(int roleId, Ticket ticket, User requestor);
    
    public void updateToOnline(int roleId, Ticket ticket, User requestor);
    
    public void updateToOffline(int roleId, Ticket ticket, User requestor);
    
    public List<Role> rowMapper(List<Map<String, Object>> resultSet);
}
