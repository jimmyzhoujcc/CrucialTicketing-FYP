/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.WorkflowChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowChangeLogDao {
    public void insertChangeLog(WorkflowChangeLog workflowChangeLog);

    public List<WorkflowChangeLog> getChangeLogListByWorkflowId(int workflowId);

    public List<WorkflowChangeLog> getChangeLogListByTicketId(int ticketId);

    public List<WorkflowChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
