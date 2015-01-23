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
public class WorkflowStage {
    private WorkflowStatus status;
    private Role role;
    private Queue queue;
    private List<WorkflowStage> nextWorkflowStage;
    
    public WorkflowStage() {}

    public WorkflowStage(WorkflowStatus status, Role role, Queue queue, List<WorkflowStage> nextWorkflowStatus) {
        this.status = status;
        this.role = role;
        this.queue = queue;
        this.nextWorkflowStage = nextWorkflowStatus;
    }

    public void addNextNode(WorkflowStage nextNode) {
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

    public List<WorkflowStage> getNextWorkflowStage() {
        return nextWorkflowStage;
    }

    public void setNextWorkflowStage(List<WorkflowStage> nextWorkflowStage) {
        this.nextWorkflowStage = nextWorkflowStage;
    }
   
    
    
}
