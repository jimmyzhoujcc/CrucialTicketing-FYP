/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.WorkflowMap;
import com.crucialticketing.entities.WorkflowStep;
import com.crucialticketing.daos.WorkflowMapDao;
import com.crucialticketing.entities.Workflow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class WorkflowMapService extends JdbcDaoSupport implements WorkflowMapDao {

    @Autowired
    WorkflowStatusService workflowStatusService;

    @Autowired
    RoleService roleService;

    @Autowired
    QueueService queueService;

    @Override
    public void insertWorkflowStep(Workflow workflow) {
        String sql = "INSERT INTO workflow_structure "
                + "(workflow_id, from_workflow_status_id, to_workflow_status_id, role_id, queue_id, clock_active) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        WorkflowMap workflowMap = workflow.getWorkflowMap();

        for (WorkflowStep workflowStep : workflowMap.getWorkflow()) {

            for (WorkflowStep nextWorkflowStep : workflowStep.getNextWorkflowStep()) {
                this.getJdbcTemplate().update(sql, new Object[]{
                    workflow.getWorkflowId(),
                    workflowStep.getWorkflowStatus().getWorkflowStatusId(),
                    nextWorkflowStep.getWorkflowStatus().getWorkflowStatusId(), 
                    nextWorkflowStep.getRole().getRoleId(), 
                    workflowStep.getQueue().getQueueId(), 
                    workflowStep.getClockActive()
                });
            }
        }

    }

    @Override
    public WorkflowMap getWorkflowMapById(int workflowMapId) {
        String sql = "SELECT * FROM workflow_structure WHERE workflow_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowMapId});
        if (rs.isEmpty()) {
            return null;
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<WorkflowMap> rowMapper(List<Map<String, Object>> resultSet) {
        List<WorkflowMap> workflowMapList = new ArrayList<>();

        WorkflowMap workflowMap = new WorkflowMap();

        for (Map row : resultSet) {
            if (!workflowMap.doesStepExist((int) row.get("from_workflow_status_id"))) {
                workflowMap.addStep(
                        workflowStatusService.getWorkflowStatus((int) row.get("from_workflow_status_id")),
                        new Role(-1, null, null, null, null),
                        new Queue(null, null, null),
                        0);
            }

            if (!workflowMap.doesStepExist((int) row.get("to_workflow_status_id"))) {
                workflowMap.addStep(
                        workflowStatusService.getWorkflowStatus((int) row.get("to_workflow_status_id")),
                        roleService.getRoleById((int) row.get("role_id")),
                        queueService.getQueueById((int) row.get("queue_id")),
                        (int) row.get("clock_active"));
            }

            WorkflowStep workflowStageStart = workflowMap.getWorkflowStageByStatus((int) row.get("from_workflow_status_id"));
            WorkflowStep workflowStageEnd = workflowMap.getWorkflowStageByStatus((int) row.get("to_workflow_status_id"));
            workflowStageStart.addNextNode(workflowStageEnd);
        }
        
        for (Map row : resultSet) {
            workflowMap.getWorkflowStageByStatus((int) row.get("from_workflow_status_id")).setClockActive((int) row.get("clock_active")); 
            workflowMap.getWorkflowStageByStatus((int) row.get("from_workflow_status_id")).setQueue(queueService.getQueueById((int) row.get("queue_id"))); 
        }
        
        workflowMapList.add(workflowMap);

        return workflowMapList;
    }
}
