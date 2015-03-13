/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface QueueDao {

    public int insertQueue(Queue queue, Ticket ticket, User requestor);

    public Queue getQueueById(int queueId);

    public boolean doesQueueExist(String queueName);

    public boolean doesQueueExistInOnline(int queueId);

    public boolean doesQueueExistInOnline(String queueName);

    public boolean doesQueueExistInOnlineOrOffline(String queueName);

    public List<Queue> getIncompleteQueueList();

    public List<Queue> getUnprocessedQueueList();

    public List<Queue> getOnlineQueueList();

    public List<Queue> getOfflineQueueList();

    public void updateToUnprocessed(int queueId, Ticket ticket, User requestor);

    public void updateToOnline(int queueId, Ticket ticket, User requestor);

    public void updateToOffline(int queueId, Ticket ticket, User requestor);

    public void removeQueue(int queueId);
    
    public List<Queue> rowMapper(List<Map<String, Object>> resultSet);
}
