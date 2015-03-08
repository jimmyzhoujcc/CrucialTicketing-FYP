/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserQueueConChangeLogDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.UserQueueConChangeLog;
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
public class UserQueueConChangeLogService extends JdbcDaoSupport implements UserQueueConChangeLogDao {
    
    @Autowired
    UserService userService;

    @Autowired
    QueueService queueService;

    @Autowired
    TicketService ticketService;

    @Override
    public void insertChangeLog(UserQueueConChangeLog changeLog) {
        String sql = "INSERT user_queue_con_change_log "
                + "(user_id, queue_id, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            changeLog.getUserQueueCon().getUser().getUserId(),
            changeLog.getUserQueueCon().getQueue().getQueueId(),
            changeLog.getTicket().getTicketId(),
            changeLog.getRequestor().getUserId(),
            getTimestamp(),
            changeLog.getUserQueueCon().getActiveFlag().getActiveFlag()
        });
    }

    @Override
    public List<UserQueueConChangeLog> getChangeLogByUserId(int userId) {
        String sql = "SELECT * FROM user_queue_con_change_log WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<UserQueueConChangeLog> getChangeLogByQueueId(int queueId) {
        String sql = "SELECT * FROM user_queue_con_change_log WHERE queue_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{queueId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<UserQueueConChangeLog> getChangeLogByTicketId(int ticketId) {
        String sql = "SELECT * FROM user_queue_con_change_log WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<UserQueueConChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserQueueConChangeLog> changeLogList = new ArrayList<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();
        Map<Integer, Queue> retrievedQueueList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();

        for (Map row : resultSet) {
            UserQueueConChangeLog changeLog = new UserQueueConChangeLog();

            changeLog.setUserQueueConChangeLogId((int) row.get("user_queue_con_change_log_id"));

            // User Queue Con retrieval via Queue and User
            // --- getting user
            User user;
            if (retrievedUserList.containsKey((int) row.get("user_id"))) {
                user = retrievedUserList.get((int) row.get("user_id"));
            } else {
                user = userService.getUserById((int) row.get("user_id"), false);
                retrievedUserList.put((int) row.get("user_id"), user);
            }

            // --- getting Queue
            Queue queue;
            if (retrievedQueueList.containsKey((int) row.get("queue_id"))) {
                queue = retrievedQueueList.get((int) row.get("queue_id"));
            } else {
                queue = queueService.getQueueById((int) row.get("queue_id"));
                retrievedQueueList.put((int) row.get("queue_id"), queue);
            }

            changeLog.setUserQueueCon(
                    new UserQueueCon(
                            user, queue,
                            ActiveFlag.values()[((int) row.get("active_flag")) + 2]
                    ));

            // Ticket checks
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                changeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false);
                changeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }

            // Requestor (user) list
            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                changeLog.setRequestor(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                user = userService.getUserById((int) row.get("requestor_user_id"), false);
                changeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }

            changeLog.setStamp((int) row.get("stamp"));

            changeLogList.add(changeLog);
        }
        return changeLogList;
    }

}
