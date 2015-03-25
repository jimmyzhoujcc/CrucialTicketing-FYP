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
public class QueueLockRequest {

    private int queueLockRequestId;
    private Queue queue;
    private User requestor;
    private int requestTime;
    private int requestPassTime;
    
    public QueueLockRequest() {
        this.queue = new Queue();
        this.requestor = new User();
    }

    public QueueLockRequest(Queue queue, User requestor) {
        this.queue = queue;
        this.requestor = requestor;
    }

    public int getQueueLockRequestId() {
        return queueLockRequestId;
    }

    public void setQueueLockRequestId(int queueLockRequestId) {
        this.queueLockRequestId = queueLockRequestId;
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

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequestPassTime() {
        return requestPassTime;
    }

    public void setRequestPassTime(int requestPassTime) {
        this.requestPassTime = requestPassTime;
    }
    
    
    
}
