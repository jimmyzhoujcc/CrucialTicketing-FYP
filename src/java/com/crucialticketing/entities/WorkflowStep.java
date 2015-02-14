/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.List;

/**
 *
 * @author Daniel Foley
 */
public class WorkflowStep {
    private WorkflowStatus status;
    private Role role;
    private Queue queue;
    private List<WorkflowStep> nextWorkflowStep;
    private int clockActive;
    
    public WorkflowStep() {}

    public WorkflowStep(WorkflowStatus status, Role role, Queue queue, List<WorkflowStep> nextWorkflowStep, int clockActive) {
        this.status = status;
        this.role = role;
        this.queue = queue;
        this.nextWorkflowStep = nextWorkflowStep;
        this.clockActive = clockActive;
    }

    public void addNextNode(WorkflowStep nextNode) {
        nextWorkflowStep.add(nextNode);
    }

    public boolean isLegalStep(int workflowStatusId) {
        for (WorkflowStep workflowStep : nextWorkflowStep) {
            if(workflowStep.getStatus().getStatusId() == workflowStatusId) {
                return true;
            }
        }
        return false;
    }
    
    public WorkflowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkflowStatus status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public List<WorkflowStep> getNextWorkflowStep() {
        return nextWorkflowStep;
    }

    public void setNextWorkflowStep(List<WorkflowStep> nextWorkflowStep) {
        this.nextWorkflowStep = nextWorkflowStep;
    }

    public int getClockActive() {
        return clockActive;
    }

    public void setClockActive(int clockActive) {
        this.clockActive = clockActive;
    }
   
    
    
    
}
