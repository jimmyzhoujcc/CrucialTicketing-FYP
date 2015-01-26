/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Workflow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Daniel Foley
 */
public class WorkflowService implements WorkflowDao {

    String selectByWorkflowId = "SELECT * FROM workflow_template WHERE workflow_template_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;
    
    @Override
    public Workflow getWorkflowById(int workflowId) {
        String sql = selectByWorkflowId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{workflowId});
        if (rs.size() != 1) {
            return new Workflow();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<Workflow> getWorkflowList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Workflow> rowMapper(List<Map<String, Object>> resultSet) {
        List<Workflow> workflowList = new ArrayList<>();

        for (Map row : resultSet) {
            Workflow workflow = new Workflow();

            workflow.setWorkflowId((int) row.get("workflow_template_id"));
            workflow.setWorkflowName((String) row.get("workflow_template_name"));

            workflowList.add(workflow);
        }
        return workflowList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }
}
