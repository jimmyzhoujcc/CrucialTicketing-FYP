/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.TicketChangeLog;
import com.crucialticketing.entities.TicketChangeLogEntry;
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
public class TicketChangeLogService extends JdbcDaoSupport implements ChangeLogDao {

    @Autowired
    ApplicationControlService applicationControlService;

    @Autowired
    UserService userService;

    @Autowired
    WorkflowStatusService workflowStatusService;

    @Override
    public void addChangeLogEntry(int ticketId, int applicationControlId, int workflowStatusId, int userId) {
        String sql = "INSERT INTO ticket_change_log (ticket_id, application_control_id, workflow_status_id, requestor_user_id, stamp) "
                + "VALUES(?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{ticketId, applicationControlId, workflowStatusId, userId, getTimestamp()});
    }

    @Override
    public TicketChangeLog getChangeLogByTicketId(int ticketId, boolean populateAll) {
        String sql = "SELECT * FROM ticket_change_log WHERE ticket_id=? ORDER BY stamp ASC";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new TicketChangeLog();
        }
        return this.rowMapper(rs, populateAll);
    }

    @Override
    public TicketChangeLog rowMapper(List<Map<String, Object>> resultSet, boolean populateAll) {
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, ApplicationControl> applicationControlList = new HashMap<>();
        Map<Integer, WorkflowStatus> workflowStatusList = new HashMap<>();

        TicketChangeLog changeLog = new TicketChangeLog();
        int i = 0;
        boolean populateWorkflow = false;

        for (Map row : resultSet) {
            if ((populateAll) || (i == 0) || (i == (resultSet.size() - 1))) {
                if (i == (resultSet.size() - 1)) {
                    populateWorkflow = true;
                }

                TicketChangeLogEntry changeLogEntry = new TicketChangeLogEntry();

                changeLogEntry.setChangeLogEntryId((int) row.get("ticket_change_log_id"));
                changeLogEntry.setTicket(new Ticket((int) row.get("ticket_id")));

                // Application control 
                if ((populateWorkflow) || (populateAll)) {
                    changeLogEntry.setApplicationControl(
                            applicationControlService.getApplicationControlById((int) row.get("application_control_id"), true));
                } else {
                    if (applicationControlList.containsKey((int) row.get("application_control_id"))) {
                        changeLogEntry.setApplicationControl(applicationControlList.get((int) row.get("application_control_id")));
                    } else {
                        changeLogEntry.setApplicationControl(
                                applicationControlService.getApplicationControlById((int) row.get("application_control_id"), false));
                        applicationControlList.put((int) row.get("application_control_id"), changeLogEntry.getApplicationControl());
                    }
                }

                // Workflow status
                if (workflowStatusList.containsKey((int) row.get("workflow_status_id"))) {
                    changeLogEntry.setWorkflowStatus(workflowStatusList.get((int) row.get("workflow_status_id")));
                } else {
                    changeLogEntry.setWorkflowStatus(workflowStatusService.getWorkflowStatus((int) row.get("workflow_status_id")));
                    workflowStatusList.put((int) row.get("workflow_status_id"), changeLogEntry.getWorkflowStatus());
                }

                // User list 
                if (userList.containsKey((int) row.get("requestor_user_id"))) {
                    changeLogEntry.setUser(userList.get((int) row.get("requestor_user_id")));
                } else {
                    changeLogEntry.setUser(userService.getUserById((int) row.get("requestor_user_id"), false));
                    userList.put((int) row.get("requestor_user_id"), changeLogEntry.getUser());
                }

                changeLogEntry.setStamp((int) row.get("stamp"));

                changeLog.getChangeLog().add(changeLogEntry);
            }
            i++;
        }
        return changeLog;
    }

}
