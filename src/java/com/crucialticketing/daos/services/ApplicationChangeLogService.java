/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.ApplicationChangeLogDao;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationChangeLog;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.entities.User;
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
public class ApplicationChangeLogService extends JdbcDaoSupport implements ApplicationChangeLogDao {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Override
    public void insertApplicationChangeLog(ApplicationChangeLog applicationChangeLog) {
        String sql = "INSERT application_change_log "
                + "(application_id, application_name, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            applicationChangeLog.getApplication().getApplicationId(),
            applicationChangeLog.getApplication().getApplicationName(),
            applicationChangeLog.getTicket().getTicketId(),
            applicationChangeLog.getRequestor().getUserId(),
            getTimestamp(),
            applicationChangeLog.getApplication().getActiveFlag().getActiveFlag()
        });
    }

    @Override
    public List<ApplicationChangeLog> getApplicationChangeLogByApplicationId(int applicationId) {
        String sql = "SELECT * FROM application_change_log WHERE "
                + "application_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql,
                new Object[]{applicationId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<ApplicationChangeLog> getApplicationChangeLogByTicketId(int ticketId) {
        String sql = "SELECT * FROM application_change_log WHERE "
                + "ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql,
                new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<ApplicationChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<ApplicationChangeLog> applicationChangeLogList = new ArrayList<>();
        Map<Integer, Application> retrievedApplicationList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();

        for (Map row : resultSet) {
            ApplicationChangeLog applicationChangeLog = new ApplicationChangeLog();

            applicationChangeLog.setApplicationChangeLogId((int) row.get("application_change_log_id"));

            // Application getter 
            if (retrievedApplicationList.containsKey((int) row.get("application_id"))) {
                applicationChangeLog.setApplication(retrievedApplicationList.get((int) row.get("application_id")));
            } else {
                Application application = applicationService.getApplicationById((int) row.get("application_id"));
                applicationChangeLog.setApplication(application);
                retrievedApplicationList.put((int) row.get("application_id"), application);
            }

            // Ticket getter
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                applicationChangeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false, false);
                applicationChangeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }

            // Requestor (user) getter
            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                applicationChangeLog.setRequestor(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                applicationChangeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }

            applicationChangeLog.setStamp((int) row.get("stamp"));

            applicationChangeLog.getApplication().setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]); // Offset 2 to get the correct array

            applicationChangeLogList.add(applicationChangeLog);
        }
        return applicationChangeLogList;
    }

}
