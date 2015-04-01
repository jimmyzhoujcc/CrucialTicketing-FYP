/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.SeverityLockRequestDao;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.SeverityLockRequest;
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
public class SeverityLockRequestService extends JdbcDaoSupport implements SeverityLockRequestDao {

    @Autowired
    UserAlertService userAlertService;

    @Autowired
    UserService userService;

    @Autowired
    SeverityService severityService;

    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing

    @Override
    public void addLockRequest(SeverityLockRequest lockRequest) {
        this.getJdbcTemplate().update("INSERT INTO severity_lock_request "
                + "(severity_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    lockRequest.getSeverity().getSeverityId(),
                    lockRequest.getRequestor().getUserId(),
                    getTimestamp(),
                    0});
    }

    @Override
    public boolean checkIfOpen(int severityId, int requestorUserId) {
        String sql = "SELECT COUNT(severity_lock_request_id) AS result FROM severity_lock_request "
                + "WHERE severity_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{severityId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean checkIfOutstanding(int severityId, int requestorUserId) {
        String sql = "SELECT COUNT(severity_lock_request_id) AS result FROM severity_lock_request "
                + "WHERE severity_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{severityId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int lockRequestId) {
        this.getJdbcTemplate().update("UPDATE severity_lock_request SET request_pass_time=? WHERE severity_lock_request_id=?", new Object[]{getTimestamp(), lockRequestId});
    }

    @Override
    public void denyAccess(int lockRequestId, Severity severity, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE severity_lock_request SET request_pass_time=? WHERE severity_lock_request_id=?", new Object[]{-1, lockRequestId});
        userAlertService.insertUserAlert(requestorUserId, "Access denied to access role (" + severity.getSeverityLevel() + ":" + severity.getSeverityName() + ")");
    }

    @Override
    public List<SeverityLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM severity_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void closeRequest(int severityId, int requestorUserId) {
        String sql = "DELETE FROM severity_lock_request WHERE severity_id=? AND requestor_user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{severityId, requestorUserId});
    }

    @Override
    public List<SeverityLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<SeverityLockRequest> lockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Severity> severityList = new HashMap<>();

        for (Map row : resultSet) {
            SeverityLockRequest lockRequest = new SeverityLockRequest();

            lockRequest.setLockRequestId((int) row.get("severity_lock_request_id"));

            // Severity
            if (userList.containsKey((int) row.get("severity_id"))) {
                lockRequest.setSeverity(severityList.get((int) row.get("severity_id")));
            } else {
                lockRequest.setSeverity(severityService.getSeverityById((int) row.get("severity_id")));
                severityList.put((int) row.get("severity_id"), lockRequest.getSeverity());
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
