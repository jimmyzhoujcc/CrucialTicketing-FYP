/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.WorkflowChangeLogDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowChangeLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class WorkflowChangeLogService extends JdbcDaoSupport implements WorkflowChangeLogDao {

    @Autowired
    WorkflowService workflowService;
    
    @Autowired
    TicketService ticketService;
    
    @Autowired
    UserService userService;
    
    @Override
    public void insertChangeLog(WorkflowChangeLog workflowChangeLog) {
        String sql = "INSERT INTO workflow_change_log "
                + "(workflow_id, workflow_name, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            workflowChangeLog.getWorkflow().getWorkflowId(), 
            workflowChangeLog.getWorkflow().getWorkflowName(), 
            workflowChangeLog.getTicket().getTicketId(), 
            workflowChangeLog.getRequestor().getUserId(), 
            workflowChangeLog.getStamp(), 
            workflowChangeLog.getWorkflow().getActiveFlag().getActiveFlag()
        });
    }

    @Override
    public List<WorkflowChangeLog> getChangeLogListByWorkflowId(int workflowId) {
        String sql = "SELECT * FROM workflow_change_log WHERE workflow_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql,
                new Object[]{workflowId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<WorkflowChangeLog> getChangeLogListByTicketId(int ticketId) {
        String sql = "SELECT * FROM workflow_change_log WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql,
                new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<WorkflowChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<WorkflowChangeLog> changeLogList = new ArrayList<>();
        Map<Integer, Workflow> retrievedWorkflowList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();

        for (Map row : resultSet) {
            WorkflowChangeLog changeLog = new WorkflowChangeLog();

            changeLog.setWorkflowChangeLogId((int)row.get("workflow_change_log_id"));
            
            // Role checks
            if (retrievedWorkflowList.containsKey((int) row.get("workflow_id"))) {
                changeLog.setWorkflow(retrievedWorkflowList.get((int) row.get("workflow_id")));
            } else {
                Workflow workflow = workflowService.getWorkflow((int) row.get("workflow_id"));
                changeLog.setWorkflow(workflow);
                retrievedWorkflowList.put((int) row.get("workflow_id"), workflow);
            }
            
            // Ticket checks
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                changeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false);
                changeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }
            
            // User Checks
            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                changeLog.setRequestor(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                changeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }
         
            changeLog.getWorkflow().setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag"))+2]);
            
            changeLog.setStamp((int) row.get("stamp"));
            
            changeLogList.add(changeLog);
        }
        return changeLogList;
    }
    
}
