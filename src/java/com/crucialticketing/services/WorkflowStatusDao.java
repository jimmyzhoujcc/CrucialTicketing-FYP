/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.WorkflowStatus;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowStatusDao extends DatabaseService {
    public WorkflowStatus getWorkflowStatusById(int workflowStatusId);
    
    public List<WorkflowStatus> getWorkflowStatusList();
    
    public List<WorkflowStatus> rowMapper(List<Map<String, Object>> resultSet);
}
