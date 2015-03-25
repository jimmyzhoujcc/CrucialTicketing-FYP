/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.WorkflowStatusLockRequestDao;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStatusLockRequest;
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
public class WorkflowStatusLockRequestService extends JdbcDaoSupport implements WorkflowStatusLockRequestDao {
    
    @Autowired
    UserAlertService userAlertService;

    @Autowired
    UserService userService;

    @Autowired
    WorkflowStatusService workflowStatusService;

    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing

    @Override
    public void addLockRequest(WorkflowStatusLockRequest lockRequest) {
        this.getJdbcTemplate().update("INSERT INTO workflow_status_lock_request "
                + "(workflow_status_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    lockRequest.getWorkflowStatus().getWorkflowStatusId(),
                    lockRequest.getRequestor().getUserId(),
                    getTimestamp(),
                    0});
    }

    @Override
    public boolean checkIfOpen(int workflowStatusId, int requestorUserId) {
        String sql = "SELECT COUNT(workflow_status_lock_request_id) AS result FROM workflow_status_lock_request "
                + "WHERE workflow_status_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowStatusId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean checkIfOutstanding(int workflowStatusId, int requestorUserId) {
        String sql = "SELECT COUNT(workflow_status_lock_request_id) AS result FROM workflow_status_lock_request "
                + "WHERE workflow_status_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowStatusId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int workflowStatusLockRequestId) {
        this.getJdbcTemplate().update("UPDATE workflow_status_lock_request SET request_pass_time=? WHERE workflow_status_lock_request_id=?", new Object[]{getTimestamp(), workflowStatusLockRequestId});
    }

    @Override
    public void denyAccess(int workflowStatusLockRequestId, int workflowStatusId, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE workflow_status_lock_request SET request_pass_time=? WHERE workflow_status_lock_request_id=?", new Object[]{-1, workflowStatusLockRequestId});
        userAlertService.insertUserAlert(requestorUserId, "Access denied to access workflow status (" + workflowStatusId + ")");
    }

    @Override
    public List<WorkflowStatusLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM workflow_status_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<WorkflowStatusLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<WorkflowStatusLockRequest> lockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, WorkflowStatus> roleList = new HashMap<>();

        for (Map row : resultSet) {
            WorkflowStatusLockRequest lockRequest = new WorkflowStatusLockRequest();

            lockRequest.setWorkflowStatusLockRequestId((int) row.get("workflow_status_lock_request_id"));

            // Workflow status
            if (userList.containsKey((int) row.get("workflow_status_id"))) {
                lockRequest.setWorkflowStatus(roleList.get((int) row.get("workflow_status_id")));
            } else {
                lockRequest.setWorkflowStatus(workflowStatusService.getWorkflowStatus((int) row.get("workflow_status_id")));
                roleList.put((int) row.get("workflow_status_id"), lockRequest.getWorkflowStatus());
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
