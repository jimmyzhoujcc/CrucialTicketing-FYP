/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.ChangeLog;
import com.crucialticketing.entities.ChangeLogEntry;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.daos.ChangeLogDao;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class ChangeLogService extends JdbcDaoSupport implements ChangeLogDao {

    @Autowired
    ApplicationControlService applicationControlService;

    @Autowired
    UserService userService;

    @Autowired
    WorkflowStatusService workflowStatusService;

    @Override
    public void addChangeLogEntry(int ticketId, int applicationControlId, int userId, int statusId) {
        String sql = "INSERT INTO change_log (ticket_id, application_control_id, user_id, workflow_status_id, stamp) "
                + "VALUES(?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{ticketId, applicationControlId, userId, statusId, getTimestamp()});
    }

    @Override
    public ChangeLog getChangeLogByTicketId(int ticketId) {
        String sql = "SELECT * FROM change_log WHERE ticket_id=? ORDER BY stamp ASC";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new ChangeLog();
        }
        return this.rowMapper(rs);
    }

    @Override
    public ChangeLog rowMapper(List<Map<String, Object>> resultSet) {
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, ApplicationControl> applicationControlList = new HashMap<>();
        Map<Integer, WorkflowStatus> workflowStatusList = new HashMap<>();

        ChangeLog changeLog = new ChangeLog();

        for (Map row : resultSet) {
            ChangeLogEntry changeLogEntry = new ChangeLogEntry();

            changeLogEntry.setChangeLogEntryId((int) row.get("change_log_id"));
            changeLogEntry.setTicket(new Ticket((int) row.get("ticket_id")));

            // Application control 
            if (applicationControlList.containsKey((int) row.get("application_control_id"))) {
                changeLogEntry.setApplicationControl(applicationControlList.get((int) row.get("application_control_id")));
            } else {
                changeLogEntry.setApplicationControl(
                        applicationControlService.getApplicationControlById((int) row.get("application_control_id"), true));
                applicationControlList.put((int) row.get("application_control_id"), changeLogEntry.getApplicationControl());
            }

            // User list 
            if (userList.containsKey((int) row.get("user_id"))) {
                changeLogEntry.setUser(userList.get((int) row.get("user_id")));
            } else {
                changeLogEntry.setUser(userService.getUserById((int) row.get("user_id"), false));
                userList.put((int) row.get("user_id"), changeLogEntry.getUser());
            }

            // Workflow status
            if (workflowStatusList.containsKey((int) row.get("workflow_status_id"))) {
                changeLogEntry.setWorkflowStatus(workflowStatusList.get((int) row.get("workflow_status_id")));
            } else {
                changeLogEntry.setWorkflowStatus(workflowStatusService.getWorkflowStatusById((int) row.get("workflow_status_id")));
                workflowStatusList.put((int) row.get("workflow_status_id"), changeLogEntry.getWorkflowStatus());
            }

            changeLogEntry.setStamp((int) row.get("stamp"));

            changeLog.getChangeLog().add(changeLogEntry);
        }
        return changeLog;
    }

}
