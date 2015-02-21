/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserRoleConRequestDao;
import com.crucialticketing.entities.UserRoleConRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class UserRoleConRequestService extends JdbcDaoSupport implements UserRoleConRequestDao {

    @Autowired
    RoleService roleService;

    @Override
    public void insertUserRoleConRequest(int roleId, int userId, int validFrom, 
            int validTo, int ticketId, int requestorUserId) {
        String sql = "INSERT INTO user_role_con_request "
                + "(role_id, user_id, valid_from, valid_to, ticket_id, requestor_user_id) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            roleId,
            userId,
            validFrom,
            validTo,
            ticketId,
            requestorUserId
        });
    }
    
    @Override
    public void removeUserRoleConRequest(int userRoleConRequestId) {
        String sql = "DELETE FROM user_role_con_request WHERE user_role_con_request_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userRoleConRequestId});
    }

    @Override
    public List<UserRoleConRequest> getUserRoleConnectionRequestList() {
        String sql = "SELECT * FROM user_role_con_request ORDER BY user_role_Con_request_id ASC";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleConRequest> getUserRoleConnectionRequestList(int tempUserId) {
        String sql = "SELECT * FROM user_role_con_request WHERE user_id=? ORDER BY user_role_con_request_id ASC";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{tempUserId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleConRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserRoleConRequest> userRoleConnectionRequestList = new ArrayList<>();

        for (Map row : resultSet) {
            UserRoleConRequest userRoleConnectionRequest = new UserRoleConRequest();

            userRoleConnectionRequest.setUserRoleConRequestId((int) row.get("user_role_con_request_id"));
            userRoleConnectionRequest.getRole().setRoleId((int) row.get("role_id"));
            userRoleConnectionRequest.getUser().setUserId((int) row.get("user_id"));

            userRoleConnectionRequest.setValidFrom((int) row.get("valid_from"));
            userRoleConnectionRequest.setValidTo((int) row.get("valid_to"));

            userRoleConnectionRequest.getTicket().setTicketId((int) row.get("ticket_id"));
            userRoleConnectionRequest.getRequestor().setUserId((int) row.get("requestor_user_id"));

            userRoleConnectionRequestList.add(userRoleConnectionRequest);
        }
        return userRoleConnectionRequestList;
    }

}
