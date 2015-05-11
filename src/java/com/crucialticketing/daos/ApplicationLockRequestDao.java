/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Application;
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

    public boolean checkIfOpen(int applicationId);
    
    public boolean checkIfOutstanding(int applicationId, int requestorUserId);

    public void grantAccess(int lockRequestId);

    public void denyAccess(int lockRequestId, Application application, int requestorUserId);

    public List<ApplicationLockRequest> getOpenRequestList();

    public void closeRequest(int applicationId, int requestorUserId);

    public List<ApplicationLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
