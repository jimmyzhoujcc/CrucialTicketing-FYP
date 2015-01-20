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
public class ApplicationControlService implements DatabaseService {

    @Autowired
    DataSource dataSource;

    @Autowired
    TicketTypeService ticketTypeService;

    @Override
    public void insert(Object o) {

    }

    @Override
    public List<Object> select(String field, String value) {

        ApplicationControl applicationControl;
        List<Object> o = new ArrayList<>();

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
                + "application_control." + field + "='" + value + "'";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> applicationControlData = jdbcTemplate.queryForList(sql);

        if (!applicationControlData.isEmpty()) {

            for (Map<String, Object> applicationControlItem : applicationControlData) {

                applicationControl = new ApplicationControl();

                applicationControl.setApplicationControlId((int) applicationControlItem.get("application_control_id"));

                applicationControl.setTicketType(new TicketType(
                        (int) applicationControlItem.get("ticket_type_id"),
                        (String) applicationControlItem.get("ticket_type_name")));

                applicationControl.setApplication(new Application(
                        (int) applicationControlItem.get("application_id"),
                        (String) applicationControlItem.get("application_name")));

                applicationControl.setWorkflow(new Workflow(
                        (int) applicationControlItem.get("workflow_template_id"),
                        (String) applicationControlItem.get("workflow_template_name")));

                applicationControl.setSeverity(new Severity(
                        (int) applicationControlItem.get("severity_id"),
                        (int) applicationControlItem.get("severity_level"),
                        (String) applicationControlItem.get("severity_name")));

                o.add((Object) applicationControl);
            }
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
