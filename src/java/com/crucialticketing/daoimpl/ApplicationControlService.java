/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.daos.ApplicationControlDao;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.entities.ApplicationControlChangeLog;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author DanFoley
 */
public class ApplicationControlService extends JdbcDaoSupport implements ApplicationControlDao {

    @Autowired
    TicketTypeService ticketTypeService;

    @Autowired
    SeverityService severityService;

    @Autowired
    RoleService roleService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    WorkflowMapService workflowMapService;

    @Autowired
    ApplicationControlChangeLogService applicationControlChangeLogService;

    @Override
    public int insertApplicationControl(final ApplicationControl applicationControl, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO application_control "
                + "(ticket_type_id, application_id, workflow_id, "
                + "severity_id, role_id, sla_clock, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, applicationControl.getTicketType().getTicketTypeId());
                ps.setInt(2, applicationControl.getApplication().getApplicationId());
                ps.setInt(3, applicationControl.getWorkflow().getWorkflowId());
                ps.setInt(4, applicationControl.getSeverity().getSeverityId());
                ps.setInt(5, applicationControl.getRole().getRoleId());
                ps.setInt(6, applicationControl.getSlaClock());
                ps.setInt(7, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedApplicationControlId = holder.getKey().intValue();
        applicationControl.setApplicationControlId(insertedApplicationControlId);
        applicationControl.setActiveFlag(ActiveFlag.INCOMPLETE);

        // Timestamp is set to 0 as it is generated on insert
        applicationControlChangeLogService.insertChangeLog(
                new ApplicationControlChangeLog(applicationControl, ticket, requestor)
        );

        return insertedApplicationControlId;
    }

    @Override
    public ApplicationControl getApplicationControlById(int applicationControlId, boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control WHERE application_control_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{applicationControlId});
        if (rs.size() != 1) {
            return new ApplicationControl();
        }
        return (this.rowMapper(rs, populateWorkflowMap)).get(0);
    }

    @Override
    public ApplicationControl getApplicationControlByCriteria(int ticketTypeId, int applicationId, int severityId, boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control WHERE ticket_type_id=? AND application_id=? AND severity_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ticketTypeId, applicationId, severityId});
        if (rs.size() != 1) {
            return new ApplicationControl();
        }
        return (this.rowMapper(rs, populateWorkflowMap)).get(0);
    }

    @Override
    public boolean doesApplicationControlExist(int ticketTypeId, int applicationId, int severityId) {
        String sql = "SELECT COUNT(application_control_id) AS result FROM application_control "
                + "WHERE ticket_type_id=? AND application_id=? AND severity_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ticketTypeId, applicationId, severityId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesApplicationControlExistInOnline(int ticketTypeId, int applicationId, int severityId) {
        String sql = "SELECT COUNT(application_control_id) AS result FROM application_control "
                + "WHERE ticket_type_id=? AND application_id=? AND severity_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ticketTypeId, applicationId, severityId, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesApplicationControlExistInOnlineOrOffline(int ticketTypeId, int applicationId, int severityId) {
        String sql = "SELECT COUNT(application_control_id) AS result FROM application_control "
                + "WHERE ticket_type_id=? AND application_id=? AND severity_id=? AND active_flag>?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ticketTypeId, applicationId, severityId, ActiveFlag.UNPROCESSED.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<ApplicationControl> getApplicationControlListByCriteria(String[] inputList, Object[] objectList, int count) {
        String sql = "SELECT * FROM application_control WHERE ";

        for (int i = 0; i < count; i++) {
            sql += inputList[i] + "='" + objectList[i] + "'";

            if ((i + 1) < count) {
                sql += " AND ";
            }
        }

        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs, false);
    }

    @Override
    public List<ApplicationControl> getIncompleteList(boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return rowMapper(rs, populateWorkflowMap);
    }

    @Override
    public List<ApplicationControl> getUnprocessedList(boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return rowMapper(rs, populateWorkflowMap);
    }

    @Override
    public List<ApplicationControl> getOnlineList(boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return rowMapper(rs, populateWorkflowMap);
    }

    @Override
    public List<ApplicationControl> getOfflineList(boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return rowMapper(rs, populateWorkflowMap);
    }

    @Override
    public void updateToUnprocessed(int applicationControlId, Ticket ticket, User requestor) {
        String sql = "UPDATE application_control SET active_flag=? WHERE application_control_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), applicationControlId});

        applicationControlChangeLogService.insertChangeLog(
                new ApplicationControlChangeLog(this.getApplicationControlById(applicationControlId, false),
                        ticket, requestor)
        );
    }

    @Override
    public void updateToOnline(int applicationControlId, Ticket ticket, User requestor) {
        String sql = "UPDATE application_control SET active_flag=? WHERE application_control_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), applicationControlId});

        applicationControlChangeLogService.insertChangeLog(
                new ApplicationControlChangeLog(this.getApplicationControlById(applicationControlId, false),
                        ticket, requestor)
        );
    }

    @Override
    public void updateToOffline(int applicationControlId, Ticket ticket, User requestor) {
        String sql = "UPDATE application_control SET active_flag=? WHERE application_control_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), applicationControlId});

        applicationControlChangeLogService.insertChangeLog(
                new ApplicationControlChangeLog(this.getApplicationControlById(applicationControlId, false),
                        ticket, requestor)
        );
    }

    @Override
    public void removeApplicationControl(int applicationControlId) {
        String sql = "DELETE FROM application_control SET WHERE application_control_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{applicationControlId});
    }

    @Override
    public List<ApplicationControl> rowMapper(List<Map<String, Object>> resultSet, boolean populateWorkflowMap) {
        List<ApplicationControl> applicationControlList = new ArrayList<>();

        for (Map row : resultSet) {
            ApplicationControl applicationControl = new ApplicationControl();

            applicationControl.setApplicationControlId((int) row.get("application_control_id"));

            // Gets ticket type
            applicationControl.setTicketType(ticketTypeService.getTicketTypeById((int) row.get("ticket_type_id")));

            // Gets severity 
            applicationControl.setSeverity(severityService.getSeverityById((int) row.get("severity_id")));

            // Gets application
            applicationControl.setApplication(applicationService.getApplicationById((int) row.get("application_id")));

            // Gets role
            applicationControl.setRole(roleService.getRoleById((int) row.get("role_id")));

            // Gets workflow
            Workflow workflow = workflowService.getWorkflow((int) row.get("workflow_id"));

            // If flag is true then the mapping of the workflow is also obtained
            if (populateWorkflowMap) {
                workflow.setWorkflowMap(workflowMapService.getWorkflowMapById((int) row.get("workflow_id")));
            }

            applicationControl.setWorkflow(workflow);

            applicationControl.setSlaClock((int) row.get("sla_clock"));

            applicationControl.setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]);

            // 
            applicationControlList.add(applicationControl);
        }

        return applicationControlList;
    }
}
