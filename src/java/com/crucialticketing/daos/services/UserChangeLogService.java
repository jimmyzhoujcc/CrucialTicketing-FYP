/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserChangeLogDao;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserChangeLog;
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
public class UserChangeLogService extends JdbcDaoSupport implements UserChangeLogDao {

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Override
    public void insertUserChangeLog(UserChangeLog userChangeLog) {
        String sql = "INSERT user_change_log "
                + "(user_id, hash, email_address, contact, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            userChangeLog.getUser().getUserId(),
            userChangeLog.getUser().getSecure().getHash(),
            userChangeLog.getUser().getEmailAddress(),
            userChangeLog.getUser().getContact(),
            userChangeLog.getTicket().getTicketId(),
            userChangeLog.getRequestor().getUserId(),
            getTimestamp(),
            userChangeLog.getUser().getActiveFlag().getActiveFlag()
        });
    }

    @Override
    public List<UserChangeLog> getUserChangeLogList() {
        String sql = "SELECT * FROM user_change_log";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<UserChangeLog> getUserChangeLogListByUserId(int userId) {
        String sql = "SELECT * FROM user_change_log WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public void removeUserChangeLogEntryByUserId(int userId) {
        String sql = "DELETE FROM user_change_log WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userId});
    }

    @Override
    public List<UserChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserChangeLog> userChangeLogList = new ArrayList<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();

        for (Map row : resultSet) {
            UserChangeLog userChangeLog = new UserChangeLog();

            userChangeLog.setUserChangeLogId((int) row.get("user_change_log_id"));

            // User List
            if (retrievedUserList.containsKey((int) row.get("user_id"))) {
                userChangeLog.setUser(retrievedUserList.get((int) row.get("user_id")));
            } else {
                User user = userService.getUserById((int) row.get("user_id"), false);
                userChangeLog.setUser(user);
                retrievedUserList.put((int) row.get("user_id"), user);
            }

            // Ticket checks
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                userChangeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false, false);
                userChangeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }

            userChangeLog.getUser().getSecure().setHash((String) row.get("hash"));
            userChangeLog.getUser().setEmailAddress((String) row.get("email_address"));
            userChangeLog.getUser().setContact((String) row.get("contact"));

            // Requestor user List
            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                userChangeLog.setUser(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                userChangeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }

            userChangeLog.getUser().setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]);

            userChangeLog.setStamp((int) row.get("stamp"));

            userChangeLogList.add(userChangeLog);
        }
        return userChangeLogList;
    }
}
