/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Daniel Foley
 */
public class WorkflowService implements DatabaseService {

    @Autowired
    DataSource dataSource;

    @Override
    public void insert(Object o) {

    }

    @Override
    public List<Object> select(String field, String value) {
        List<Object> o = new ArrayList<>();
        Workflow workflow = new Workflow();
        
        String sql = "SELECT workflow_structure.workflow_structure_id, "
                + "from_workflow_status.workflow_status_id AS from_workflow_status_id, "
                + "from_workflow_status.workflow_status_name AS from_workflow_status_name, "
                + "to_workflow_status.workflow_status_id AS to_workflow_status_id, "
                + "to_workflow_status.workflow_status_name AS to_workflow_status_name, "
                + "workflow_structure.role_id, workflow_structure.queue_id "
                + "FROM workflow_structure "
                + "JOIN workflow_status AS from_workflow_status ON workflow_structure.from_workflow_status_id=from_workflow_status.workflow_status_id "
                + "JOIN workflow_status AS to_workflow_status ON workflow_structure.to_workflow_status_id=to_workflow_status.workflow_status_id "
                + "JOIN role ON role.role_id=workflow_structure.role_id "
                + "JOIN queue ON queue.queue_id=workflow_structure.queue_id "
                + "WHERE workflow_structure."+ field + "=" + value;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> workflowInfo = jdbcTemplate.queryForList(sql);
        
        for (Map<String, Object> workflowItem : workflowInfo) {
            workflow.addStatus(
                    new WorkflowStatus((int)workflowItem.get("to_workflow_status_id"), (String)workflowItem.get("to_workflow_status_name")), 
                    new Role((int)workflowItem.get("role_id"), (String)workflowItem.get("role_name")), 
                    new Queue((int)workflowItem.get("queue_id"), (String)workflowItem.get("queue_name")));
        }
        
        o.add((Object)workflow);
        
        return o;
    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public List<Object> getTable() {
        return new ArrayList<Object>();
    }
}
