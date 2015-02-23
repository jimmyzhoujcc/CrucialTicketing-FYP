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
    public WorkflowMap getWorkflowMapById(int workflowMapId) {
        String sql = "SELECT * FROM workflow_structure WHERE workflow_template_id=?";
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
                        workflowStatusService.getWorkflowStatusById((int) row.get("from_workflow_status_id")),
                        new Role(-1, null, null, null, -1),
                        new Queue(-1, null),
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
}
