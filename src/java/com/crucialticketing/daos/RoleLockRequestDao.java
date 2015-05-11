/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface RoleLockRequestDao {

    public void addLockRequest(RoleLockRequest lockRequest);

    public boolean checkIfOpen(int roleId, int requestorUserId);

    public boolean checkIfOpen(int roleId);
    
    public boolean checkIfOutstanding(int roleId, int requestorUserId);

    public void grantAccess(int lockRequestId);

    public void denyAccess(int lockRequestId, Role role, int requestorUserId);

    public List<RoleLockRequest> getOpenRequestList();

    public void closeRequest(int roleId, int requestorUserId);

    public List<RoleLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
