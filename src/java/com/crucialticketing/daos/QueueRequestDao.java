/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.QueueRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface QueueRequestDao {
    public int insertQueueRequest(QueueRequest queueRequest);
    
    public void setReadyToProcess(int queueRequestId);
    
    public List<QueueRequest> getQueueRequestList();
    
    public void removeRequest(int queueRequestId);
    
    public List<QueueRequest> rowMapper(List<Map<String, Object>> resultSet);
}
