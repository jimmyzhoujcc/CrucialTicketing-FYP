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
import java.util.Date;
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
        int unixTime = (int) (((new Date()).getTime()) / 1000);

        TicketLogEntry ticketLogEntry = (TicketLogEntry) o;

        String sql = "INSERT ticket_log (ticket_id, user_id, ticket_log_entry, stamp) "
                + "VALUES("
                + ticketLogEntry.getTicketId() + ", "
                + ticketLogEntry.getUser().getUserId() + ", '"
                + ticketLogEntry.getLogEntry() + "', "
                + unixTime + ")";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        int executedQuery = jdbcTemplate.update(sql);
    }

    @Override
    public List<Object> select(String field, String value) {
        List<Object> o = new ArrayList<>();

        String sql = "SELECT * FROM ticket_log JOIN user ON ticket_log.user_id=user.user_id "
                + "WHERE " + field + "=" + value + " ORDER BY ticket_log_id DESC";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> ticketLogInfo = jdbcTemplate.queryForList(sql);

        TicketLog ticketLog = new TicketLog();

        for (Map<String, Object> tableItem : ticketLogInfo) {

            ticketLog.addEntry(
                    (int) tableItem.get("ticket_id"),
                    (int) tableItem.get("ticket_log_id"),
                    new User(
                            (int) tableItem.get("user_id"),
                            (String) tableItem.get("first_name"),
                            (String) tableItem.get("last_name")),
                    (String) tableItem.get("ticket_log_entry"),
                    (int) tableItem.get("stamp"));

        }

        o.add((Object) ticketLog);

        return o;

    }

    @Override
    public void update(String filterField, String filterValue, String updateField, String updateValue) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public List<Object> getTable() {
        return new ArrayList<Object>();
    }
}
