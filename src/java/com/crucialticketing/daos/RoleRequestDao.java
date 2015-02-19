/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.RoleRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface RoleRequestDao {
     public void insertRoleRequest(RoleRequest roleRequest);
    
    public List<RoleRequest> getRoleRequestList();
    
    public void removeRequest(int roleRequestId);
    
    public List<RoleRequest> rowMapper(List<Map<String, Object>> resultSet);
}
