/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.daos.WorkflowStatusDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.RoleChangeLog;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.WorkflowStatusChangeLog;
import static com.crucialticketing.util.Timestamp.getTimestamp;
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
public class WorkflowStatusService extends JdbcDaoSupport implements WorkflowStatusDao {

    @Autowired
    WorkflowStatusChangeLogService changeLogService;
    
    @Override
    public int insertWorkflowStatus(final WorkflowStatus workflowStatus, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO workflow_status "
                + "(workflow_status_name, active_flag) "
                + "VALUES "
                + "(?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, workflowStatus.getWorkflowStatusName());
                ps.setInt(2, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedId = holder.getKey().intValue();

        changeLogService.insertChangeLog(
                new WorkflowStatusChangeLog(workflowStatus, ticket, requestor, getTimestamp())
        );

        return insertedId;

    }

    @Override
    public WorkflowStatus getWorkflowStatus(int workflowStatusId) {
        String sql = "SELECT * FROM workflow_status WHERE workflow_status_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowStatusId});
        if (rs.size() != 1) {
            return new WorkflowStatus();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public WorkflowStatus getWorkflowStatus(String workflowStatusName) {
        String sql = "SELECT * FROM workflow_status WHERE workflow_status_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowStatusName});
        if (rs.size() != 1) {
            return new WorkflowStatus();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public boolean doesWorkflowStatusExistInOnline(int workflowStatusId) {
        String sql = "SELECT COUNT(workflow_status_id) AS result FROM workflow_status "
                + "WHERE workflow_status_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowStatusId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesWorkflowStatusExistInOnline(String workflowStatusName) {
        String sql = "SELECT COUNT(workflow_status_name) AS result FROM workflow_status "
                + "WHERE workflow_status_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowStatusName});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesWorkflowStatusExistInOnlineOrOffline(String workflowStatusName) {
        String sql = "SELECT COUNT(workflow_status_name) AS result FROM workflow_status "
                + "WHERE workflow_status_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowStatusName});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<WorkflowStatus> getIncompleteWorkflowStatusList() {
        String sql = "SELECT * FROM workflow_status WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<WorkflowStatus> getUnprocessedWorkflowStatusList() {
        String sql = "SELECT * FROM workflow_status WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<WorkflowStatus> getOnlineWorkflowStatusList() {
        String sql = "SELECT * FROM workflow_status WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<WorkflowStatus> getOfflineWorkflowStatusList() {
        String sql = "SELECT * FROM workflow_status WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public void updateToUnprocessed(int workflowStatusId, Ticket ticket, User requestor) {
        String sql = "UPDATE workflow_status SET active_flag=? WHERE workflow_status_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), workflowStatusId});
        
        changeLogService.insertChangeLog(
          new WorkflowStatusChangeLog(this.getWorkflowStatus(workflowStatusId), ticket, requestor, getTimestamp())
        );
    }
    
    @Override
    public void updateToOnline(int workflowStatusId, Ticket ticket, User requestor) {
        String sql = "UPDATE workflow_status SET active_flag=? WHERE workflow_status_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), workflowStatusId});
        
        changeLogService.insertChangeLog(
          new WorkflowStatusChangeLog(this.getWorkflowStatus(workflowStatusId), ticket, requestor, getTimestamp())
        );
    }
    
    @Override
    public void updateToOffline(int workflowStatusId, Ticket ticket, User requestor) {
        String sql = "UPDATE workflow_status SET active_flag=? WHERE workflow_status_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), workflowStatusId});
        
        changeLogService.insertChangeLog(
          new WorkflowStatusChangeLog(this.getWorkflowStatus(workflowStatusId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public List<WorkflowStatus> rowMapper(List<Map<String, Object>> resultSet) {
        List<WorkflowStatus> workflowStatusList = new ArrayList<>();

        for (Map row : resultSet) {
            WorkflowStatus workflowStatus = new WorkflowStatus();

            workflowStatus.setWorkflowStatusId((int) row.get("workflow_status_id"));
            workflowStatus.setWorkflowStatusName((String) row.get("workflow_status_name"));
            workflowStatus.setBaseWorkflowStatus((int)row.get("base") != 0);
            workflowStatus.setClosureWorkflowStatus((int)row.get("closure") != 0);
            workflowStatus.setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag"))+2]);

            workflowStatusList.add(workflowStatus);
        }
        return workflowStatusList;
    }
}
