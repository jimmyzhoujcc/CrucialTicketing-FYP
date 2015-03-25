/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.QueueLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface QueueLockRequestDao {
 public void addLockRequest(QueueLockRequest lockRequest);

    public boolean checkIfOpen(int queueId, int requestorUserId);

    public boolean checkIfOutstanding(int queueId, int requestorUserId);
    
    public void grantAccess(int queueLockRequestId);

    public void denyAccess(int queueLockRequestId, int queueId, int requestorUserId);

    public List<QueueLockRequest> getOpenRequestList();

    public List<QueueLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}