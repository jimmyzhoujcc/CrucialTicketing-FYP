/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.UserRoleConRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserRoleConRequestDao {
    public void insertUserRoleConRequest(int roleId, int userId, int validFrom, 
            int validTo, int ticketId, int requestorUserId);
    
    public void removeUserRoleConRequest(int userRoleConRequestId);
    
    public List<UserRoleConRequest> getUserRoleConnectionRequestList();
 
    public List<UserRoleConRequest> getUserRoleConnectionRequestList(int tempUserId);
    
    public List<UserRoleConRequest> rowMapper(List<Map<String, Object>> resultSet);
}
