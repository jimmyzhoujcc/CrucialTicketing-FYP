/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DanFoley
 */
public class QueueRequest {
    private int queueRequestId;
    private Queue queue;
    private List<UserQueueCon> userQueueConList;
    private Ticket ticket;
    private User requestor;
    
    public QueueRequest() {
        this.queue = new Queue();
        this.userQueueConList = new ArrayList<>();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public int getQueueRequestId() {
        return queueRequestId;
    }

    public void setQueueRequestId(int queueRequestId) {
        this.queueRequestId = queueRequestId;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public List<UserQueueCon> getUserQueueConList() {
        return userQueueConList;
    }

    public void setUserQueueConList(List<UserQueueCon> UserQueueConList) {
        this.userQueueConList = UserQueueConList;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getRequestor() {
        return requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

 
    
}
