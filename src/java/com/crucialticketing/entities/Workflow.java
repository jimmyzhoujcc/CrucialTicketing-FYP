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
public class Workflow {
    private int workflowId;
    private String workflowName;
    private WorkflowMap workflowMap;
    private ActiveFlag activeFlag;

    public Workflow() {
        workflowMap = new WorkflowMap();
    }

    public Workflow(int workflowId, String workflowName) {
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        workflowMap = new WorkflowMap();
    }

    public Workflow(int workflowId, String workflowName, WorkflowMap workflowMap) {
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        this.workflowMap = workflowMap;
    }

    public Workflow(String workflowName, WorkflowMap workflowMap, ActiveFlag activeFlag) {
        this.workflowName = workflowName;
        this.workflowMap = workflowMap;
        this.activeFlag = activeFlag;
    }

    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public WorkflowMap getWorkflowMap() {
        return workflowMap;
    }

    public void setWorkflowMap(WorkflowMap workflowMap) {
        this.workflowMap = workflowMap;
    }

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }
    
 
}
