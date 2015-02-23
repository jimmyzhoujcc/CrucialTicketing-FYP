/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Queue;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface QueueDao {
    
    public int insertQueue(Queue queue);
    
    public Queue getQueueById(int queueId);
    
    public List<Queue> getIncompleteQueueList();
    
    public List<Queue> getUnprocessedQueueList();
    
    public List<Queue> getOnlineQueueList();
    
    public List<Queue> getOfflineQueueList(); 
    
    public void updateToUnprocessed(Queue queue);
    
    public void updateToOnline(Queue queue);
    
    public void updateToOffline(Queue queue);
    
    public boolean doesQueueExist(String queueName);
    
    public boolean doesQueueExistInOnline(Queue queue);
    
    public boolean doesQueueExistInOnline(String queueName);
    
    public boolean doesQueueExistInOnlineOrOffline(String queueName);
  
    public void removeQueueEntry(Queue queue);
    
    public List<Queue> rowMapper(List<Map<String, Object>> resultSet);
}
