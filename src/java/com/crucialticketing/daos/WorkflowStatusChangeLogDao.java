/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.WorkflowStatusChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowStatusChangeLogDao {
    public void insertChangeLog(WorkflowStatusChangeLog workflowStatusChangeLog);
    
    public List<WorkflowStatusChangeLog> getChangeLogByWorkflowStatusId(int workflowStatusId);
    
    public List<WorkflowStatusChangeLog> getChangeLogByTicketId(int ticketId);
    
    public List<WorkflowStatusChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
