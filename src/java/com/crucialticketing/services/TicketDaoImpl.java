/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Ticket;
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
public class TicketDaoImpl implements TicketDao {

    @Autowired
    DataSource dataSource;
    
    @Autowired
    UserService userService;
    
    @Autowired
    ApplicationControlService applicationControlService;
    
    @Autowired
    ApplicationService applicationService;
    
    
    @Override
    public void insertTicket(Ticket ticket) {
        /*
         String sql = "INSERT INTO user "
         + "(name,age) VALUES (?, ?)";

         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

         jdbcTemplate.update(
         sql,
         new Object[]{person.getName(), person.getAge()});
         */
    }

    @Override
    public List<Ticket> getTicketList() {

        List<Ticket> ticketList = new ArrayList();
        /*
         String sql = "SELECT * FROM person";

         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         List<Map<String, Object>> row = jdbcTemplate.queryForList(sql);

         for (Map<String, Object> dbItem : row) {
         userList.add(new Person(
         Integer.valueOf((String) dbItem.get("person_id")),
         (String) dbItem.get("name"),
         Integer.valueOf((String) dbItem.get("age"))));
         }
         */
        return ticketList;
    }

    @Override
    public void deleteTicket(String id) {
        /*
         String sql = "DELETE FROM user WHERE person_id=" + id;
         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         jdbcTemplate.update(sql);
         */
    }

    @Override
    public void updateTicket(Ticket ticket) {

        /*String sql = "UPDATE user SET name = ?,age = ? WHERE person_id = ?";
         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

         jdbcTemplate.update(
         sql,
         new Object[]{person.getName(), person.getAge()});
         */
    }

    @Override
    public Ticket getTicketById(String id) {
        Ticket ticket = new Ticket();
        
        String sql = "SELECT * FROM ticket WHERE ticket_id= " + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> ticketInfo = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> tableItem : ticketInfo) {
           /* ticket.setMessageProcessor(userService.getUserById(String.valueOf(tableItem.get("message_processor_id"))));
            ticket.setCreatedBy(userService.getUserById(String.valueOf(tableItem.get("created_by_id"))));
            ticket.setReportedBy(userService.getUserById(String.valueOf(tableItem.get("reported_by_id"))));
            */
            ticket.setApplicationControl(applicationControlService.getApplicationControlById(
                    String.valueOf(tableItem.get("application_control_id"))));
            
        }
       // ticket.setTicketId(String.valueOf(ticketList.get(0).get("ticket_id")));
       // ticket.setShortDescription((String) ticketList.get(0).get("short_description"));
 
        return ticket;
    }

}
