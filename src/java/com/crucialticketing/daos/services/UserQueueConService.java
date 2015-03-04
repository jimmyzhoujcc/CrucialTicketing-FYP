/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserQueueConDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserQueueCon;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
    public int insertUserQueueCon(final UserQueueCon userQueueCon, final boolean newUserFlag) {
        final String sql = "INSERT INTO user_queue_con (user_id, queue_id, active_flag, new_user_flag) VALUES "
                + "(?, ?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userQueueCon.getUser().getUserId());
                ps.setInt(2, userQueueCon.getQueue().getQueueId());
                ps.setInt(3, -2);
                ps.setInt(4, (newUserFlag) ? 1 : 0);
                return ps;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public List<UserQueueCon> getIncompleteUserQueueConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{-2, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserQueueCon> getUnprocessedUserQueueConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{-1, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public List<UserQueueCon> getUnprocessedUserQueueConListByQueueId(Queue queue, boolean newUserFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE queue_id=? AND active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queue.getQueueId(), -1, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserQueueCon> getOnlineUserQueueConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{1, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserQueueCon> getOfflineUserQueueConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{0, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void updateToUnprocessed(UserQueueCon userQueueCon) {
        String sql = "UPDATE user_queue_con SET active_flag=-1 WHERE user_queue_con_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userQueueCon.getUserQueueConId()});
    }

    @Override
    public void updateToOnline(UserQueueCon userQueueCon) {
        String sql = "UPDATE user_queue_con SET active_flag=1 WHERE user_queue_con_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userQueueCon.getUserQueueConId()});
    }

    @Override
    public void updateToOffline(UserQueueCon userQueueCon) {
        String sql = "UPDATE user_queue_con SET active_flag=0 WHERE user_queue_con_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userQueueCon.getUserQueueConId()});
    }

    @Override
    public void removeUserQueueConEntry(UserQueueCon userQueueCon) {
        String sql = "DELETE FROM user_queue_con WHERE user_queue_con_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userQueueCon.getUserQueueConId()});
    }
    
    @Override
    public void removeAllUserQueueConEntries(User user) {
        String sql = "DELETE FROM user_queue_con WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{user.getUserId()});
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
    public boolean doesUserQueueConExistInOnline(int userId, int queueId) {
        String sql = "SELECT COUNT(user_queue_con_id) AS result FROM user_queue_con "
                + "WHERE user_id=? AND queue_id=? AND active_flag=?";
         List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId, queueId, ActiveFlag.ONLINE});
         int result = Integer.valueOf(rs.get(0).get("result").toString());
         
        return result != 0;
    }
    
    @Override
    public List<UserQueueCon> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserQueueCon> userQueueConList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Queue> queueList = new HashMap<>();

        for (Map row : resultSet) {
            UserQueueCon userQueueCon = new UserQueueCon();

            userQueueCon.setUserQueueConId((int) row.get("user_queue_con_id"));

            if (userList.containsKey((int) row.get("user_id"))) {
                userQueueCon.setUser(userList.get((int) row.get("user_id")));
            } else {
                User user = userService.getUserById((int) row.get("user_id"), false);
                userQueueCon.setUser(user);
                userList.put((int) row.get("user_id"), user);
            }

            if (queueList.containsKey((int) row.get("queue_id"))) {
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
