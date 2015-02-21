/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.QueueRequestDao;
import com.crucialticketing.entities.QueueRequest;
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
public class QueueRequestService extends JdbcDaoSupport implements QueueRequestDao {

    @Override
    public int insertQueueRequest(final QueueRequest queueRequest) {
        final String sql = "INSERT INTO queue_request "
                + "(queue_name, ticket_id, requestor_user_id, ready_to_process) "
                + "VALUES (?, ?, ?, ?)";
        
         KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, queueRequest.getQueue().getQueueName());
                ps.setInt(2, queueRequest.getTicket().getTicketId());
                ps.setInt(3, queueRequest.getRequestor().getUserId());
                ps.setInt(4, 0);
                return ps;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public void setReadyToProcess(int queueRequestId) {
        String sql = "UPDATE queue_request SET ready_to_process=1 WHERE queue_request_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queueRequestId});               
    }

    @Override
    public List<QueueRequest> getQueueRequestList() {
        String sql = "SELECT * FROM queue_request";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void removeRequest(int queueRequestId) {
        String sql = "DELETE FROM queue_request WHERE queue_request_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{queueRequestId});  
    }

    @Override
    public List<QueueRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<QueueRequest> queueRequestList = new ArrayList<>();

        for (Map row : resultSet) {
            QueueRequest queueRequest = new QueueRequest();

            queueRequest.setQueueRequestId((int) row.get("queue_request_id"));
            queueRequest.getQueue().setQueueName((String) row.get("queue_name"));
            queueRequest.getTicket().setTicketId((int) row.get("ticket_id"));
            queueRequest.getRequestor().setUserId((int) row.get("requestor_user_id"));
          
            queueRequestList.add(queueRequest);
        }
        return queueRequestList;
    }
    
}
