/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.ApplicationControl;
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
        
        String sql = "SELECT * FROM application_control WHERE application_control_id= " + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> applicationControlData = jdbcTemplate.queryForList(sql);
        
        applicationControl.setApplicationControlId((int)applicationControlData.get(0).get("application_control_id"));
        applicationControl.setTicketType(ticketTypeService.getTicketTypeById((String)applicationControlData.get(0).get("ticket_type_id")));
        applicationControlData.get(0).get("application_id");
        applicationControlData.get(0).get("workflow_template_id");
        
        return new ApplicationControl();
    }
}
