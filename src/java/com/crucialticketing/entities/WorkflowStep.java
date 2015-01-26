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
    private List<WorkflowStep> nextWorkflowStage;
    
    public WorkflowStep() {}

    public WorkflowStep(WorkflowStatus status, Role role, Queue queue, List<WorkflowStep> nextWorkflowStatus) {
        this.status = status;
        this.role = role;
        this.queue = queue;
        this.nextWorkflowStage = nextWorkflowStatus;
    }

    public void addNextNode(WorkflowStep nextNode) {
        nextWorkflowStage.add(nextNode);
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

    public List<WorkflowStep> getNextWorkflowStage() {
        return nextWorkflowStage;
    }

    public void setNextWorkflowStage(List<WorkflowStep> nextWorkflowStage) {
        this.nextWorkflowStage = nextWorkflowStage;
    }
   
    
    
}
