/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.ApplicationLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ApplicationLockRequestDao {

    public void addLockRequest(ApplicationLockRequest lockRequest);

    public boolean checkIfOpen(int applicationId, int requestorUserId);

    public boolean checkIfOutstanding(int applicationId, int requestorUserId);

    public void grantAccess(int applicationLockRequestId);

    public void denyAccess(int applicationLockRequestId, int applicationId, int requestorUserId);

    public List<ApplicationLockRequest> getOpenRequestList();

    public List<ApplicationLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
