/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author Daniel Foley
 */
public class Queue {
    private int queueId;
    private String queueName;
    
    public Queue() {}

    public Queue(int queueId, String queueName) {
        this.queueId = queueId;
        this.queueName = queueName;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
    
    
}
