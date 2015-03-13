/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.WorkflowStatus;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowStatusDao {
    public int insertWorkflowStatus(WorkflowStatus workflowStatus, Ticket ticket, User requestor);

    public WorkflowStatus getWorkflowStatus(int workflowStatusId);
    
    public WorkflowStatus getWorkflowStatus(String workflowStatusName);
    
    public boolean doesWorkflowStatusExistInOnline(int workflowStatusId);
    
    public boolean doesWorkflowStatusExistInOnline(String workflowStatusName);
    
    public boolean doesWorkflowStatusExistInOnlineOrOffline(String workflowStatusName);

    public List<WorkflowStatus> getIncompleteList();

    public List<WorkflowStatus> getUnprocessedList();
    
    public List<WorkflowStatus> getOnlineList();
    
    public List<WorkflowStatus> getOfflineList();
    
    public void updateToUnprocessed(int workflowStatusId, Ticket ticket, User requestor);
    
    public void updateToOnline(int workflowStatusId, Ticket ticket, User requestor);
    
    public void updateToOffline(int workflowStatusId, Ticket ticket, User requestor);
    
    public void removeWorkflowStatus(int workflowStatusId);
    
    public List<WorkflowStatus> rowMapper(List<Map<String, Object>> resultSet);
}
