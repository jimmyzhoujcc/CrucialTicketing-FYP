/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

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

    public boolean checkIfOutstanding(int roleId, int requestorUserId);
    
    public void grantAccess(int roleLockRequestId);

    public void denyAccess(int roleLockRequestId, int roleId, int requestorUserId);

    public List<RoleLockRequest> getOpenRequestList();

    public List<RoleLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
