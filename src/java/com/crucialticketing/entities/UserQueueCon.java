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
public class UserQueueCon {

    private int userQueueConId;
    private User user;
    private Queue queue;
    private ActiveFlag activeFlag;

    public UserQueueCon() {
        this.user = new User();
        this.queue = new Queue();
    }

    public UserQueueCon(User user, Queue queue, ActiveFlag activeFlag) {
        this.user = user;
        this.queue = queue;
        this.activeFlag = activeFlag;
    }

    public int getUserQueueConId() {
        return userQueueConId;
    }

    public void setUserQueueConId(int userQueueConId) {
        this.userQueueConId = userQueueConId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    
}
