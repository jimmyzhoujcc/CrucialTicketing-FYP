/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.daos.WorkflowStatusDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class WorkflowStatusService extends JdbcDaoSupport implements WorkflowStatusDao {

    @Override
    public WorkflowStatus getWorkflowStatusById(int workflowStatusId) {
        String sql = "SELECT * FROM workflow_status WHERE workflow_status_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowStatusId});
        if (rs.size() != 1) {
            return new WorkflowStatus();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<WorkflowStatus> getWorkflowStatusList() {
        String sql = "SELECT * FROM workflow_status";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<WorkflowStatus> rowMapper(List<Map<String, Object>> resultSet) {
        List<WorkflowStatus> workflowStatusList = new ArrayList<>();

        for (Map row : resultSet) {
            WorkflowStatus workflowStatus = new WorkflowStatus();

            workflowStatus.setStatusId((int) row.get("workflow_status_id"));
            workflowStatus.setStatusName((String) row.get("workflow_status_name"));
     
            workflowStatusList.add(workflowStatus);
        }
        return workflowStatusList;
    }
}
