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
public class SeverityLockRequest {

    private int lockRequestId;
    private Severity severity;
    private User requestor;
    private int requestTime;
    private int requestPassTime;

    public SeverityLockRequest() {
    }

    public SeverityLockRequest(Severity severity, User requestor) {
        this.severity = severity;
        this.requestor = requestor;
    }
    
    

    public int getLockRequestId() {
        return lockRequestId;
    }

    public void setLockRequestId(int lockRequestId) {
        this.lockRequestId = lockRequestId;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
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
