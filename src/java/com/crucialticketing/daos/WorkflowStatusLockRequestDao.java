/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

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

    public void grantAccess(int workflowStatusLockRequestId);

    public void denyAccess(int workflowStatusLockRequestId, int workflowStatusId, int requestorUserId);

    public List<WorkflowStatusLockRequest> getOpenRequestList();

    public List<WorkflowStatusLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
