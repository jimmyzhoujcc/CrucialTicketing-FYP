/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.entities.TicketLog;
import com.crucialticketing.daos.TicketLogDao;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class TicketLogService extends JdbcDaoSupport implements TicketLogDao {

    @Autowired
    UserService userService;
    
    @Override
    public TicketLog getTicketLogByTicketId(int ticketId) {
        String sql = "SELECT * FROM ticket_log WHERE ticket_id=? ORDER BY stamp DESC";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public void addTicketLog(int ticketId, int userId, String logEntry) {
        String sql = "INSERT INTO ticket_log (ticket_id, user_id, ticket_log_entry, stamp) "
                + "VALUES (?, ?, ?, ?)";
        
        this.getJdbcTemplate().update(sql, new Object[] {ticketId, userId, logEntry, getTimestamp()});
    }
    
    @Override
    public List<TicketLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<TicketLog> ticketLogList = new ArrayList<>();

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
}
