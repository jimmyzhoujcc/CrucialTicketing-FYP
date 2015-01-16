/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.WorkflowStatus;
import java.util.List;

/**
 *
 * @author Daniel Foley
 */
public interface WorkflowStatusDao {

    public void insertWorkflowStatus(WorkflowStatus workflowStatus);

    public List<WorkflowStatus> getWorkflowStatusList();

    public void updateWorkflowStatus(WorkflowStatus workflowStatus);

    public void deleteWorkflowStatus(String id);

    public WorkflowStatus getWorkflowStatusById(String id);
}
