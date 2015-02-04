/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class TicketLogService implements TicketLogDao {

    String selectByTicketId = "SELECT * FROM ticket_log WHERE ticket_id=? ORDER BY stamp DESC";
    JdbcTemplate jdbcTemplate;

    @Override
    public TicketLog getTicketLogByTicketId(int ticketId) {
        String sql = selectByTicketId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{ticketId});
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<TicketLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<TicketLog> ticketLogList = new ArrayList<>();
        
        UserService userService = new UserService();
        userService.setCon(jdbcTemplate);

        TicketLog ticketLog = new TicketLog();

        for (Map row : resultSet) {
            ticketLog.addEntry(
                    (int)row.get("ticket_log_id"),
                    (int)row.get("ticket_id"),
                    userService.getUserById((int)row.get("user_id"), false),
                    (String)row.get("ticket_log_entry"),
                    (int)row.get("stamp"));
        }
        
        ticketLogList.add(ticketLog);
        return ticketLogList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        this.jdbcTemplate = con;
    }

}
