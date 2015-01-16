/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.TicketType;
import com.crucialticketing.entities.Workflow;
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
public class ApplicationControlDaoImpl implements ApplicationControlDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    TicketTypeService ticketTypeService;

    @Override
    public void insertApplicationControl(ApplicationControl applicationControl) {

    }

    @Override
    public List<ApplicationControl> getApplicationControl() {
        List<ApplicationControl> applicationControl = new ArrayList<>();
        return applicationControl;
    }

    @Override
    public void updateApplicationControl(ApplicationControl applicationControl) {

    }

    @Override
    public void deleteApplicationControl(String id) {

    }

    @Override
    public ApplicationControl getApplicationControlById(String id) {

        ApplicationControl applicationControl = new ApplicationControl();

        String sql = "SELECT "
                + "application_control.application_control_id, "
                + "ticket_type.ticket_type_id, "
                + "ticket_type.ticket_type_name, "
                + "application.application_id, "
                + "application.application_name, "
                + "workflow_template.workflow_template_id, "
                + "workflow_template.workflow_template_name, "
                + "severity.severity_id, "
                + "severity.severity_level, "
                + "severity.severity_name "
                + "FROM "
                + "application_control, "
                + "ticket_type, "
                + "application, "
                + "workflow_template, "
                + "severity "
                + "WHERE "
                + "application_control.ticket_type_id=ticket_type.ticket_type_id AND "
                + "application_control.application_id=application.application_id AND "
                + "application_control.workflow_template_id=workflow_template.workflow_template_id AND "
                + "application_control.severity_id=severity.severity_id AND "
                + "application_control.application_control_id=" + id;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> applicationControlData = jdbcTemplate.queryForList(sql);

        applicationControl.setApplicationControlId((int) applicationControlData.get(0).get("application_control_id"));

        applicationControl.setTicketType(new TicketType(
                (int) applicationControlData.get(0).get("ticket_type_id"),
                (String) applicationControlData.get(0).get("ticket_type_name")));

        applicationControl.setApplication(new Application(
                (int) applicationControlData.get(0).get("application_id"),
                (String) applicationControlData.get(0).get("application_name")));

        applicationControl.setWorkflow(new Workflow(
                (int) applicationControlData.get(0).get("workflow_template_id"),
                (String) applicationControlData.get(0).get("workflow_template_name")));

        applicationControl.setSeverity(new Severity(
                (int) applicationControlData.get(0).get("severity_id"), 
                (int) applicationControlData.get(0).get("severity_level"), 
                (String) applicationControlData.get(0).get("severity_name")));
        
        return applicationControl;
    }
}
