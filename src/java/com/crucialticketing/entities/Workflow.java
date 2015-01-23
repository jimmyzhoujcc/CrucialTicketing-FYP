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
    private List<WorkflowStage> workflow;

    public Workflow() {
        workflow = new ArrayList<WorkflowStage>();
    }

    public Workflow(int workflowId, String workflowName) {
        this.workflowId = workflowId;
        this.workflowName = workflowName;
    }

    public Workflow(int workflowId, String workflowName, List<WorkflowStage> workflow) {
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        this.workflow = workflow;
    }

    public void addStatus(WorkflowStatus status, Role role, Queue queue) {
        List<WorkflowStage> newList = new ArrayList<>();
        workflow.add(new WorkflowStage(status, role, queue, newList));
    }

    public void removeStatus(WorkflowStage workflowStage) {
        workflow.remove(workflowStage);
    }

    public boolean doesStatusExist(int workflowStatusId) {
        for (WorkflowStage workflowStage : workflow) {
            if (workflowStage.getStatus().getStatusId() == workflowStatusId) {
                return true;
            }
        }
        return false;
    }

    public WorkflowStage getWorkflowStageByStatus(int workflowStatusId) {
        for (WorkflowStage workflowStage : workflow) {
            if (workflowStage.getStatus().getStatusId() == workflowStatusId) {
                return workflowStage;
            }
        }
        return new WorkflowStage();
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

    public List<WorkflowStage> getWorkflow() {
        return workflow;
    }

    public void setWorkflow(List<WorkflowStage> workflow) {
        this.workflow = workflow;
    }

}
