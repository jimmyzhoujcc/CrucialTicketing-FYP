/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.QueueChangeLog;
import com.crucialticketing.entities.Ticket;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface QueueChangeLogDao {
    public void insertQueueChangeLog(QueueChangeLog queueChangeLog);

    public List<QueueChangeLog> getQueueChangeLogList();
    
    public List<QueueChangeLog> getQueueChangeLogListByQueueId(int queueId);

    public List<QueueChangeLog> getQueueChangeLogListByTicketId(Ticket ticket);
    
    public List<QueueChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
