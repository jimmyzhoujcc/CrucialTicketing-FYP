/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Workflow;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowDao extends DatabaseService {
    public Workflow getWorkflowById(int workflowId);
    
    public List<Workflow> getWorkflowList();
    
    public List<Workflow> rowMapper(List<Map<String, Object>> resultSet);
}