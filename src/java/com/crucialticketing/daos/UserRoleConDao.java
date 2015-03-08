/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRoleCon;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserRoleConDao {
   public int insertUserRoleCon(final UserRoleCon userRoleCon, final boolean newUserFlag, Ticket ticket, User requestor);
   
   public UserRoleCon getUserRoleConById(int userRoleConId);
   
   public List<UserRoleCon> getUserListByRoleId(int roleId);
   
   public List<UserRoleCon> getRoleListByUserId(int userId);
   
   public boolean doesUserRoleConExist(int userId, int roleId);
   
   public boolean doesUserRoleConExistInOnline(int userId, int roleId);
  
   public List<UserRoleCon> getIncompleteUserRoleConList(boolean newUserFlag);
   
   public List<UserRoleCon> getUnprocessedUserRoleConList(boolean newUserFlag);
   
   public List<UserRoleCon> getUnprocessedUserRoleConListByRoleId(int roleId, boolean newUserFlag);
   
   public List<UserRoleCon> getOnlineUserRoleConList(boolean newUserFlag);
   
   public List<UserRoleCon> getOfflineUserRoleConList(boolean newUserFlag);
   
   public void updateToUnprocessed(int userRoleCon, Ticket ticket, User requestor);
   
   public void updateToOnline(int userRoleConId, Ticket ticket, User requestor);
   
   public void updateToOffline(int userRoleConId, Ticket ticket, User requestor);
   
   public List<UserRoleCon> rowMapper(List<Map<String, Object>> resultSet);
}
