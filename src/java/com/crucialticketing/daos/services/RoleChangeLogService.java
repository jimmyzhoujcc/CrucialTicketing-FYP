/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.RoleChangeLog;
import com.crucialticketing.daos.RoleChangeLogDao;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.entities.Timestamp.getTimestamp;
import com.crucialticketing.entities.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class RoleChangeLogService extends JdbcDaoSupport implements RoleChangeLogDao {

    @Autowired
    UserService userService;
    
    @Autowired
    TicketService ticketService;
    
    @Autowired
    RoleService roleService;
    
    @Override
    public void insertRoleChange(RoleChangeLog roleChangeLog) {
        String sql = "INSERT INTO role_change_log "
                + "(role_id, active_flag, ticket_id, requestor_user_id, stamp) "
                + "VALUES "
                + "(?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            roleChangeLog.getRole().getRoleId(), 
            roleChangeLog.getActiveFlag(), 
            roleChangeLog.getTicket().getTicketId(), 
            roleChangeLog.getRequestor().getUserId(), 
            roleChangeLog.getStamp()
        });
    }

    @Override
    public List<RoleChangeLog> getChangeLogByRoleId(Role role) {
        String sql = "SELECT * FROM role_change_log WHERE role_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql,
                new Object[]{role.getRoleId()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<RoleChangeLog> getChangeLogByTicketId(Ticket ticket) {
        String sql = "SELECT * FROM role_change_log WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql,
                new Object[]{ticket.getTicketId()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void removeRoleChangeLogEntry(RoleChangeLog roleChangeLog) {
        String sql = "DELETE FROM role_change_log WHERE role_change_log_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{roleChangeLog.getRoleChangeLogId()});
    }

    @Override
    public void removeAllRoleChangeLogEntries(Role role) {
        String sql = "DELETE FROM role_change_log WHERE role_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{role.getRoleId()});
    }

    @Override
    public List<RoleChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<RoleChangeLog> roleChangeLogList = new ArrayList<>();
        Map<Integer, Role> retrievedRoleList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();

        for (Map row : resultSet) {
            RoleChangeLog roleChangeLog = new RoleChangeLog();

            // Role checks
            if (retrievedRoleList.containsKey((int) row.get("role_id"))) {
                roleChangeLog.setRole(retrievedRoleList.get((int) row.get("role_id")));
            } else {
                Role role = roleService.getRoleById((int) row.get("role_id"));
                roleChangeLog.setRole(role);
                retrievedRoleList.put((int) row.get("role_id"), role);
            }
            
            // Ticket checks
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                roleChangeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false);
                roleChangeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }
            
            // User Checks
            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                roleChangeLog.setRequestor(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                roleChangeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }
         
            roleChangeLog.setActiveFlag((int) row.get("active_flag"));
            roleChangeLog.setStamp((int) row.get("stamp"));
            
            roleChangeLogList.add(roleChangeLog);
        }
        return roleChangeLogList;
    }
}
