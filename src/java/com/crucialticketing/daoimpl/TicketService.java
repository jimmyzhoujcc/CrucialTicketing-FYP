/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.entities.TicketChangeLog;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.daos.TicketDao;
import com.crucialticketing.entities.TicketChangeLogEntry;
import com.crucialticketing.util.QueryGenerator;
import com.crucialticketing.util.Validation;
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
    TicketChangeLogService changeLogService;
    
    @Autowired
    TicketLinkService ticketLinkService;

    @Override
    public int insertTicket(final Ticket ticket) {
        final String sql = "INSERT INTO ticket ("
                + "short_description, "
                + "reported_by_user_id) VALUES (?, ?)";

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
                ticket.getLastUpdatedBy().getUserId());

        if (ticket.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusId()
                != ticket.getApplicationControl().getWorkflow().getWorkflowMap()
                .getWorkflow().get(0).getWorkflowStatus().getWorkflowStatusId()) {

            changeLogService.addChangeLogEntry(
                    ticket.getTicketId(),
                    ticket.getApplicationControl().getApplicationControlId(),
                    ticket.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusId(),
                    ticket.getLastUpdatedBy().getUserId());
        }

        return insertedId;
    }

    @Override
    public Ticket getTicketById(int ticketId, boolean popTicketLog, 
            boolean popAttachments, boolean popTicketLinks, 
            boolean populateAllChangeLog, boolean popSLA) {
        String sql = "SELECT * FROM ticket WHERE ticket.ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});
        if (rs.size() != 1) {
            return new Ticket();
        }
        return (this.rowMapper(rs, popTicketLog, popAttachments, popTicketLinks, populateAllChangeLog, popSLA)).get(0);
    }

    @Override
    public List<Ticket> getListByCriteria(
            ArrayList<String> ticketList,
            ArrayList<String> ticketTypeList,
            ArrayList<String> applicationList,
            ArrayList<String> severityList,
            ArrayList<String> workflowList,
            ArrayList<String> workflowStatusList,
            ArrayList<String> queueList,
            ArrayList<String> reportedByUserList,
            ArrayList<String> createdByUserList,
            ArrayList<String> lastUpdatedByUserList,
            String dateCreatedFrom, String dateCreatedTo,
            String dateLastUpdatedFrom, String dateLastUpdatedTo) {

        List<Ticket> retrievedTicketList = new ArrayList<>();

        String sql = "SELECT * FROM ticket_complete_earliest_view WHERE 1=1 ";

        sql = QueryGenerator.amendQuery(sql, ticketList, "ticket_complete_earliest_view.ticket_id", true);
        sql = QueryGenerator.amendQuery(sql, ticketTypeList, "ticket_complete_earliest_view.ticket_type_id", true);
        sql = QueryGenerator.amendQuery(sql, applicationList, "ticket_complete_earliest_view.application_id", true);
        sql = QueryGenerator.amendQuery(sql, severityList, "ticket_complete_earliest_view.severity_id", true);
        sql = QueryGenerator.amendQuery(sql, workflowList, "ticket_complete_earliest_view.workflow_id", true);
        sql = QueryGenerator.amendQuery(sql, workflowStatusList, "ticket_complete_earliest_view.workflow_status_id", true);
        sql = QueryGenerator.amendQuery(sql, queueList, "ticket_complete_earliest_view.queue_id", true);

        // Reported by 
        sql = QueryGenerator.amendQuery(sql, reportedByUserList, "ticket_complete_earliest_view.reported_by_id", true);

        if (Validation.toInteger(dateCreatedFrom) != 0) {
            if (Validation.toInteger(dateCreatedTo) == 0) {
                dateCreatedTo = dateCreatedFrom;
            }

            sql = QueryGenerator.amendQueryRange(sql, "ticket_complete_earliest_view.stamp", dateCreatedFrom, dateCreatedTo, true);
        }

        // Created  *****************
        String createdSql = sql;

        createdSql = QueryGenerator.amendQuery(createdSql, createdByUserList, "ticket_complete_earliest_view.requestor_user_id", true);

        if (Validation.toInteger(dateCreatedFrom) != 0) {
            if (Validation.toInteger(dateCreatedTo) == 0) {
                dateCreatedTo = dateCreatedFrom;
            }

            createdSql = QueryGenerator.amendQueryRange(createdSql, "ticket_complete_earliest_view.stamp", dateCreatedFrom, dateCreatedTo, true);
        }

        // Last Updated
        String lastUpdatedSql = sql;

        lastUpdatedSql = QueryGenerator.amendQuery(lastUpdatedSql, lastUpdatedByUserList, "ticket_complete_latest_view.requestor_user_id", true);

        if (Validation.toInteger(dateLastUpdatedFrom) != 0) {
            if (Validation.toInteger(dateLastUpdatedTo) == 0) {
                dateLastUpdatedTo = dateLastUpdatedFrom;
            }

            lastUpdatedSql = QueryGenerator.amendQueryRange(lastUpdatedSql, "ticket_complete_latest_view.stamp", dateLastUpdatedFrom, dateLastUpdatedTo, true);
        }

        lastUpdatedSql = lastUpdatedSql.replaceAll("ticket_complete_earliest_view", "ticket_complete_latest_view");

        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(createdSql + " UNION ALL " + lastUpdatedSql + " ORDER BY ticket_id ASC");
        List<Integer> checkList = new ArrayList<>();
        ArrayList<String> searchList = new ArrayList<>();

        for (Map<String, Object> r : rs) {
            if (!checkList.contains((int) r.get("ticket_id"))) {
                checkList.add((int) r.get("ticket_id"));
            } else {
                if (!searchList.contains(String.valueOf((int) r.get("ticket_id")))) {
                    searchList.add(String.valueOf((int) r.get("ticket_id")));
                }
            }
        }

        return this.getTicketListByTicketIdList(searchList);
    }

    private List<Ticket> getTicketListByTicketIdList(ArrayList<String> ticketIdList) {
        String sql = "SELECT * FROM ticket WHERE ";

        sql = QueryGenerator.amendQuery(sql, ticketIdList, "ticket_id", false);

        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);

        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs, false, false, false, false, true);
    }

    @Override
    public void updateDescription(int ticketId, String newDescription) {
        String sql = "UPDATE ticket SET short_description=? WHERE ticket_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{newDescription, ticketId});
    }

    @Override
    public boolean doesTicketExist(int ticketId) {
        String sql = "SELECT COUNT(ticket_id) AS result FROM ticket "
                + "WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    private List<Ticket> rowMapper(List<Map<String, Object>> resultSet, boolean popTicketLog, boolean popAttachments, boolean popTicketLinks, boolean populateAllChangeLog, boolean popSLA) {
        List<Ticket> ticketList = new ArrayList<>();

        for (Map row : resultSet) {
            Ticket ticket = new Ticket();

            ticket.setTicketId((int) row.get("ticket_id"));
            ticket.setShortDescription((String) row.get("short_description"));
            ticket.setReportedBy(userService.getUserById((int) row.get("reported_by_user_id"), false));

            if (popTicketLog) {
                ticket.setTicketLog(ticketLogService.getTicketLogByTicketId((int) row.get("ticket_id")));
            }

            if (popAttachments) {
                ticket.setAttachmentList(attachmentService.getAttachmentListByTicketId((int) row.get("ticket_id")));
            }

            if (popTicketLinks) {
                ticket.setTicketLinkList(ticketLinkService.getTicketLink((int) row.get("ticket_id")));
            }

            TicketChangeLog changeLog = changeLogService.getChangeLogByTicketId((int) row.get("ticket_id"), populateAllChangeLog);
            ticket.setChangeLog(changeLog);

            // Self generated
            ticket.setCreatedBy(ticket.getChangeLog().getChangeLog().get(0).getUser());
            ticket.setLastUpdatedBy(ticket.getChangeLog().getChangeLog().get(ticket.getChangeLog().getChangeLog().size() - 1).getUser());

            TicketChangeLogEntry lastEntry = changeLog.getChangeLog().get(changeLog.getChangeLog().size() - 1);

            ticket.setApplicationControl(lastEntry.getApplicationControl());

            ticket.setCurrentWorkflowStep(lastEntry.getApplicationControl()
                    .getWorkflow()
                    .getWorkflowMap()
                    .getWorkflowStageByStatus(lastEntry.getWorkflowStatus().getWorkflowStatusId()));

            if (popSLA) {
                changeLog.setTimeElapsed();
            }

            ticketList.add(ticket);
        }
        return ticketList;
    }
}
