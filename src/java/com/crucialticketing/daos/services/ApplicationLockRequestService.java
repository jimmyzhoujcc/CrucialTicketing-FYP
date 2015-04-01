/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.ApplicationLockRequestDao;
import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationLockRequest;
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
public class ApplicationLockRequestService extends JdbcDaoSupport implements ApplicationLockRequestDao {

    @Autowired
    UserAlertService userAlertService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationService applicationService;

    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing

    @Override
    public void addLockRequest(ApplicationLockRequest lockRequest) {
        this.getJdbcTemplate().update("INSERT INTO application_lock_request "
                + "(application_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    lockRequest.getApplication().getApplicationId(),
                    lockRequest.getRequestor().getUserId(),
                    getTimestamp(),
                    0});
    }

    @Override
    public boolean checkIfOpen(int applicationId, int requestorUserId) {
        String sql = "SELECT COUNT(application_lock_request_id) AS result FROM application_lock_request "
                + "WHERE application_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{applicationId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean checkIfOutstanding(int applicationId, int requestorUserId) {
        String sql = "SELECT COUNT(application_lock_request_id) AS result FROM application_lock_request "
                + "WHERE application_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{applicationId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int applicationLockRequestId) {
        this.getJdbcTemplate().update("UPDATE application_lock_request SET request_pass_time=? WHERE application_lock_request_id=?", new Object[]{getTimestamp(), applicationLockRequestId});
    }

    @Override
    public void denyAccess(int applicationLockRequestId, Application application, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE application_lock_request SET request_pass_time=? WHERE application_lock_request_id=?", new Object[]{-1, applicationLockRequestId});
        userAlertService.insertUserAlert(requestorUserId, "Access denied to access application (" + application.getApplicationName() + ")");
    }

    @Override
    public List<ApplicationLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM application_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public void closeRequest(int applicationId, int requestorUserId) {
        String sql = "DELETE FROM application_lock_request WHERE application_id=? AND requestor_user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{applicationId, requestorUserId});
    }

    @Override
    public List<ApplicationLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<ApplicationLockRequest> lockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Application> applicationList = new HashMap<>();

        for (Map row : resultSet) {
            ApplicationLockRequest lockRequest = new ApplicationLockRequest();

            lockRequest.setLockRequestId((int) row.get("application_lock_request_id"));

            // Application
            if (userList.containsKey((int) row.get("application_id"))) {
                lockRequest.setApplication(applicationList.get((int) row.get("application_id")));
            } else {
                lockRequest.setApplication(applicationService.getApplicationById((int) row.get("application_id")));
                applicationList.put((int) row.get("application_id"), lockRequest.getApplication());
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
