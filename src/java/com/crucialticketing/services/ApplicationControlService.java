/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.TicketType;
import com.crucialticketing.entities.Workflow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class ApplicationControlService implements ApplicationControlDao {

    String selectByApplicationControlId = "SELECT * FROM application_control WHERE application_control_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public ApplicationControl getApplicationControlById(int applicationControlId, boolean populateWorkflowMap) {
        String sql = selectByApplicationControlId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{applicationControlId});
        if (rs.size() != 1) {
            return new ApplicationControl();
        }
        return (this.rowMapper(rs, populateWorkflowMap)).get(0);
    }

    @Override
    public List<ApplicationControl> getApplicationControlList(boolean populateWorkflowMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ApplicationControl> rowMapper(List<Map<String, Object>> resultSet, boolean populateWorkflowMap) {
        List<ApplicationControl> applicationControlList = new ArrayList<>();
        
        TicketTypeService ticketTypeService = new TicketTypeService();
        ticketTypeService.setCon(jdbcTemplate);
        
        SeverityService severityService = new SeverityService();
        severityService.setCon(jdbcTemplate);
        
        ApplicationService applicationService = new ApplicationService();
        applicationService.setCon(jdbcTemplate);
        
        WorkflowService workflowService = new WorkflowService();
        workflowService.setCon(jdbcTemplate);
        
        WorkflowMapService workflowMapService = new WorkflowMapService();
        workflowMapService.setCon(jdbcTemplate);

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
            
            // 
            applicationControlList.add(applicationControl);
        }
        
        return applicationControlList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }

}
