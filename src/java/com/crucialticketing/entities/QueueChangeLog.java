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
    
    private Ticket ticket;
    private User requestor;
    
    private int stamp;
    
    public QueueChangeLog() {
        this.queue = new Queue();
        this.ticket = new Ticket();
        this.requestor = new User();
    }

    public QueueChangeLog(Queue queue, Ticket ticket, User requestor, int stamp) {
        this.queue = queue;
        this.ticket = ticket;
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

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }
    
}
