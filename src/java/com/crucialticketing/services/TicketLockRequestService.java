/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketLockRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class TicketLockRequestService implements TicketLockRequestDao {

    private int buffer = (20*60) + 30; // 20*60 for 20 minutes of editing - buffer of 30 seconds
    JdbcTemplate jdbcTemplate;
    String insert = "INSERT INTO ticket_lock_request (ticket_id, user_id, request_time, request_pass_time) "
            + "VALUES (?, ?, ?, ?)";
    String select = "SELECT * FROM ticket_lock_request WHERE ticket_id=? AND user_id=?";
    String selectList = "SELECT * FROM ticket_lock_request WHERE request_pass_time=?";
    String update = "UPDATE ticket_lock_request SET request_pass_time=? WHERE ticket_id=? AND user_id=?";

    @Override
    public void addTicketLockRequest(int ticketId, int userId) {
        int unixTime = (int) (System.currentTimeMillis() / 1000);
        jdbcTemplate.update(insert, new Object[]{ticketId, userId, unixTime, 0});
    }

    @Override
    public boolean ticketOpenForEditByUser(int ticketId, int userId) {
        int unixTime = (int) (System.currentTimeMillis() / 1000);
        String sql = "SELECT * FROM ticket_lock_request WHERE ticket_id=? AND user_id=? AND request_pass_time+" + buffer + " > " + unixTime;

        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{ticketId, userId});
        if (rs.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void grantAccess(int ticketId, int userId) {
        UserAlertService userAlertService = new UserAlertService();
        userAlertService.setCon(jdbcTemplate);
        userAlertService.insertUserAlert(userId, ticketId, "(" + ticketId + ") Access granted for edit");

        int unixTime = (int) (System.currentTimeMillis() / 1000);

        jdbcTemplate.update(update, new Object[]{unixTime, ticketId, userId});

    }

    @Override
    public void denyAccess(int ticketId, int userId) {
        int unixTime = (int) (System.currentTimeMillis() / 1000);
        jdbcTemplate.update(update, new Object[]{-1, ticketId, userId});

        UserAlertService userAlertService = new UserAlertService();
        userAlertService.setCon(jdbcTemplate);
        userAlertService.insertUserAlert(userId, ticketId, "(" + ticketId + ") Access denied for edit (in use)");
    }

    @Override
    public List<TicketLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(selectList, new Object[]{0});
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

    @Override
    public void setCon(JdbcTemplate con) {
        this.jdbcTemplate = con;
    }

}
