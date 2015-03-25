/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.ApplicationControlLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ApplicationControlLockRequestDao {
    public void addLockRequest(ApplicationControlLockRequest lockRequest);

    public boolean checkIfOpen(int applicationControlId, int requestorUserId);

    public boolean checkIfOutstanding(int applicationControlId, int requestorUserId);
    
    public void grantAccess(int applicationControlLockRequestId);

    public void denyAccess(int applicationControlLockRequestId, int applicationControlId, int requestorUserId);

    public List<ApplicationControlLockRequest> getOpenRequestList();

    public List<ApplicationControlLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
