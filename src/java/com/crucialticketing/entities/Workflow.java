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
 * @author DanFoley
 */
public class Workflow {
    private int workflowId;
    private String workflowName;
    private List<WorkflowChange> workflow;
    
    public Workflow() {
        workflow = new ArrayList<WorkflowChange>();
    }

    public Workflow(int workflowId, String workflowName) {
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        workflow = new ArrayList<WorkflowChange>();
    }
    
    public Workflow(int workflowId, List<WorkflowChange> workflow) {
        this.workflowId = workflowId;
        this.workflow = workflow;
    }
    
    public void addStatus(WorkflowStatus status, Role role, Queue queue) {
        workflow.add(new WorkflowChange(status, role, queue));
    }
    
    public void removeStatus(WorkflowChange workflowSection) {
        workflow.remove(workflowSection);
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

    public List<WorkflowChange> getWorkflow() {
        return workflow;
    }

    public void setWorkflow(List<WorkflowChange> workflow) {
        this.workflow = workflow;
    }

    

    
}
