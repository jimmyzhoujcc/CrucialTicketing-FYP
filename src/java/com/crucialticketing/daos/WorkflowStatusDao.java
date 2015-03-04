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

    public WorkflowStatus getWorkflowStatusById(int workflowStatusId);
    
    public WorkflowStatus getWorkflowStatusByName(String workflowStatusName);
    
    public boolean doesWorkflowStatusExistInOnlineById(int workflowStatusId);
    
    public boolean doesWorkflowStatusExistInOnlineByName(String workflowStatusName);
    
    public boolean doesWorkflowStatusExistInOnlineOrOfflineByName(String workflowStatusName);

    public List<WorkflowStatus> getIncompleteWorkflowStatusList();

    public List<WorkflowStatus> getUnprocessedWorkflowStatusList();
    
    public List<WorkflowStatus> getOnlineWorkflowStatusList();
    
    public List<WorkflowStatus> getOfflineWorkflowStatusList();
    
    public void updateToUnprocessed(int workflowStatusId, Ticket ticket, User requestor);
    
    public void updateToOnline(int workflowStatusId, Ticket ticket, User requestor);
    
    public void updateToOffline(int workflowStatusId, Ticket ticket, User requestor);
    
    public List<WorkflowStatus> rowMapper(List<Map<String, Object>> resultSet);
}
