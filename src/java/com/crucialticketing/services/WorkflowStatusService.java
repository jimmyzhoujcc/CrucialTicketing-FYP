/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.WorkflowStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class WorkflowStatusService implements WorkflowStatusDao {

   
    String selectByWorkflowStatusId = "SELECT * FROM workflow_status WHERE workflow_status_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public WorkflowStatus getWorkflowStatusById(int workflowStatusId) {
        String sql = selectByWorkflowStatusId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{workflowStatusId});
        if (rs.size() != 1) {
            return new WorkflowStatus();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<WorkflowStatus> getWorkflowStatusList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }
}
