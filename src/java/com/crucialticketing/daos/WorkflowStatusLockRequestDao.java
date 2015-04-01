/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStatusLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowStatusLockRequestDao {

    public void addLockRequest(WorkflowStatusLockRequest lockRequest);

    public boolean checkIfOpen(int workflowStatusId, int requestorUserId);

    public boolean checkIfOutstanding(int workflowStatusId, int requestorUserId);

    public void grantAccess(int lockRequestId);

    public void denyAccess(int lockRequestId, WorkflowStatus workflowStatus, int requestorUserId);

    public List<WorkflowStatusLockRequest> getOpenRequestList();

    public void closeRequest(int workflowStatusId, int requestorUserId);

    public List<WorkflowStatusLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
