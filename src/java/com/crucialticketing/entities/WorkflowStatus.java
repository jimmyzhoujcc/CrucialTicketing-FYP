/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.util.WorkflowStatusType;

/**
 *
 * @author DanFoley
 */
public class WorkflowStatus {
    private int workflowStatusId;
    private String workflowStatusName;
    private WorkflowStatusType workflowStatusType;
    private ActiveFlag activeFlag;
    
    public WorkflowStatus() {}

    public WorkflowStatus(String workflowStatusName, WorkflowStatusType workflowStatusType, ActiveFlag activeFlag) {
        this.workflowStatusName = workflowStatusName;
        this.workflowStatusType = workflowStatusType;
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

    public WorkflowStatusType getWorkflowStatusType() {
        return workflowStatusType;
    }

    public void setWorkflowStatusType(WorkflowStatusType workflowStatusType) {
        this.workflowStatusType = workflowStatusType;
    }

    

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }

    
    
}
