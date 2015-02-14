/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.WorkflowMap;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStep;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class WorkflowMapService implements WorkflowMapDao {

    String selectByMapId = "SELECT * FROM workflow_structure WHERE workflow_template_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public WorkflowMap getWorkflowMapById(int workflowMapId) {
        String sql = selectByMapId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{workflowMapId});
        if (rs.size() < 1) {
            return new WorkflowMap();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<WorkflowMap> getWorkflowMapList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WorkflowMap> rowMapper(List<Map<String, Object>> resultSet) {
        List<WorkflowMap> workflowMapList = new ArrayList<>();

        WorkflowMap workflowMap = new WorkflowMap();
        WorkflowStatusService workflowStatusService = new WorkflowStatusService();
        workflowStatusService.setCon(jdbcTemplate);
        
        RoleService roleService = new RoleService();
        roleService.setCon(jdbcTemplate);
        
        QueueService queueService = new QueueService();
        queueService.setCon(jdbcTemplate);

        for (Map row : resultSet) {
            if (!workflowMap.doesStepExist((int) row.get("from_workflow_status_id"))) {
                workflowMap.addStep(
                        workflowStatusService.getWorkflowStatusById((int) row.get("from_workflow_status_id")),
                        new Role(-1, "n/a"),
                        new Queue(-1, "n/a"), 
                        0);
            }

            if (!workflowMap.doesStepExist((int) row.get("to_workflow_status_id"))) {
                workflowMap.addStep(
                        workflowStatusService.getWorkflowStatusById((int) row.get("to_workflow_status_id")),
                        roleService.getRoleById((int) row.get("role_id")),
                        queueService.getQueueById((int) row.get("queue_id")), 
                        (int) row.get("clock_active"));
            }

            WorkflowStep workflowStageStart = workflowMap.getWorkflowStageByStatus((int) row.get("from_workflow_status_id"));
            WorkflowStep workflowStageEnd = workflowMap.getWorkflowStageByStatus((int) row.get("to_workflow_status_id"));
            workflowStageStart.addNextNode(workflowStageEnd);
        }
        workflowMapList.add(workflowMap);

        return workflowMapList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }

}
