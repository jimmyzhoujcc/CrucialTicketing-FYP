/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Queue;
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

    public boolean checkIfOpen(int queueId);
    
    public boolean checkIfOutstanding(int queueId, int requestorUserId);

    public void grantAccess(int lockRequestId);

    public void denyAccess(int lockRequestId, Queue queue, int requestorUserId);

    public List<QueueLockRequest> getOpenRequestList();

    public void closeRequest(int queueId, int requestorUserId);

    public List<QueueLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
