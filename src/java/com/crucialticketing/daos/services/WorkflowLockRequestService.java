/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.WorkflowLockRequestDao;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowLockRequest;
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
public class WorkflowLockRequestService extends JdbcDaoSupport implements WorkflowLockRequestDao {

    @Autowired
    UserAlertService userAlertService;

    @Autowired
    UserService userService;

    @Autowired
    WorkflowService workflowService;

    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing

    @Override
    public void addLockRequest(WorkflowLockRequest lockRequest) {
        this.getJdbcTemplate().update("INSERT INTO workflow_lock_request "
                + "(workflow_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    lockRequest.getWorkflow().getWorkflowId(),
                    lockRequest.getRequestor().getUserId(),
                    getTimestamp(),
                    0});
    }

    @Override
    public boolean checkIfOpen(int workflowId, int requestorUserId) {
        String sql = "SELECT COUNT(workflow_lock_request_id) AS result FROM workflow_lock_request "
                + "WHERE workflow_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean checkIfOutstanding(int workflowId, int requestorUserId) {
        String sql = "SELECT COUNT(workflow_lock_request_id) AS result FROM workflow_lock_request "
                + "WHERE workflow_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{workflowId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int workflowLockRequestId) {
        this.getJdbcTemplate().update("UPDATE workflow_lock_request SET request_pass_time=? WHERE workflow_lock_request_id=?", new Object[]{getTimestamp(), workflowLockRequestId});
    }

    @Override
    public void denyAccess(int workflowLockRequestId, int workflowId, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE workflow_lock_request SET request_pass_time=? WHERE workflow_lock_request_id=?", new Object[]{-1, workflowLockRequestId});
        userAlertService.insertUserAlert(requestorUserId, "Access denied to access workflow (" + workflowId + ")");
    }

    @Override
    public List<WorkflowLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM workflow_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<WorkflowLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<WorkflowLockRequest> lockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Workflow> workflowList = new HashMap<>();

        for (Map row : resultSet) {
            WorkflowLockRequest lockRequest = new WorkflowLockRequest();

            lockRequest.setWorkflowLockRequestId((int) row.get("workflow_lock_request_id"));

            // Workflow
            if (userList.containsKey((int) row.get("workflow_id"))) {
                lockRequest.setWorkflow(workflowList.get((int) row.get("workflow_id")));
            } else {
                lockRequest.setWorkflow(workflowService.getWorkflow((int) row.get("workflow_id")));
                workflowList.put((int) row.get("workflow_id"), lockRequest.getWorkflow());
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
