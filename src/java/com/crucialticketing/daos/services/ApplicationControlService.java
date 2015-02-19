/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.daos.ApplicationControlDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class ApplicationControlService extends JdbcDaoSupport implements ApplicationControlDao {

    @Autowired
    TicketTypeService ticketTypeService;
    
    @Autowired
    SeverityService severityService;
    
    @Autowired
    ApplicationService applicationService;
    
    @Autowired
    WorkflowService workflowService;
    
    @Autowired
    WorkflowMapService workflowMapService;

    @Override
    public ApplicationControl getApplicationControlById(int applicationControlId, boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control WHERE application_control_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{applicationControlId});
        if (rs.size() != 1) {
            return new ApplicationControl();
        }
        return (this.rowMapper(rs, populateWorkflowMap)).get(0);
    }

    @Override
    public ApplicationControl getApplicationControlByCriteria(int ticketTypeId, int applicationId, int severityId, boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control WHERE ticket_type_id=? AND application_id=? AND severity_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketTypeId, applicationId, severityId});
        if (rs.isEmpty()) {
            return new ApplicationControl();
        }
        return (this.rowMapper(rs, populateWorkflowMap)).get(0);
    }

    @Override
    public List<ApplicationControl> getApplicationControlList(boolean populateWorkflowMap) {
        String sql = "SELECT * FROM application_control";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs, populateWorkflowMap);
    }

    @Override
    public List<ApplicationControl> rowMapper(List<Map<String, Object>> resultSet, boolean populateWorkflowMap) {
        List<ApplicationControl> applicationControlList = new ArrayList<>();

        for (Map row : resultSet) {
            ApplicationControl applicationControl = new ApplicationControl();

            applicationControl.setApplicationControlId((int) row.get("application_control_id"));

            // Gets ticket type
            applicationControl.setTicketType(ticketTypeService.getTicketTypeById((int) row.get("ticket_type_id")));

            // Gets severity 
            applicationControl.setSeverity(severityService.getSeverityById((int) row.get("severity_id")));

            // Gets application
            applicationControl.setApplication(applicationService.getApplicationById((int) row.get("application_id")));

            // Gets workflow
            Workflow workflow = workflowService.getWorkflowById((int) row.get("workflow_template_id"));

            // If flag is true then the mapping of the workflow is also obtained
            if (populateWorkflowMap) {
                workflow.setWorkflowMap(workflowMapService.getWorkflowMapById((int) row.get("workflow_template_id")));
            }

            applicationControl.setWorkflow(workflow);

            applicationControl.setSlaClock((int) row.get("sla_clock"));
            // 
            applicationControlList.add(applicationControl);
        }

        return applicationControlList;
    }
}
