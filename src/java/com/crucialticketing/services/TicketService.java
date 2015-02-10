/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class TicketService implements TicketDao {

    private String selectByTicketId = "SELECT * FROM ticket WHERE ticket.ticket_id=?";

    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public Ticket getTicketById(int ticketId, boolean populateInternalData) {
        String sql = selectByTicketId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{ticketId});
        if (rs.size() != 1) {
            return new Ticket();
        }
        return (this.rowMapper(rs, populateInternalData)).get(0);
    }

    @Override
    public List<Ticket> getTicketList(boolean populateInternalData) {
        String sql = selectByTicketId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql);
        return this.rowMapper(rs, populateInternalData);
    }

    @Override
    public void updateDescription(int ticketId, String newDescription) {
        String sql = "UPDATE ticket SET short_description=? WHERE ticket_id=?";
        jdbcTemplate.update(sql, new Object[]{newDescription, ticketId});
    }

    @Override
    public void updateStatus(int ticketId, int newStatusId) {
        String sql = "UPDATE ticket SET current_status_id=? WHERE ticket_id=?";
        jdbcTemplate.update(sql, new Object[]{newStatusId, ticketId});
    }

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }

    @Override
    public List<Ticket> rowMapper(List<Map<String, Object>> resultSet, boolean populateInternalData) {
        List<Ticket> ticketList = new ArrayList<>();

        UserService userService = new UserService();
        userService.setCon(jdbcTemplate);

        ApplicationControlService applicationControlService = new ApplicationControlService();
        applicationControlService.setCon(jdbcTemplate);

        TicketLogService ticketLogService = new TicketLogService();
        ticketLogService.setCon(jdbcTemplate);

        for (Map row : resultSet) {
            Ticket ticket = new Ticket();

            ticket.setTicketId((int)row.get("ticket_id"));
            ticket.setShortDescription((String) row.get("short_description"));

            ticket.setMessageProcessor(userService.getUserById((int) row.get("message_processor_id"), false));
            ticket.setReportedBy(userService.getUserById((int) row.get("reported_by_id"), false));
            ticket.setCreatedBy(userService.getUserById((int) row.get("created_by_id"), false));

            ticket.setApplicationControl(applicationControlService.getApplicationControlById((int) row.get("application_control_id"), populateInternalData));

            ticket.setCurrentWorkflowStep(ticket.getApplicationControl().getWorkflow().getWorkflowMap().getWorkflowStageByStatus(((int) row.get("current_status_id"))));

            if (populateInternalData) {
                ticket.setTicketLog(ticketLogService.getTicketLogByTicketId((int) row.get("ticket_id")));
            }

            ticketList.add(ticket);
        }
        return ticketList;
    }
}
