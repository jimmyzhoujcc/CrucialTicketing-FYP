/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRoleCon;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserRoleConDao {
    public int insertUserRoleCon(final UserRoleCon userQueueCon, final boolean newUserFlag);
    
    public List<UserRoleCon> getIncompleteUserRoleConList(boolean newUserFlag);
    
    public List<UserRoleCon> getUnprocessedUserRoleConList(boolean newUserFlag);
    
    public List<UserRoleCon> getUnprocessedUserRoleConListByRoleId(Role role, boolean newUserFlag);
    
    public List<UserRoleCon> getUnprocessedUserRoleConListByUserId(User user, boolean newUserFlag);
    
    public List<UserRoleCon> getOnlineUserRoleConList(boolean newUserFlag);
    
    public List<UserRoleCon> getOfflineUserRoleConList(boolean newUserFlag);
    
    public void updateToUnprocessed(UserRoleCon userRoleCon);
    
    public void updateToOnline(UserRoleCon userRoleCon);
    
    public void updateToOffline(UserRoleCon userRoleCon);
    
    public void removeUserRoleConEntry(UserRoleCon userRoleCon);
  
    public void removeAllUserRoleConEntries(User user);
    
    public List<UserRoleCon> getUserListByRoleId(Role role);
    
    public List<UserRoleCon> getRoleListByUserId(User user);
    
    public boolean doesUserRoleConExistInOnline(User user, Role role);
    
    public List<UserRoleCon> rowMapper(List<Map<String, Object>> resultSet);
}
