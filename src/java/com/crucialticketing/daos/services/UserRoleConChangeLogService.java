/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserRoleConChangeLogDao;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRoleConChangeLog;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class UserRoleConChangeLogService extends JdbcDaoSupport implements UserRoleConChangeLogDao {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserRoleConService userRoleConService;

    @Override
    public void insertChangeLog(UserRoleConChangeLog changeLog) {
        String sql = "INSERT user_role_con_change_log "
                + "(user_role_con_id, valid_from, valid_to, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            changeLog.getUserRoleCon().getUserRoleConId(), 
            changeLog.getUserRoleCon().getValidFrom(),
            changeLog.getUserRoleCon().getValidTo(),
            changeLog.getTicket().getTicketId(),
            changeLog.getRequestor().getUserId(),
            getTimestamp(),
            changeLog.getUserRoleCon().getActiveFlag().getActiveFlag()
        });
    }

    @Override
    public List<UserRoleConChangeLog> getChangeLogByUserRoleConId(int userRoleConId) {
        String sql = "SELECT * FROM user_role_con_change_log WHERE user_role_con_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{userRoleConId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<UserRoleConChangeLog> getChangeLogByTicketId(int ticketId) {
        String sql = "SELECT * FROM user_role_con_change_log WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<UserRoleConChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserRoleConChangeLog> changeLogList = new ArrayList<>();
        Map<Integer, User> retrievedUserList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();

        for (Map row : resultSet) {
            UserRoleConChangeLog changeLog = new UserRoleConChangeLog();

            changeLog.setUserRoleConChangeLogId((int) row.get("user_role_con_change_log_id"));

            // User Role Con retrieval
            changeLog.setUserRoleCon(userRoleConService.getUserRoleConById((int) row.get("user_role_con_id")));

            changeLog.getUserRoleCon().setValidFrom((int) row.get("valid_from"));
            changeLog.getUserRoleCon().setValidTo((int) row.get("valid_to"));
            changeLog.getUserRoleCon().setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]);

            // Ticket checks
            if (retrievedTicketList.containsKey((int) row.get("ticket_id"))) {
                changeLog.setTicket(retrievedTicketList.get((int) row.get("ticket_id")));
            } else {
                Ticket ticket = ticketService.
                        getTicketById((int) row.get("ticket_id"), false, false, false, false);
                changeLog.setTicket(ticket);
                retrievedTicketList.put((int) row.get("ticket_id"), ticket);
            }

            // Requestor (user) list
            if (retrievedUserList.containsKey((int) row.get("requestor_user_id"))) {
                changeLog.setRequestor(retrievedUserList.get((int) row.get("requestor_user_id")));
            } else {
                User user = userService.getUserById((int) row.get("requestor_user_id"), false);
                changeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }

            changeLog.setStamp((int) row.get("stamp"));

            changeLogList.add(changeLog);
        }
        return changeLogList;
    }

}
