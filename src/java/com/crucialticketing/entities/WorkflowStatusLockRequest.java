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
public class WorkflowStatusLockRequest {
    private int workflowStatusLockRequestId;
    private WorkflowStatus workflowStatus;
    private User requestor;
    private int requestTime;
    private int requestPassTime;
    
    public WorkflowStatusLockRequest() {
        this.workflowStatus = new WorkflowStatus();
        this.requestor = new User();
    }

    public WorkflowStatusLockRequest(WorkflowStatus workflowStatus, User requestor) {
        this.workflowStatus = workflowStatus;
        this.requestor = requestor;
    }

    public int getWorkflowStatusLockRequestId() {
        return workflowStatusLockRequestId;
    }

    public void setWorkflowStatusLockRequestId(int workflowStatusLockRequestId) {
        this.workflowStatusLockRequestId = workflowStatusLockRequestId;
    }

    public WorkflowStatus getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(WorkflowStatus workflowStatus) {
        this.workflowStatus = workflowStatus;
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
