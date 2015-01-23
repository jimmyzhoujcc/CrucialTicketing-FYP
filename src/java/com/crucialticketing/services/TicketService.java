/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.TicketLog;
import com.crucialticketing.entities.TicketType;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowStage;
import com.crucialticketing.entities.WorkflowStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Owner
 */
public class TicketService implements DatabaseService {

    @Autowired
    DataSource dataSource;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    TicketLogService ticketLogService;
    
    @Override
    public void insert(Object o) {

    }

    @Override
    public List<Object> select(String field, String value) {
        List<Object> o = new ArrayList<>();
        Ticket ticket = new Ticket();

        String sql = "SELECT ticket.ticket_id, ticket.short_description, "
                + "user1.user_id AS MP_user_id, user1.username AS MP_username, user1.password AS MP_password, "
                + "user1.first_name AS MP_first_name, user1.last_name AS MP_last_name, "
                + "user2.user_id AS C_user_id, user2.username AS C_username, user2.password AS C_password, "
                + "user2.first_name AS C_first_name, user2.last_name AS C_last_name, "
                + "user3.user_id AS R_user_id, user3.username AS R_username, user3.password AS R_password, "
                + "user3.first_name AS R_first_name, user3.last_name AS R_last_name, "
                + "workflow_status.workflow_status_id, workflow_status.workflow_status_name, "
                + "application_control.application_control_id, "
                + "ticket_type.ticket_type_id, ticket_type.ticket_type_name, "
                + "application.application_id, application.application_name, "
                + "workflow_template.workflow_template_id, workflow_template.workflow_template_name, "
                + "severity.severity_id, severity.severity_level, severity.severity_name "
                + "FROM "
                + "ticket JOIN user AS user1 ON user1.user_id=ticket.message_processor_id "
                + "JOIN user AS user2 ON user2.user_id=ticket.created_by_id "
                + "JOIN user AS user3 ON user3.user_id=ticket.reported_by_id "
                + "JOIN workflow_status ON ticket.current_status_id=workflow_status.workflow_status_id "
                + "JOIN application_control ON ticket.application_control_id=application_control.application_control_id "
                + "JOIN ticket_type ON ticket_type.ticket_type_id=application_control.ticket_type_id "
                + "JOIN application ON application.application_id=application_control.application_id "
                + "JOIN workflow_template ON workflow_template.workflow_template_id=application_control.workflow_template_id "
                + "JOIN severity ON severity.severity_id=application_control.severity_id "
                + "WHERE ticket." + field + "=" + value;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> ticketInfo = jdbcTemplate.queryForList(sql);
        
        for (Map<String, Object> tableItem : ticketInfo) {

            ticket.setTicketId(String.valueOf(tableItem.get("ticket_id")));
            ticket.setShortDescription((String) tableItem.get("short_description"));

            ticket.setMessageProcessor(
                    new User((int) tableItem.get("MP_user_id"),
                            (String) tableItem.get("MP_first_name"),
                            (String) tableItem.get("MP_last_name")));

            ticket.setCreatedBy(
                    new User((int) tableItem.get("C_user_id"),
                            (String) tableItem.get("C_first_name"),
                            (String) tableItem.get("C_last_name")));

            ticket.setReportedBy(
                    new User((int) tableItem.get("R_user_id"),
                            (String) tableItem.get("R_first_name"),
                            (String) tableItem.get("R_last_name")));

            List<Object> tempWorkflow = workflowService.select("workflow_template_id", String.valueOf(tableItem.get("workflow_template_id")));
            Workflow workflow = (Workflow)tempWorkflow.get(0);
            workflow.setWorkflowId((int)tableItem.get("workflow_template_id"));
            workflow.setWorkflowName((String)tableItem.get("workflow_template_name"));
            
            ticket.setApplicationControl(new ApplicationControl(
                    (int) tableItem.get("application_control_id"),
                    new TicketType((int) tableItem.get("ticket_type_id"), (String) tableItem.get("ticket_type_name")),
                    new Application((int) tableItem.get("application_id"), (String) tableItem.get("application_name")),
                    new Workflow(workflow.getWorkflowId(), workflow.getWorkflowName(), workflow.getWorkflow()),
                    new Severity((int) tableItem.get("severity_id"), (int) tableItem.get("severity_level"), (String) tableItem.get("severity_name"))));

            WorkflowStage currentWorkflowStage = ticket.getApplicationControl().getWorkflow().getWorkflowStageByStatus((int) tableItem.get("workflow_status_id"));
            ticket.setCurrentWorkflowStage(currentWorkflowStage);
            
            TicketLog ticketLog = (TicketLog)(ticketLogService.select("ticket_id", String.valueOf(tableItem.get("ticket_id")))).get(0);
           
            ticket.setTicketLog(ticketLog);
            
            o.add((Object) ticket);
        }
        
        if(o.isEmpty()) {
            o.add(new Ticket());
        }

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
