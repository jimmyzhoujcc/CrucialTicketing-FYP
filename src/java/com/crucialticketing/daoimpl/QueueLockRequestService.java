/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.daos.QueueLockRequestDao;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.QueueLockRequest;
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
public class QueueLockRequestService extends JdbcDaoSupport implements QueueLockRequestDao {

    @Autowired
    UserAlertService userAlertService;

    @Autowired
    UserService userService;

    @Autowired
    QueueService queueService;

    private final int buffer = (20 * 60); // 20*60 for 20 minutes of editing

    @Override
    public void addLockRequest(QueueLockRequest lockRequest) {
        this.getJdbcTemplate().update("INSERT INTO queue_lock_request "
                + "(queue_id, requestor_user_id, request_time, request_pass_time) "
                + "VALUES "
                + "(?, ?, ?, ?)", new Object[]{
                    lockRequest.getQueue().getQueueId(),
                    lockRequest.getRequestor().getUserId(),
                    getTimestamp(),
                    0});
    }

    @Override
    public boolean checkIfOpen(int queueId, int requestorUserId) {
        String sql = "SELECT COUNT(queue_lock_request_id) AS result FROM queue_lock_request "
                + "WHERE queue_id=? AND  requestor_user_id=? AND request_pass_time+" + buffer + " > " + getTimestamp();
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queueId, requestorUserId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean checkIfOutstanding(int queueId, int requestorUserId) {
        String sql = "SELECT COUNT(queue_lock_request_id) AS result FROM queue_lock_request "
                + "WHERE queue_id=? AND  requestor_user_id=? AND request_pass_time=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queueId, requestorUserId, 0});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void grantAccess(int queueLockRequestId) {
        this.getJdbcTemplate().update("UPDATE queue_lock_request SET request_pass_time=? WHERE queue_lock_request_id=?", new Object[]{getTimestamp(), queueLockRequestId});
    }

    @Override
    public void denyAccess(int queueLockRequestId, Queue queue, int requestorUserId) {
        this.getJdbcTemplate().update("UPDATE queue_lock_request SET request_pass_time=? WHERE queue_lock_request_id=?", new Object[]{-1, queueLockRequestId});
        userAlertService.insertUserAlert(requestorUserId, "Access denied to access queue (" + queue.getQueueName() + ")");
    }

    @Override
    public List<QueueLockRequest> getOpenRequestList() {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList("SELECT * FROM queue_lock_request WHERE request_pass_time=?", new Object[]{0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public void closeRequest(int queueId, int requestorUserId) {
        String sql = "DELETE FROM queue_lock_request WHERE queue_id=? AND requestor_user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queueId, requestorUserId});
    }

    @Override
    public List<QueueLockRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<QueueLockRequest> lockRequestList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Queue> queueList = new HashMap<>();

        for (Map row : resultSet) {
            QueueLockRequest lockRequest = new QueueLockRequest();

            lockRequest.setLockRequestId((int) row.get("queue_lock_request_id"));

            // Queue
            if (userList.containsKey((int) row.get("queue_id"))) {
                lockRequest.setQueue(queueList.get((int) row.get("queue_id")));
            } else {
                lockRequest.setQueue(queueService.getQueueById((int) row.get("queue_id")));
                queueList.put((int) row.get("queue_id"), lockRequest.getQueue());
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
