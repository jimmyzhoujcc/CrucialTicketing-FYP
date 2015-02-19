/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.ChangeLog;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.daos.TicketDao;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author DanFoley
 */
public class TicketService extends JdbcDaoSupport implements TicketDao {

    @Autowired
    UserService userService;

    @Autowired
    ApplicationControlService applicationControlService;

    @Autowired
    TicketLogService ticketLogService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    ChangeLogService changeLogService;

    @Override
    public int insertTicket(final String shortDescription, final int applicationControlId,
            final int createdByUserId, final int reportedByUserId, final int currentStatusId) {
        final String sql = "INSERT INTO ticket ("
                + "short_description, "
                + "application_control_id, "
                + "created_by_id, "
                + "reported_by_id, "
                + "current_status_id) VALUES (?, ?, ?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, shortDescription);
                ps.setInt(2, applicationControlId);
                ps.setInt(3, createdByUserId);
                ps.setInt(4, reportedByUserId);
                ps.setInt(5, currentStatusId);
                return ps;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public Ticket getTicketById(int ticketId, boolean popWorkflowMap,
            boolean popTicketLog, boolean popAttachments, boolean popChangeLog) {
        String sql = "SELECT * FROM ticket WHERE ticket.ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});
        if (rs.size() != 1) {
            return new Ticket();
        }
        return (this.rowMapper(rs, popWorkflowMap, popTicketLog, popAttachments, popChangeLog)).get(0);
    }

    @Override
    public List<Ticket> getTicketList(boolean popWorkflowMap,
            boolean popTicketLog, boolean popAttachments, boolean popChangeLog) {
        String sql = "SELECT * FROM ticket WHERE ticket.ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        return this.rowMapper(rs, popWorkflowMap, popTicketLog, popAttachments, popChangeLog);
    }

    @Override
    public void updateDescription(int ticketId, String newDescription) {
        String sql = "UPDATE ticket SET short_description=? WHERE ticket_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{newDescription, ticketId});
    }

    @Override
    public void updateStatus(int ticketId, int newStatusId) {
        String sql = "UPDATE ticket SET current_status_id=? WHERE ticket_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{newStatusId, ticketId});
    }

    @Override
    public void updateApplicationControl(int ticketId, int applicationControlId) {
        String sql = "UPDATE ticket SET application_control_id=? WHERE ticket_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{applicationControlId, ticketId});
    }

    @Override
    public List<Ticket> rowMapper(List<Map<String, Object>> resultSet, boolean popWorkflowMap, boolean popTicketLog, boolean popAttachments, boolean popChangeLog) {
        List<Ticket> ticketList = new ArrayList<>();

        for (Map row : resultSet) {
            Ticket ticket = new Ticket();

            ticket.setTicketId((int) row.get("ticket_id"));
            ticket.setShortDescription((String) row.get("short_description"));

            ticket.setReportedBy(userService.getUserById((int) row.get("reported_by_id"), false));
            ticket.setCreatedBy(userService.getUserById((int) row.get("created_by_id"), false));

            ticket.setApplicationControl(applicationControlService.getApplicationControlById((int) row.get("application_control_id"), popWorkflowMap));

            ticket.setCurrentWorkflowStep(ticket.getApplicationControl().getWorkflow().getWorkflowMap().getWorkflowStageByStatus(((int) row.get("current_status_id"))));

            if (popTicketLog) {
                ticket.setTicketLog(ticketLogService.getTicketLogByTicketId((int) row.get("ticket_id")));
            }

            if (popAttachments) {
                ticket.setAttachmentList(attachmentService.getAttachmentListByTicketId((int) row.get("ticket_id")));
            }

            if (popChangeLog) {
                ChangeLog changeLog = changeLogService.getChangeLogByTicketId((int) row.get("ticket_id"));
                changeLog.setTimeElapsed();
                ticket.setChangeLog(changeLog);
            }

            ticketList.add(ticket);
        }
        return ticketList;
    }
}
