/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.ChangeLog;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.daos.TicketDao;
import com.crucialticketing.entities.ChangeLogEntry;
import com.crucialticketing.entities.Role;
import com.crucialticketing.util.QueryGenerator;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
    TicketChangeLogService changeLogService;

    @Override
    public int insertTicket(final Ticket ticket) {
        final String sql = "INSERT INTO ticket ("
                + "short_description, "
                + "reported_by_id) VALUES (?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, ticket.getShortDescription());
                ps.setInt(2, ticket.getReportedBy().getUserId());
                return ps;
            }
        }, holder);

        int insertedId = holder.getKey().intValue();
        ticket.setTicketId(insertedId);

        // Adds change log entry
        changeLogService.addChangeLogEntry(
                ticket.getTicketId(),
                ticket.getApplicationControl().getApplicationControlId(),
                ticket.getApplicationControl().getWorkflow().getWorkflowMap()
                .getWorkflow().get(0).getWorkflowStatus().getWorkflowStatusId(),
                ticket.getLastProcessedBy().getUserId());

        if (ticket.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusId()
                != ticket.getApplicationControl().getWorkflow().getWorkflowMap()
                .getWorkflow().get(0).getWorkflowStatus().getWorkflowStatusId()) {

            changeLogService.addChangeLogEntry(
                    ticket.getTicketId(),
                    ticket.getApplicationControl().getApplicationControlId(),
                    ticket.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusId(),
                    ticket.getLastProcessedBy().getUserId());
        }

        return insertedId;
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
    public boolean doesTicketExist(int ticketId) {
        String sql = "SELECT COUNT(ticket_id) AS result FROM ticket "
                + "WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<Ticket> getListByCriteria(ArrayList<String> ticketArrayList,
            ArrayList<String> applicationArrayList,
            ArrayList<String> severityArrayList,
            ArrayList<String> workflowArrayList,
            ArrayList<String> workflowStatusArrayList,
            ArrayList<String> reportedByUserArrayList,
            ArrayList<String> createdByUserArrayList,
            ArrayList<String> lastUpdatedByUserArrayList,
            String dateCreatedFrom, String dateCreatedTo,
            String dateLastUpdatedFrom, String dateLastUpdatedTo) {
        String sql = "SELECT * FROM reporting_view WHERE ";

        sql = QueryGenerator.amendQuery(sql, ticketArrayList, "ticket_id", false);
        sql = QueryGenerator.amendQuery(sql, applicationArrayList, "application_id", true);
        sql = QueryGenerator.amendQuery(sql, severityArrayList, "severity_id", true);
        sql = QueryGenerator.amendQuery(sql, workflowArrayList, "workflow_id", true);
        sql = QueryGenerator.amendQuery(sql, workflowStatusArrayList, "workflow_status_id", true);
        sql = QueryGenerator.amendQuery(sql, reportedByUserArrayList, "reported_by_id", true);

        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Ticket> ticketList = new ArrayList<>();
        
        for (Map<String, Object> r : rs) {
            ticketList.add(this.getTicketById((int)r.get("ticket_id"), true, true, true, true));
        }
        return ticketList;
    }

    private List<Ticket> rowMapper(List<Map<String, Object>> resultSet, boolean popWorkflowMap, boolean popTicketLog, boolean popAttachments, boolean popSLA) {
        List<Ticket> ticketList = new ArrayList<>();

        for (Map row : resultSet) {
            Ticket ticket = new Ticket();

            ticket.setTicketId((int) row.get("ticket_id"));
            ticket.setShortDescription((String) row.get("short_description"));

            ticket.setReportedBy(userService.getUserById((int) row.get("reported_by_id"), false));

            if (popTicketLog) {
                ticket.setTicketLog(ticketLogService.getTicketLogByTicketId((int) row.get("ticket_id")));
            }

            if (popAttachments) {
                ticket.setAttachmentList(attachmentService.getAttachmentListByTicketId((int) row.get("ticket_id")));
            }

            ChangeLog changeLog = changeLogService.getChangeLogByTicketId((int) row.get("ticket_id"));
            ticket.setChangeLog(changeLog);

            ChangeLogEntry firstEntry = changeLog.getChangeLog().get(0);
            ChangeLogEntry lastEntry = changeLog.getChangeLog().get(changeLog.getChangeLog().size() - 1);

            ticket.setApplicationControl(firstEntry.getApplicationControl());

            ticket.setCurrentWorkflowStep(lastEntry.getApplicationControl().getWorkflow().getWorkflowMap().getWorkflowStageByStatus(lastEntry.getWorkflowStatus().getWorkflowStatusId()));

            if (popSLA) {
                changeLog.setTimeElapsed();
            }

            ticketList.add(ticket);
        }
        return ticketList;
    }
}
