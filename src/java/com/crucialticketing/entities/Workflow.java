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
    List<Status> workflow;
    
    public Workflow() {}

    public Workflow(List<Status> workflow) {
        this.workflow = workflow;
    }
    
    public void addStatus(Status status) {
        workflow.add(status);
    }
    
    public void removeStatus(Status status) {
        workflow.remove(status);
    }
    
}
