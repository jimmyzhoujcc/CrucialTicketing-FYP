/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowChange;
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
    UserService userService;
    
    @Autowired
    ApplicationControlService applicationControlService;
    
    @Autowired
    ApplicationService applicationService;
    
    @Override
    public void insert(Object o) {
        
    }

    @Override
    public List<Object> select(String field, String value) {
        List<Object> o = new ArrayList<>();
        Ticket ticket = new Ticket();
        
        String sql = "SELECT * FROM ticket WHERE "+ field +"='" + value + "'";
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> ticketInfo = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> tableItem : ticketInfo) {
           /* ticket.setMessageProcessor(userService.getUserById(String.valueOf(tableItem.get("message_processor_id"))));
            ticket.setCreatedBy(userService.getUserById(String.valueOf(tableItem.get("created_by_id"))));
            ticket.setReportedBy(userService.getUserById(String.valueOf(tableItem.get("reported_by_id"))));
            */
            
            ticket.setTicketId("10027587");
            ticket.setShortDescription("Test description");
            
            List<Object> applicationControlReturn = applicationControlService.select(
                    "application_control_id", 
                    String.valueOf(tableItem.get("application_control_id")));
            
            ticket.setApplicationControl((ApplicationControl)applicationControlReturn.get(0));
            Workflow workflow = new Workflow(1, "test");
            workflow.addStatus(new WorkflowStatus(1, "test status"), new Role(1, "test role"), new Queue(1, "test queue"));
            workflow.addStatus(new WorkflowStatus(2, "test status 2"), new Role(1, "test role"), new Queue(1, "test queue"));
            workflow.addStatus(new WorkflowStatus(2, "test status 2"), new Role(1, "test role"), new Queue(1, "test queue"));
            workflow.addStatus(new WorkflowStatus(2, "test status 2"), new Role(1, "test role"), new Queue(1, "test queue"));
            workflow.addStatus(new WorkflowStatus(2, "test status 2"), new Role(1, "test role"), new Queue(1, "test queue"));
     
            ticket.setWorkflow(workflow);
            o.add((Object)ticket);
            
        }
       // ticket.setTicketId(String.valueOf(ticketList.get(0).get("ticket_id")));
       // ticket.setShortDescription((String) ticketList.get(0).get("short_description"));
 
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
