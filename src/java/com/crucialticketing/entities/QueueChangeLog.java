/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author DanFoley
 */
public class QueueChangeLog {
    private int queueChangeLogId;
    private Queue queue;
    private User requestor;
    private int stamp;
    
    public QueueChangeLog() {}
    
    public QueueChangeLog(
            Queue queue, 
            User requestor) {
        this.queue = queue;
        this.requestor = requestor;
    }
    
    public QueueChangeLog(
            int queueChangeLogId, 
            Queue queue, 
            User requestor, 
            int stamp) {
        this.queueChangeLogId = queueChangeLogId;
        this.queue = queue;
        this.requestor = requestor;
        this.stamp = stamp;
    }

    public int getQueueChangeLogId() {
        return queueChangeLogId;
    }

    public void setQueueChangeLogId(int queueChangeLogId) {
        this.queueChangeLogId = queueChangeLogId;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public User getRequestor() {
        return requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }
    
    
}
