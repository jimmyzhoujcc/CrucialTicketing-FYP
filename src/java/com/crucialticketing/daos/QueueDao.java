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
    public Queue getQueueById(int queueId);
    
    public List<Queue> getQueueList();
    
    public List<Queue> rowMapper(List<Map<String, Object>> resultSet);
}
