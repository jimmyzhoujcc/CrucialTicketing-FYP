/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserQueueConDao;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserQueueCon;
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
public class UserQueueConService extends JdbcDaoSupport implements UserQueueConDao {

    @Autowired
    UserService userService;
    
    @Autowired
    QueueService queueService;
    
    @Override
    public void insertUserQueueCon(UserQueueCon userQueueCon) {
        String sql = "INSERT INTO user_queue_con (user_id, queue_id) VALUES "
                + "(?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{userQueueCon.getUser().getUserId(), userQueueCon.getQueue().getQueueId()});
    }

    @Override
    public List<UserQueueCon> getUserQueueConList() {
        String sql = "SELECT * FROM user_queue_con";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserQueueCon> getUserListByQueueId(int queueId) {
        String sql = "SELECT * FROM user_queue_con WHERE queue_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queueId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserQueueCon> getQueueListByUserId(int userId) {
        String sql = "SELECT * FROM user_queue_con WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserQueueCon> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserQueueCon> userQueueConList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Queue> queueList = new HashMap<>();

        for (Map row : resultSet) {
            UserQueueCon userQueueCon = new UserQueueCon();

            userQueueCon.setUserQueueConId((int) row.get("user_queue_con_id"));
            
            if(userList.containsKey((int) row.get("user_id"))) {
                 userQueueCon.setUser(userList.get((int) row.get("user_id")));
            } else {
                User user = userService.getUserById((int) row.get("user_id"), false);
                userQueueCon.setUser(user);
                userList.put((int) row.get("user_id"), user);
            }
            
            if(queueList.containsKey((int) row.get("queue_id"))) {
                 userQueueCon.setQueue(queueList.get((int) row.get("queue_id")));
            } else {
                Queue queue = queueService.getQueueById((int) row.get("queue_id"));
                userQueueCon.setQueue(queue);
                queueList.put((int) row.get("queue_id"), queue);
            }
            
            userQueueConList.add(userQueueCon);
        }
        return userQueueConList;
    }

}
