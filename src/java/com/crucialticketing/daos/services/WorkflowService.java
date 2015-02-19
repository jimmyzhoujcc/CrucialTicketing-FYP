/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Workflow;
import com.crucialticketing.daos.WorkflowDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Daniel Foley
 */
public class WorkflowService extends JdbcDaoSupport implements WorkflowDao {
    
    @Override
    public Workflow getWorkflowById(int workflowId) {
        String sql = "SELECT * FROM workflow_template WHERE workflow_template_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowId});
        if (rs.size() != 1) {
            return new Workflow();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<Workflow> getWorkflowList() {
        String sql = "SELECT * FROM workflow_template";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
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
}
