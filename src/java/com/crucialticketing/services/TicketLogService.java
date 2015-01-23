/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketLog;
import com.crucialticketing.entities.TicketLogEntry;
import com.crucialticketing.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class TicketLogService implements DatabaseService {

    @Autowired
    DataSource dataSource;

    @Override
    public void insert(Object o) {

    }

    @Override
    public List<Object> select(String field, String value) {
        List<Object> o = new ArrayList<>();

        String sql = "SELECT * FROM ticket_log JOIN user ON ticket_log.user_id=user.user_id "
                + "WHERE " + field + "=" + value;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> ticketLogInfo = jdbcTemplate.queryForList(sql);

        TicketLog ticketLog = new TicketLog();
        
        for (Map<String, Object> tableItem : ticketLogInfo) {
            
            
            ticketLog.addEntry(
                    (int) tableItem.get("ticket_log_id"),
                    new User(
                            (int) tableItem.get("user_id"),
                            (String) tableItem.get("first_name"),
                            (String) tableItem.get("last_name")),
                    (String) tableItem.get("ticket_log_entry"),
                    (int) tableItem.get("stamp"));
            
           
        }
        
        o.add((Object)ticketLog);

        return o;

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public List<Object> getTable() {
        return new ArrayList<Object>();
    }
}
