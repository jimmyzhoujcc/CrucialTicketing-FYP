/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.daos.RoleLockRequestDao;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleLockRequest;
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
public class RoleLockRequestService extends JdbcDaoSupport implements RoleLockRequestDao {

    @Autowired
    UserAlertService userAlertService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing

    @Override
    public void addLockRequest(RoleLockRequest lockRequest) {
        this.getJdbcTemplate().update("INSERT INTO role_lock_request "
                + "(role_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    lockRequest.getRole().getRoleId(),
                    lockRequest.getRequestor().getUserId(),
                    getTimestamp(),
                    0});
    }

    @Override
    public boolean checkIfOpen(int roleId, int requestorUserId) {
        String sql = "SELECT COUNT(role_lock_request_id) AS result FROM role_lock_request "
                + "WHERE role_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean checkIfOutstanding(int roleId, int requestorUserId) {
        String sql = "SELECT COUNT(role_lock_request_id) AS result FROM role_lock_request "
                + "WHERE role_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int roleLockRequestId) {
        this.getJdbcTemplate().update("UPDATE role_lock_request SET request_pass_time=? WHERE role_lock_request_id=?", new Object[]{getTimestamp(), roleLockRequestId});
    }

    @Override
    public void denyAccess(int roleLockRequestId, Role role, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE role_lock_request SET request_pass_time=? WHERE role_lock_request_id=?", new Object[]{-1, roleLockRequestId});
        userAlertService.insertUserAlert(requestorUserId, "Access denied to access role (" + role.getRoleName() + ")");
    }

    @Override
    public List<RoleLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM role_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void closeRequest(int roleId, int requestorUserId) {
        String sql = "DELETE FROM role_lock_request WHERE role_id=? AND requestor_user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{roleId, requestorUserId});
    }
    
    @Override
    public List<RoleLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<RoleLockRequest> lockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Role> roleList = new HashMap<>();

        for (Map row : resultSet) {
            RoleLockRequest lockRequest = new RoleLockRequest();

            lockRequest.setLockRequestId((int) row.get("role_lock_request_id"));

            // Role
            if (userList.containsKey((int) row.get("role_id"))) {
                lockRequest.setRole(roleList.get((int) row.get("role_id")));
            } else {
                lockRequest.setRole(roleService.getRoleById((int) row.get("role_id")));
                roleList.put((int) row.get("role_id"), lockRequest.getRole());
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
