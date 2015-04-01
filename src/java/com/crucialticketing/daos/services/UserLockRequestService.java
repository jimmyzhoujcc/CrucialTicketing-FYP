/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserLockRequestDao;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserLockRequest;
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
public class UserLockRequestService extends JdbcDaoSupport implements UserLockRequestDao {

    @Autowired
    UserAlertService userAlertService;
    
    @Autowired
    UserService userService;
    
    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing
    
    @Override
    public void addUserLockRequest(UserLockRequest userLockRequest) {
        this.getJdbcTemplate().update("INSERT INTO user_lock_request "
                + "(user_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    userLockRequest.getUser().getUserId(),
                    userLockRequest.getRequestor().getUserId(),
                    getTimestamp(), 
                    0});
    }

    @Override
    public boolean checkIfOpen(int userId, int requestorUserId) {
        String sql = "SELECT COUNT(user_lock_request_id) AS result FROM user_lock_request "
                + "WHERE user_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean checkIfOutstanding(int userId, int requestorUserId) {
        String sql = "SELECT COUNT(user_lock_request_id) AS result FROM user_lock_request "
                + "WHERE user_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int userLockRequestId) {
        this.getJdbcTemplate().update("UPDATE user_lock_request SET request_pass_time=? WHERE user_lock_request_id=?", new Object[]{getTimestamp(), userLockRequestId});
    }

    @Override
    public void denyAccess(int lockRequestId, User user, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE user_lock_request SET request_pass_time=? WHERE user_lock_request_id=?", new Object[]{-1, lockRequestId});
         userAlertService.insertUserAlert(requestorUserId, "Access denied to access user (" + user.getUsername() + ")");
    }

    @Override
    public List<UserLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM user_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public void closeRequest(int userId, int requestorUserId) {
        String sql = "DELETE FROM user_lock_request WHERE user_id=? AND requestor_user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userId, requestorUserId});
    }

    @Override
    public List<UserLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserLockRequest> userLockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        
        for (Map row : resultSet) {
            UserLockRequest userLockRequest = new UserLockRequest();

            userLockRequest.setLockRequestId((int) row.get("user_lock_request_id"));
            
            // User
            if (userList.containsKey((int) row.get("user_id"))) {
                userLockRequest.setUser(userList.get((int) row.get("user_id")));
            } else {
                User user = userService.getUserById((int) row.get("user_id"), false);
                userLockRequest.setUser(user);
                userList.put((int) row.get("user_id"), user);
            }
            
            // User
            if (userList.containsKey((int) row.get("requestor_user_id"))) {
                userLockRequest.setUser(userList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                userLockRequest.setUser(user);
                userList.put((int) row.get("requestor_user_id"), user);
            }

            userLockRequest.setRequestTime((int) row.get("request_time"));
            userLockRequest.setRequestPassTime((int) row.get("request_pass_time"));

            userLockRequestList.add(userLockRequest);
        }
        return userLockRequestList;
    }

}
