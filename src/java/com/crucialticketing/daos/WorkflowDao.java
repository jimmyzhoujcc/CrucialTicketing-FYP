/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.Workflow;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface WorkflowDao {

    public int insertWorkflow(final Workflow workflow, Ticket ticket, User requestor);

    public Workflow getWorkflow(int workflowId);

    public Workflow getWorkflow(String workflowName);

    public boolean doesWorkflowExist(String workflowName);

    public boolean doesWorkflowExistInOnline(int workflowId);

    public boolean doesWorkflowExistInOnline(String workflowName);

    public boolean doesWorkflowExistInOnlineOrOffline(String workflowName);

    public List<Workflow> getListByCriteria(String[] inputList, Object[] objectList, int count);
    
    public List<Workflow> getList();
    
    public List<Workflow> getIncompleteList();

    public List<Workflow> getUnprocessedList();

    public List<Workflow> getOnlineList();

    public List<Workflow> getOfflineList();

    public void updateToUnprocessed(int workflowId, Ticket ticket, User requestor);

    public void updateToOnline(int workflowId, Ticket ticket, User requestor);

    public void updateToOffline(int workflowId, Ticket ticket, User requestor);

    public void removeWorkflow(int workflowId);
    
    public List<Workflow> rowMapper(List<Map<String, Object>> resultSet);
}
