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
public class WorkflowStatus {
    private int workflowStatusId;
    private String workflowStatusName;
    private boolean baseWorkflowStatus;
    private boolean closureWorkflowStatus;
    private ActiveFlag activeFlag;
    
    public WorkflowStatus() {}

    public WorkflowStatus(String workflowStatusName, boolean baseWorkflowStatus, boolean closureWorkflowStatus, ActiveFlag activeFlag) {
        this.workflowStatusName = workflowStatusName;
        this.baseWorkflowStatus = baseWorkflowStatus;
        this.closureWorkflowStatus = closureWorkflowStatus;
        this.activeFlag = activeFlag;
    }

    public int getWorkflowStatusId() {
        return workflowStatusId;
    }

    public void setWorkflowStatusId(int workflowStatusId) {
        this.workflowStatusId = workflowStatusId;
    }

    public String getWorkflowStatusName() {
        return workflowStatusName;
    }

    public void setWorkflowStatusName(String workflowStatusName) {
        this.workflowStatusName = workflowStatusName;
    }

    public boolean isBaseWorkflowStatus() {
        return baseWorkflowStatus;
    }

    public void setBaseWorkflowStatus(boolean baseWorkflowStatus) {
        this.baseWorkflowStatus = baseWorkflowStatus;
    }

    public boolean isClosureWorkflowStatus() {
        return closureWorkflowStatus;
    }

    public void setClosureWorkflowStatus(boolean closureWorkflowStatus) {
        this.closureWorkflowStatus = closureWorkflowStatus;
    }

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }

    
    
}
