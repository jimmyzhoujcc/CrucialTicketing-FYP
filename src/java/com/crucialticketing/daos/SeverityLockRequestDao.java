/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.RoleLockRequest;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.SeverityLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface SeverityLockRequestDao {

    public void addLockRequest(SeverityLockRequest lockRequest);

    public boolean checkIfOpen(int severityId, int requestorUserId);

    public boolean checkIfOutstanding(int severityId, int requestorUserId);

    public void grantAccess(int lockRequestId);

    public void denyAccess(int lockRequestId, Severity severity, int requestorUserId);

    public List<SeverityLockRequest> getOpenRequestList();

    public void closeRequest(int severityId, int requestorUserId);

    public List<SeverityLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
