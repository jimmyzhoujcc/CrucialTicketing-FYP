/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowLockRequestDao {

    public void addLockRequest(WorkflowLockRequest lockRequest);

    public boolean checkIfOpen(int workflowId, int requestorUserId);

    public boolean checkIfOpen(int workflowId);
    
    public boolean checkIfOutstanding(int workflowId, int requestorUserId);

    public void grantAccess(int lockRequestId);

    public void denyAccess(int lockRequestId, Workflow workflow, int requestorUserId);

    public List<WorkflowLockRequest> getOpenRequestList();

    public void closeRequest(int workflowId, int requestorUserId);

    public List<WorkflowLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
