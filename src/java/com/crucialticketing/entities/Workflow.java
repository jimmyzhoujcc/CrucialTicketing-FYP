/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.List;

/**
 *
 * @author DanFoley
 */
public class Workflow {
    private int workflowId;
    private List<WorkflowChange> workflow;
    
    public Workflow() {}

    public Workflow(int workflowId, List<WorkflowChange> workflow) {
        this.workflowId = workflowId;
        this.workflow = workflow;
    }
    
    public void addStatus(Status status, Role role, Queue queue) {
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

    public List<WorkflowChange> getWorkflow() {
        return workflow;
    }

    public void setWorkflow(List<WorkflowChange> workflow) {
        this.workflow = workflow;
    }

    
}
