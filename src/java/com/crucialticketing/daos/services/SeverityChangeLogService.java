/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.SeverityChangeLogDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.SeverityChangeLog;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class SeverityChangeLogService extends JdbcDaoSupport implements SeverityChangeLogDao {

    @Autowired
    SeverityService severityService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Override
    public void insertSeverityChangeLog(SeverityChangeLog severityChangeLog) {
        String sql = "INSERT queue_change_log "
                + "(severity_id, severity_level, severity_name, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            severityChangeLog.getSeverity().getSeverityLevel(),
            severityChangeLog.getSeverity().getSeverityName(),
            severityChangeLog.getTicket().getTicketId(),
            severityChangeLog.getRequestor().getUserId(),
            getTimestamp(),
            ActiveFlag.UNPROCESSED.getActiveFlag()
        });
    }

    @Override
    public List<SeverityChangeLog> getChangeLogBySeverityId(int severityId) {
        String sql = "SELECT * FROM severity_change_log WHERE severity_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{severityId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<SeverityChangeLog> getChangeLogByTicketId(Ticket ticket) {
        String sql = "SELECT * FROM severity_change_log WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ticket.getTicketId()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<SeverityChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<SeverityChangeLog> severityChangeLogList = new ArrayList<>();
        Map<Integer, Severity> retrievedSeverityList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();

        for (Map row : resultSet) {
            SeverityChangeLog severityChangeLog = new SeverityChangeLog();

            severityChangeLog.setSeverityChangeLogId((int) row.get("severity_change_log_id"));

            // Severity
            if (retrievedSeverityList.containsKey((int) row.get("severity_id"))) {
                severityChangeLog.setSeverity(retrievedSeverityList.get((int) row.get("severity_id")));
            } else {
                Severity severity = severityService.getSeverityById((int) row.get("severity_id"));
                severityChangeLog.setSeverity(severity);
                retrievedSeverityList.put((int) row.get("severity_id"), severity);
            }

            // Ticket checks
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                severityChangeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false);
                severityChangeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }

            // Requestor (user) list
            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                severityChangeLog.setRequestor(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                severityChangeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }

            severityChangeLog.setSeverityLevel((int) row.get("severity_level"));
            severityChangeLog.setSeverityName((String) row.get("severity_name"));
            
            severityChangeLog.setStamp((int) row.get("stamp"));

            severityChangeLog.setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]);

            severityChangeLogList.add(severityChangeLog);
        }
        return severityChangeLogList;
    }

}
