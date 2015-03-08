/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserRoleConChangeLogDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRoleCon;
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

    @Override
    public void insertChangeLog(UserRoleConChangeLog changeLog) {
        String sql = "INSERT user_role_con_change_log "
                + "(user_id, role_id, valid_from, valid_to, ticket_id, requestor_user_id, stamp, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            changeLog.getUserRoleCon().getUser().getUserId(),
            changeLog.getUserRoleCon().getRole().getRoleId(),
            changeLog.getUserRoleCon().getValidFrom(),
            changeLog.getUserRoleCon().getValidTo(),
            changeLog.getTicket().getTicketId(),
            changeLog.getRequestor().getUserId(),
            getTimestamp(),
            changeLog.getUserRoleCon().getActiveFlag().getActiveFlag()
        });
    }

    @Override
    public List<UserRoleConChangeLog> getChangeLogByUserId(int userId) {
        String sql = "SELECT * FROM user_role_con_change_log WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return rowMapper(rs);
    }

    @Override
    public List<UserRoleConChangeLog> getChangeLogByRoleId(int roleId) {
        String sql = "SELECT * FROM user_role_con_change_log WHERE role_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{roleId});
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
        Map<Integer, Role> retrievedRoleList = new HashMap<>();
        Map<Integer, Ticket> retrievedTicketList = new HashMap<>();

        for (Map row : resultSet) {
            UserRoleConChangeLog changeLog = new UserRoleConChangeLog();

            changeLog.setUserRoleConChangeLogId((int) row.get("user_role_con_change_log_id"));

            // User Role Con retrieval via Role and User
            // --- getting user
            User user;
            if (retrievedUserList.containsKey((int) row.get("user_id"))) {
                user = retrievedUserList.get((int) row.get("user_id"));
            } else {
                user = userService.getUserById((int) row.get("user_id"), false);
                retrievedUserList.put((int) row.get("user_id"), user);
            }

            // --- getting role
            Role role;
            if (retrievedRoleList.containsKey((int) row.get("role_id"))) {
                role = retrievedRoleList.get((int) row.get("role_id"));
            } else {
                role = roleService.getRoleById((int) row.get("role_id"));
                retrievedRoleList.put((int) row.get("role_id"), role);
            }

            changeLog.setUserRoleCon(
                    new UserRoleCon(
                            user, role,
                            (int) row.get("valid_from"),
                            (int) row.get("valid_to"), 
                            ActiveFlag.values()[((int) row.get("active_flag")) + 2]
                    ));

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
                user = userService.getUserById((int) row.get("requestor_user_id"), false);
                changeLog.setRequestor(user);
                retrievedUserList.put((int) row.get("requestor_user_id"), user);
            }

            changeLog.setStamp((int) row.get("stamp"));

            changeLogList.add(changeLog);
        }
        return changeLogList;
    }

}
