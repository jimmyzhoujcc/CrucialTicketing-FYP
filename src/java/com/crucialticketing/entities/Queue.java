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
 * @author Daniel Foley
 */
public class Queue {
    private int queueId;
    private String queueName;
    private List<UserQueueCon> userList;
    private ActiveFlag activeFlag;
    
    public Queue() {
        userList = new ArrayList<>();
    }

    public Queue(String queueName, List<UserQueueCon> userList, ActiveFlag activeFlag) {
        this.queueName = queueName;
        this.userList = userList;
        this.activeFlag = activeFlag;
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

    public List<UserQueueCon> getUserList() {
        return userList;
    }

    public void setUserList(List<UserQueueCon> userList) {
        this.userList = userList;
    }

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }

   
    
}
