/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Queue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class QueueService implements QueueDao {
       
    String selectByQueueId = "SELECT * FROM queue WHERE queue_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public Queue getQueueById(int queueId) {
        String sql = selectByQueueId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{queueId});
        if (rs.size() != 1) {
            return new Queue();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<Queue> getQueueList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }
}
