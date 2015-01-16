/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author Daniel Foley
 */
public class WorkflowChange {
    private WorkflowStatus status;
    private Role role;
    private Queue queue;
    
    public WorkflowChange() {}

    public WorkflowChange(WorkflowStatus status, Role role, Queue queue) {
        this.status = status;
        this.role = role;
        this.queue = queue;
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
    
    
    
}
