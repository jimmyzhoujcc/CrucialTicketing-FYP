/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Queue;
import com.crucialticketing.daos.QueueDao;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author DanFoley
 */
public class QueueService extends JdbcDaoSupport implements QueueDao {

    @Override
    public int insertQueue(final Queue queue) {
        final String sql = "INSERT INTO queue (queue_name, active_flag) VALUES (?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, queue.getQueueName());
                ps.setInt(2, -2);
                return ps;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public Queue getQueueById(int queueId) {
        String sql = "SELECT * FROM queue WHERE queue_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queueId});
        if (rs.size() != 1) {
            return new Queue();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<Queue> getIncompleteQueueList() {
        String sql = "SELECT * FROM queue WHERE active_flag=-2";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Queue> getUnprocessedQueueList() {
        String sql = "SELECT * FROM queue WHERE active_flag=-1";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Queue> getOfflineQueueList() {
        String sql = "SELECT * FROM queue WHERE active_flag=0";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Queue> getOnlineQueueList() {
        String sql = "SELECT * FROM queue WHERE active_flag=1";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public boolean doesQueueExistFromAll(String queueName) {
        String sql = "SELECT COUNT(queue_id) AS result FROM queue "
                + "WHERE queue_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queueName});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesQueueExistPostUnprocessed(String queueName) {
        String sql = "SELECT COUNT(queue_id) AS result FROM queue "
                + "WHERE queue_name=? AND active_flag>?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queueName, -1});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public void updateToUnprocessed(Queue queue) {
        String sql = "UPDATE queue SET active_flag=-1 WHERE queue_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queue.getQueueId()});
    }
    
    @Override
    public void updateToOnline(Queue queue) {
        String sql = "UPDATE queue SET active_flag=1 WHERE queue_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queue.getQueueId()});
    }
    
    @Override
    public void updateToOffline(Queue queue) {
        String sql = "UPDATE queue SET active_flag=0 WHERE queue_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queue.getQueueId()});
    }
    
    @Override
    public void removeQueueEntry(Queue queue) {
        String sql = "DELETE FROM queue WHERE queue_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queue.getQueueId()});
    }

    @Override
    public List<Queue> rowMapper(List<Map<String, Object>> resultSet) {
        List<Queue> queueList = new ArrayList<>();

        for (Map row : resultSet) {
            Queue queue = new Queue();

            queue.setQueueId((int) row.get("queue_id"));
            queue.setQueueName((String) row.get("queue_name"));

            queueList.add(queue);
        }
        return queueList;
    }
}
