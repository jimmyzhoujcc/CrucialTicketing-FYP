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
public class WorkflowMap {
    private List<WorkflowStep> workflow;

    public WorkflowMap() {
        workflow = new ArrayList<>();
    }

    public WorkflowMap(List<WorkflowStep> workflow) {
        this.workflow = workflow;
    }
    
    
    public void addStep(WorkflowStatus status, Role role, Queue queue, int clockActive) {
        List<WorkflowStep> newList = new ArrayList<>();
        workflow.add(new WorkflowStep(status, role, queue, newList, clockActive));
    }

    public void removeStep(WorkflowStep workflowStep) {
        workflow.remove(workflowStep);
    }

    public boolean doesStepExist(int workflowStatusId) {
        for (WorkflowStep workflowStage : workflow) {
            if (workflowStage.getWorkflowStatus().getWorkflowStatusId()== workflowStatusId) {
                return true;
            }
        }
        return false;
    }

    public WorkflowStep getWorkflowStageByStatus(int workflowStatusId) {
        for (WorkflowStep workflowStage : workflow) {
            if (workflowStage.getWorkflowStatus().getWorkflowStatusId()== workflowStatusId) {
                return workflowStage;
            }
        }
        return new WorkflowStep();
    }

    public List<WorkflowStep> getWorkflow() {
        return workflow;
    }

    public void setWorkflow(List<WorkflowStep> workflow) {
        this.workflow = workflow;
    }
    
    
    
}
