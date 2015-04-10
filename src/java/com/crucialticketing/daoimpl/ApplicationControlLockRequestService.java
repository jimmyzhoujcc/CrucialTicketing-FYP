/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.daos.ApplicationControlLockRequestDao;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.ApplicationControlLockRequest;
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
public class ApplicationControlLockRequestService extends JdbcDaoSupport implements ApplicationControlLockRequestDao {

    @Autowired
    UserAlertService userAlertService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationControlService applicationControlService;

    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing

    @Override
    public void addLockRequest(ApplicationControlLockRequest lockRequest) {
        this.getJdbcTemplate().update("INSERT INTO application_control_lock_request "
                + "(application_control_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    lockRequest.getApplicationControl().getApplicationControlId(),
                    lockRequest.getRequestor().getUserId(),
                    getTimestamp(),
                    0});
    }

    @Override
    public boolean checkIfOpen(int applicationControlId, int requestorUserId) {
        String sql = "SELECT COUNT(application_control_lock_request_id) AS result FROM application_control_lock_request "
                + "WHERE application_control_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{applicationControlId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean checkIfOutstanding(int applicationControlId, int requestorUserId) {
        String sql = "SELECT COUNT(application_control_lock_request_id) AS result FROM application_control_lock_request "
                + "WHERE application_control_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{applicationControlId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int lockRequestId) {
        this.getJdbcTemplate().update("UPDATE application_control_lock_request SET request_pass_time=? WHERE application_control_lock_request_id=?", new Object[]{getTimestamp(), lockRequestId});
    }

    @Override
    public void denyAccess(int lockRequestId, ApplicationControl applicationControl, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE application_control_lock_request SET request_pass_time=? WHERE application_control_lock_request_id=?", new Object[]{-1, lockRequestId});
        userAlertService.insertUserAlert(requestorUserId, "Access denied to access configuration ("
                + applicationControl.getTicketType().getTicketTypeName() + "-"
                + applicationControl.getApplication().getApplicationName() + "-"
                + applicationControl.getSeverity().getSeverityLevel() + ":" + applicationControl.getSeverity().getSeverityName() + " )");
    }

    @Override
    public List<ApplicationControlLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM application_control_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void closeRequest(int applicationControlId, int requestorUserId) {
        String sql = "DELETE FROM application_control_lock_request WHERE application_control_id=? AND requestor_user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{applicationControlId, requestorUserId});
    }

    @Override
    public List<ApplicationControlLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<ApplicationControlLockRequest> lockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, ApplicationControl> applicationControlList = new HashMap<>();

        for (Map row : resultSet) {
            ApplicationControlLockRequest lockRequest = new ApplicationControlLockRequest();

            lockRequest.setLockRequestId((int) row.get("application_control_lock_request_id"));

            // ApplicationControl
            if (userList.containsKey((int) row.get("application_control_id"))) {
                lockRequest.setApplicationControl(applicationControlList.get((int) row.get("application_control_id")));
            } else {
                lockRequest.setApplicationControl(applicationControlService.getApplicationControlById((int) row.get("application_control_id"), false));
                applicationControlList.put((int) row.get("application_control_id"), lockRequest.getApplicationControl());
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
