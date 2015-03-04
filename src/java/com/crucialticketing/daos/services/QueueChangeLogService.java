/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.QueueChangeLogDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.QueueChangeLog;
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
public class QueueChangeLogService extends JdbcDaoSupport implements QueueChangeLogDao {

    @Autowired
    UserService userService;

    @Autowired
    QueueService queueService;
    
    @Autowired
    TicketService ticketService;

    @Override
    public void insertQueueChangeLog(QueueChangeLog queueChangeLog) {
        String sql = "INSERT queue_change_log "
                + "(queue_id, queue_name, active_flag, ticket_id, requestor_user_id, stamp) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            queueChangeLog.getQueue().getQueueId(), 
            queueChangeLog.getQueue().getQueueName(),
            ActiveFlag.UNPROCESSED.getActiveFlag(), 
            queueChangeLog.getTicket().getTicketId(), 
            queueChangeLog.getRequestor().getUserId(),
            getTimestamp()
        });
    }

    @Override
    public List<QueueChangeLog> getQueueChangeLogList() {
        String sql = "SELECT * FROM queue_change_log";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<QueueChangeLog> getQueueChangeLogListByQueueId(Queue queue) {
        String sql = "SELECT * FROM queue_change_log WHERE queue_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queue.getQueueId()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }
    
    @Override
    public List<QueueChangeLog> getQueueChangeLogListByTicketId(Ticket ticket) {
        String sql = "SELECT * FROM queue_change_log WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticket.getTicketId()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }
    
    @Override
    public void removeQueueChangeLogEntry(QueueChangeLog queueChangeLog) {
        String sql = "DELETE FROM queue_change_log WHERE queue_change_log_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queueChangeLog.getQueueChangeLogId()});
    }
    
    @Override
    public void removeAllQueueChangeLogEntries(Queue queue) {
        String sql = "DELETE FROM queue_change_log WHERE queue_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queue.getQueueId()});
    }

    @Override
    public List<QueueChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<QueueChangeLog> queueChangeLogList = new ArrayList<>();
        Map<Integer, Queue> retrievedQueueList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();

        for (Map row : resultSet) {
            QueueChangeLog queueChangeLog = new QueueChangeLog();

            queueChangeLog.setQueueChangeLogId((int) row.get("queue_change_log_id"));

            if (retrievedQueueList.containsKey((int) row.get("queue_id"))) {
                queueChangeLog.setQueue(retrievedQueueList.get((int) row.get("queue_id")));
            } else {
                Queue queue = queueService.getQueueById((int) row.get("queue_id"));
                queueChangeLog.setQueue(queue);
                retrievedQueueList.put((int) row.get("queue_id"), queue);
            }
            
            // Ticket checks
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                queueChangeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false);
                queueChangeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }

            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                queueChangeLog.setRequestor(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                queueChangeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }

            queueChangeLog.setStamp((int) row.get("stamp"));

            queueChangeLogList.add(queueChangeLog);
        }
        return queueChangeLogList;
    }

}
