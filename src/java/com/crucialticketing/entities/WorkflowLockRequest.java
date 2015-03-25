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
public class WorkflowLockRequest {
    private int workflowLockRequestId;
    private Workflow workflow;
    private User requestor;
    private int requestTime;
    private int requestPassTime;
    
    public WorkflowLockRequest() {
        this.workflow = new Workflow();
        this.requestor = new User();
    }

    public WorkflowLockRequest(Workflow workflow, User requestor) {
        this.workflow = workflow;
        this.requestor = requestor;
    }

    public int getWorkflowLockRequestId() {
        return workflowLockRequestId;
    }

    public void setWorkflowLockRequestId(int workflowLockRequestId) {
        this.workflowLockRequestId = workflowLockRequestId;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
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
