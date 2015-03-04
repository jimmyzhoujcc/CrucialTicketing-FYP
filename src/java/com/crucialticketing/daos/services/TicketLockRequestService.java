/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.TicketLockRequest;
import com.crucialticketing.daos.TicketLockRequestDao;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class TicketLockRequestService extends JdbcDaoSupport implements TicketLockRequestDao {

    @Autowired
    UserAlertService userAlertService;
    
    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing

    @Override
    public void addTicketLockRequest(int ticketId, int userId) {
        this.getJdbcTemplate().update("INSERT INTO ticket_lock_request "
                + "(ticket_id, user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{ticketId, userId, getTimestamp(), 0});
    }

    @Override
    public boolean ticketOpenForEditByUser(int ticketId, int userId) {
        String sql = "SELECT * FROM ticket_lock_request WHERE "
                + "ticket_id=? AND user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();

        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId, userId});
        return !rs.isEmpty();
    }

    @Override
    public void grantAccess(int ticketId, int userId) {
        this.getJdbcTemplate().update("UPDATE ticket_lock_request SET request_pass_time=? WHERE ticket_id=? AND user_id=?", new Object[]{getTimestamp(), ticketId, userId});
         userAlertService.insertUserAlert(userId, "(" + ticketId + ") Access granted for edit");
    }

    @Override
    public void denyAccess(int ticketId, int userId) {
        this.getJdbcTemplate().update("UPDATE ticket_lock_request SET request_pass_time=? WHERE ticket_id=? AND user_id=?", new Object[]{-1, ticketId, userId});
        userAlertService.insertUserAlert(userId, "(" + ticketId + ") Access denied for edit (in use)");
    }

    @Override
    public List<TicketLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM ticket_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<TicketLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<TicketLockRequest> ticketLockRequestList = new ArrayList<>();

        for (Map row : resultSet) {
            TicketLockRequest ticketLockRequest = new TicketLockRequest();

            ticketLockRequest.setLockId((int) row.get("lock_id"));
            ticketLockRequest.setTicketId((int) row.get("ticket_id"));
            ticketLockRequest.setUserId((int) row.get("user_id"));
            ticketLockRequest.setRequestTime((int) row.get("request_time"));
            ticketLockRequest.setRequestPassTime((int) row.get("request_pass_time"));

            ticketLockRequestList.add(ticketLockRequest);
        }
        return ticketLockRequestList;
    }
}
