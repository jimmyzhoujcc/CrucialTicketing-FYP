/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import com.crucialticketing.util.ActiveFlag;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Foley
 */
public class Queue {
    private int queueId;
    private String queueName;
    private List<UserQueueCon> userQueueConList;
    private boolean protectedFlag;
    private ActiveFlag activeFlag;
    
    public Queue() {
        userQueueConList = new ArrayList<>();
    }

    public Queue(int queueId) {
        this.queueId = queueId;
    }
    /*
    public Queue(String queueName, List<UserQueueCon> userQueueConList, ActiveFlag activeFlag) {
        this.queueName = queueName;
        this.userQueueConList = userQueueConList;
        this.activeFlag = activeFlag;
    }*/

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

    public List<UserQueueCon> getUserQueueConList() {
        return userQueueConList;
    }

    public void setUserQueueConList(List<UserQueueCon> userQueueConList) {
        this.userQueueConList = userQueueConList;
    }

    public boolean isProtectedFlag() {
        return protectedFlag;
    }

    public void setProtectedFlag(boolean protectedFlag) {
        this.protectedFlag = protectedFlag;
    }

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }

    
}
