/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.entities.TicketLockRequest;
import com.crucialticketing.daos.TicketLockRequestDao;
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
public class TicketLockRequestService extends JdbcDaoSupport implements TicketLockRequestDao {

    @Autowired
    TicketService ticketService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserAlertService userAlertService;

    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing
    
  @Override
    public void addLockRequest(TicketLockRequest lockRequest) {
        this.getJdbcTemplate().update("INSERT INTO ticket_lock_request "
                + "(ticket_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    lockRequest.getTicket().getTicketId(),
                    lockRequest.getRequestor().getUserId(),
                    getTimestamp(),
                    0});
    }

    @Override
    public boolean checkIfOpen(int ticketId, int requestorUserId) {
        String sql = "SELECT COUNT(ticket_lock_request_id) AS result FROM ticket_lock_request "
                + "WHERE ticket_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean checkIfOutstanding(int ticketId, int requestorUserId) {
        String sql = "SELECT COUNT(ticket_lock_request_id) AS result FROM ticket_lock_request "
                + "WHERE ticket_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int lockRequestId) {
        this.getJdbcTemplate().update("UPDATE ticket_lock_request SET request_pass_time=? WHERE ticket_lock_request_id=?", new Object[]{getTimestamp(), lockRequestId});
    }

    @Override
    public void denyAccess(int lockRequestId, Ticket ticket, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE ticket_lock_request SET request_pass_time=? WHERE ticket_lock_request_id=?", new Object[]{-1, lockRequestId});
        userAlertService.insertUserAlert(requestorUserId, "Access denied to access ticket (" + ticket.getTicketId() + ")");
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
    public void closeRequest(int ticketId, int requestorUserId) {
        String sql = "DELETE FROM ticket_lock_request WHERE ticket_id=? AND requestor_user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ticketId, requestorUserId});
    }
    
    private List<TicketLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<TicketLockRequest> lockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Ticket> ticketList = new HashMap<>();

        for (Map row : resultSet) {
            TicketLockRequest lockRequest = new TicketLockRequest();

            lockRequest.setLockRequestId((int) row.get("ticket_lock_request_id"));

            // ticket
            if (userList.containsKey((int) row.get("ticket_id"))) {
                lockRequest.setTicket(ticketList.get((int) row.get("ticket_id")));
            } else {
                lockRequest.setTicket(ticketService.getTicketById((int) row.get("ticket_id"), false, false, false, false, false));
                ticketList.put((int) row.get("ticket_id"), lockRequest.getTicket());
            }

            // User
            if (userList.containsKey((int) row.get("requestor_user_id"))) {
                lockRequest.setRequestor(userList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                lockRequest.setRequestor(user);
                userList.put((int) row.get("requestor_user_id"), user);
            }

            lockRequest.setRequestTime((int) row.get("request_time"));
            lockRequest.setRequestPassTime((int) row.get("request_pass_time"));

            lockRequestList.add(lockRequest);
        }
        return lockRequestList;
    }
}
