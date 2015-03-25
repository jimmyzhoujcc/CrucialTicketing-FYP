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
public class ApplicationControlLockRequest {
    private int applicationControlLockRequestId;
    private ApplicationControl applicationControl;
    private User requestor;
    private int requestTime;
    private int requestPassTime;
    
    public ApplicationControlLockRequest() {
        this.applicationControl = new ApplicationControl();
        this.requestor = new User();
    }

    public ApplicationControlLockRequest(ApplicationControl applicationControl, User requestor) {
        this.applicationControl = applicationControl;
        this.requestor = requestor;
    }

    public int getApplicationControlLockRequestId() {
        return applicationControlLockRequestId;
    }

    public void setApplicationControlLockRequestId(int applicationControlLockRequestId) {
        this.applicationControlLockRequestId = applicationControlLockRequestId;
    }

    public ApplicationControl getApplicationControl() {
        return applicationControl;
    }

    public void setApplicationControl(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
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
