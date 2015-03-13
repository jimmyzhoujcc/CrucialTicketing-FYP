/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.util;

/**
 *
 * @author DanFoley
 */
public enum WorkflowStatusType {

    NORMAL(0),
    BASE(1),
    CLOSURE(2);

    private final int workflowStatusType;

    private WorkflowStatusType(int workflowStatusType) {
        this.workflowStatusType = workflowStatusType;
    }

    public int getWorkflowStatusType() {
        return workflowStatusType;
    }

}
