/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.entities.Workflow;
import com.crucialticketing.daos.WorkflowDao;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.WorkflowChangeLog;
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
 * @author Daniel Foley
 */
public class WorkflowService extends JdbcDaoSupport implements WorkflowDao {

    @Autowired
    WorkflowChangeLogService changeLogService;

    @Override
    public int insertWorkflow(final Workflow workflow, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO workflow "
                + "(workflow_name, active_flag) "
                + "VALUES "
                + "(?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, workflow.getWorkflowName());
                ps.setInt(2, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedId = holder.getKey().intValue();
        workflow.setWorkflowId(insertedId);
        workflow.setActiveFlag(ActiveFlag.INCOMPLETE);

//        roleChangeLogService.insertRoleChange(
//          new RoleChangeLog(role, ticket, requestor, getTimestamp())
//        );
        return insertedId;
    }

    @Override
    public Workflow getWorkflow(int workflowId) {
        String sql = "SELECT * FROM workflow WHERE workflow_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowId});
        if (rs.size() != 1) {
            return new Workflow();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public Workflow getWorkflow(String workflowName) {
        String sql = "SELECT * FROM workflow WHERE workflow_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowName});
        if (rs.size() != 1) {
            return new Workflow();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public boolean doesWorkflowExist(String workflowName) {
        String sql = "SELECT COUNT(workflow_name) AS result FROM workflow "
                + "WHERE workflow_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowName});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesWorkflowExistInOnline(int workflowId) {
        String sql = "SELECT COUNT(workflow_id) AS result FROM workflow "
                + "WHERE workflow_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowId, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesWorkflowExistInOnline(String workflowName) {
        String sql = "SELECT COUNT(workflow_name) AS result FROM workflow "
                + "WHERE workflow_name=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowName, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesWorkflowExistInOnlineOrOffline(String workflowName) {
        String sql = "SELECT COUNT(workflow_name) AS result FROM workflow "
                + "WHERE workflow_name=? AND active_flag>?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowName, ActiveFlag.UNPROCESSED.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<Workflow> getListByCriteria(String[] inputList, Object[] objectList, int count) {
        String sql = "SELECT * FROM workflow WHERE ";

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
        return this.rowMapper(rs);
    }

    @Override
    public List<Workflow> getList() {
        String sql = "SELECT * FROM workflow";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Workflow> getIncompleteList() {
        String sql = "SELECT * FROM workflow WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Workflow> getUnprocessedList() {
        String sql = "SELECT * FROM workflow WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Workflow> getOnlineList() {
        String sql = "SELECT * FROM workflow WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Workflow> getOfflineList() {
        String sql = "SELECT * FROM workflow WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void updateToUnprocessed(int workflowId, Ticket ticket, User requestor) {
        String sql = "UPDATE workflow SET active_flag=? WHERE workflow_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), workflowId});

        changeLogService.insertChangeLog(
                new WorkflowChangeLog(this.getWorkflow(workflowId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOnline(int workflowId, Ticket ticket, User requestor) {
        String sql = "UPDATE workflow SET active_flag=? WHERE workflow_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), workflowId});

        changeLogService.insertChangeLog(
                new WorkflowChangeLog(this.getWorkflow(workflowId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOffline(int workflowId, Ticket ticket, User requestor) {
        String sql = "UPDATE workflow SET active_flag=? WHERE workflow_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), workflowId});

        changeLogService.insertChangeLog(
                new WorkflowChangeLog(this.getWorkflow(workflowId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void removeWorkflow(int workflowId) {
        String sql = "DELETE FROM workflow WHERE workflow_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{workflowId});
    }

    @Override
    public List<Workflow> rowMapper(List<Map<String, Object>> resultSet) {
        List<Workflow> workflowList = new ArrayList<>();

        for (Map row : resultSet) {
            Workflow workflow = new Workflow();

            workflow.setWorkflowId((int) row.get("workflow_id"));
            workflow.setWorkflowName((String) row.get("workflow_name"));
            workflow.setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]);

            workflowList.add(workflow);
        }
        return workflowList;
    }
}
