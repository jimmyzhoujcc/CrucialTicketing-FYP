/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.WorkflowStatusChangeLogDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserChangeLog;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStatusChangeLog;
import static com.crucialticketing.util.Timestamp.getTimestamp;
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
public class WorkflowStatusChangeLogService extends JdbcDaoSupport implements WorkflowStatusChangeLogDao {

    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;

    @Autowired
    WorkflowStatusService workflowStatusService;

    @Override
    public void insertChangeLog(WorkflowStatusChangeLog workflowStatusChangeLog) {
        String sql = "INSERT user_change_log "
                + "(workflow_status_id, workflow_status_name, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            workflowStatusChangeLog.getWorkflowStatus().getStatusId(),
            workflowStatusChangeLog.getWorkflowStatus().getStatusName(),
            workflowStatusChangeLog.getTicket().getTicketId(),
            workflowStatusChangeLog.getRequestor().getUserId(),
            getTimestamp(),
            workflowStatusChangeLog.getActiveFlag().getActiveFlag()
        });
    }

    @Override
    public List<WorkflowStatusChangeLog> getChangeLogByWorkflowStatusId(int workflowStatusId) {
        String sql = "SELECT * FROM workflow_status_change_log WHERE workflow_status_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{workflowStatusId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<WorkflowStatusChangeLog> getChangeLogByTicketId(int ticketId) {
        String sql = "SELECT * FROM workflow_status_change_log WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<WorkflowStatusChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<WorkflowStatusChangeLog> changeLogList = new ArrayList<>();
        Map<Integer, WorkflowStatus> retrievedWorkflowStatusList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();

        for (Map row : resultSet) {
            WorkflowStatusChangeLog changeLog = new WorkflowStatusChangeLog();

            changeLog.setWorkflowStatusChangeLogId((int) row.get("workflow_status_change_log_id"));

            // Workflow Status List
            if (retrievedWorkflowStatusList.containsKey((int) row.get("workflow_status_id"))) {
                changeLog.setWorkflowStatus(retrievedWorkflowStatusList.get((int) row.get("workflow_status_id")));
            } else {
                WorkflowStatus workflowStatus = workflowStatusService.getWorkflowStatusById((int) row.get("workflow_status_id"));
                changeLog.setWorkflowStatus(workflowStatus);
                retrievedWorkflowStatusList.put((int) row.get("workflow_status_id"), workflowStatus);
            }

            // User List
            if (retrievedUserList.containsKey((int) row.get("user_id"))) {
                changeLog.setRequestor(retrievedUserList.get((int) row.get("user_id")));
            } else {
                User user = userService.getUserById((int) row.get("user_id"), false);
                changeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("user_id"), user);
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

            changeLog.getWorkflowStatus().setStatusName((String) row.get("workflow_status_name"));
            changeLog.getWorkflowStatus().setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]);

            changeLog.setStamp((int) row.get("stamp"));

            changeLogList.add(changeLog);
        }
        return changeLogList;
    }

}
