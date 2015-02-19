/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.RoleRequestDao;
import com.crucialticketing.entities.RoleRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class RoleRequestService extends JdbcDaoSupport implements RoleRequestDao {

    @Override
    public void insertRoleRequest(RoleRequest roleRequest) {
        String sql = "INSERT INTO role_request "
                + "(role_name, role_description, ticket_id, user_id) VALUES "
                + "(?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            roleRequest.getRole().getRoleName(),
            roleRequest.getRole().getRoleDescription(),
            roleRequest.getTicket().getTicketId(),
            roleRequest.getRequestor().getUserId()
        });
    }

    @Override
    public List<RoleRequest> getRoleRequestList() {
        String sql = "SELECT * FROM role_request ORDER BY role_request_id ASC";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);

        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void removeRequest(int roleRequestId) {
        String sql = "DELETE FROM role_request WHERE role_request_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{roleRequestId});
    }

    @Override
    public List<RoleRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<RoleRequest> roleRequestList = new ArrayList<>();

        for (Map row : resultSet) {
            RoleRequest roleRequest = new RoleRequest();

            roleRequest.setRoleRequestId((int) row.get("role_request_id"));
            roleRequest.getRole().setRoleName((String) row.get("role_name"));
            roleRequest.getRole().setRoleDescription((String) row.get("role_description"));

            roleRequest.getTicket().setTicketId((int)row.get("ticket_id"));
            roleRequest.getRequestor().setUserId((int) row.get("user_id"));
            
            roleRequestList.add(roleRequest);
        }
        return roleRequestList;
    }

}
