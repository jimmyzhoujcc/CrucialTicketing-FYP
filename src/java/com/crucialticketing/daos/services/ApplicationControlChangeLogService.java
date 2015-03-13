/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.ApplicationControlChangeLogDao;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.ApplicationControlChangeLog;
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
public class ApplicationControlChangeLogService extends JdbcDaoSupport implements ApplicationControlChangeLogDao {
    
    @Autowired
    ApplicationControlService applicationControlService;
    
    @Autowired
    TicketService ticketService;
    
    @Autowired
    UserService userService;
    
    @Override
    public void insertChangeLog(ApplicationControlChangeLog applicationControlChangeLog) {
        String sql = "INSERT INTO application_control_change_log "
                + "(application_control_id, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{ 
            applicationControlChangeLog.getApplicationControl().getApplicationControlId(), 
            applicationControlChangeLog.getTicket().getTicketId(), 
            applicationControlChangeLog.getRequestor().getUserId(), 
            getTimestamp(), 
            applicationControlChangeLog.getApplicationControl().getActiveFlag().getActiveFlag()
        });
    }

    @Override
    public List<ApplicationControlChangeLog> getChangeLogListByApplicationControlId(int applicationControlId) {
        String sql = "SELECT * FROM application_control_change_log WHERE application_control_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql,
                new Object[]{applicationControlId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<ApplicationControlChangeLog> getChangeLogListByTicketId(int ticketId) {
        String sql = "SELECT * FROM application_control_change_log WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql,
                new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<ApplicationControlChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<ApplicationControlChangeLog> changeLogList = new ArrayList<>();
        Map<Integer, ApplicationControl> retrievedApplicationControlList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();

        for (Map row : resultSet) {
            ApplicationControlChangeLog applicationControlChangeLog = new ApplicationControlChangeLog();

            // Application Control checks
            if (retrievedApplicationControlList.containsKey((int) row.get("application_control_id"))) {
                applicationControlChangeLog.setApplicationControl(retrievedApplicationControlList.get((int) row.get("application_control_id")));
            } else {
                ApplicationControl applicationControl = 
                        applicationControlService.getApplicationControlById((int) row.get("application_control_id"), false);
                applicationControlChangeLog.setApplicationControl(applicationControl);
                retrievedApplicationControlList.put((int) row.get("application_control_id"), applicationControl);
            }
            
            // Ticket checks
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                applicationControlChangeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false);
                applicationControlChangeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }
            
            // User Checks
            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                applicationControlChangeLog.setRequestor(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                applicationControlChangeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }
         
            applicationControlChangeLog.setStamp((int) row.get("stamp"));
            
            applicationControlChangeLog.getApplicationControl().setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag"))+2]);
            
            changeLogList.add(applicationControlChangeLog);
        }
        return changeLogList;
    }
}
