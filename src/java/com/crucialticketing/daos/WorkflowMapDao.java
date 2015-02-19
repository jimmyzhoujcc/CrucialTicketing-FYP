/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.WorkflowMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowMapDao {
    
    public WorkflowMap getWorkflowMapById(int workflowMapId);

    public List<WorkflowMap> rowMapper(List<Map<String, Object>> resultSet);
}
