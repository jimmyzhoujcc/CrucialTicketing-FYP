/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Queue;
import com.crucialticketing.daos.QueueDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.QueueChangeLog;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.entities.User;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class QueueService extends JdbcDaoSupport implements QueueDao {

    @Autowired
    QueueChangeLogService queueChangeLogService;
 
    @Override
    public int insertQueue(final Queue queue, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO queue (queue_name, active_flag) VALUES (?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, queue.getQueueName());
                ps.setInt(2, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedId = holder.getKey().intValue();
        queue.setQueueId(insertedId);
        queue.setActiveFlag(ActiveFlag.INCOMPLETE);
        
        queueChangeLogService.insertQueueChangeLog(
          new QueueChangeLog(queue, ticket, requestor, getTimestamp())
        );
        
        return insertedId;
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
    public boolean doesQueueExistInOnline(int queueId) {
        String sql = "SELECT COUNT(queue_id) AS result FROM queue "
                + "WHERE queue_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{queueId, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesQueueExistInOnline(String queueName) {
        String sql = "SELECT COUNT(queue_id) AS result FROM queue "
                + "WHERE queue_name=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{queueName, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesQueueExistInOnlineOrOffline(String queueName) {
        String sql = "SELECT COUNT(queue_id) AS result FROM queue "
                + "WHERE queue_name=? AND active_flag>?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{queueName, ActiveFlag.UNPROCESSED.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesQueueExist(String queueName) {
        String sql = "SELECT COUNT(queue_id) AS result FROM queue "
                + "WHERE queue_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{queueName});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<Queue> getIncompleteQueueList() {
        String sql = "SELECT * FROM queue WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Queue> getUnprocessedQueueList() {
        String sql = "SELECT * FROM queue WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Queue> getOfflineQueueList() {
        String sql = "SELECT * FROM queue WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Queue> getOnlineQueueList() {
        String sql = "SELECT * FROM queue WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag()});
        if (rs.size() != 1) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void updateToUnprocessed(int queueId, Ticket ticket, User requestor) {
        String sql = "UPDATE queue SET active_flag=? WHERE queue_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), queueId});
        
        queueChangeLogService.insertQueueChangeLog(
          new QueueChangeLog(this.getQueueById(queueId), ticket, requestor, getTimestamp())
        );
    }
    
    @Override
    public void updateToOnline(int queueId, Ticket ticket, User requestor) {
        String sql = "UPDATE queue SET active_flag=? WHERE queue_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), queueId});
        
        queueChangeLogService.insertQueueChangeLog(
          new QueueChangeLog(this.getQueueById(queueId), ticket, requestor, getTimestamp())
        );
    }
    
    @Override
    public void updateToOffline(int queueId, Ticket ticket, User requestor) {
        String sql = "UPDATE queue SET active_flag=? WHERE queue_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), queueId});
        
        queueChangeLogService.insertQueueChangeLog(
          new QueueChangeLog(this.getQueueById(queueId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public List<Queue> rowMapper(List<Map<String, Object>> resultSet) {
        List<Queue> queueList = new ArrayList<>();

        for (Map row : resultSet) {
            Queue queue = new Queue();

            queue.setQueueId((int) row.get("queue_id"));
            queue.setQueueName((String) row.get("queue_name"));
            queue.setActiveFlag(ActiveFlag.values()[((int)row.get("active_flag"))+2]);

            queueList.add(queue);
        }
        return queueList;
    }
}
