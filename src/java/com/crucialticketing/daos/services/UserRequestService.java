/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.UserRequest;
import com.crucialticketing.daos.UserRequestDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class UserRequestService extends JdbcDaoSupport implements UserRequestDao {

    @Override
    public void insertUserRequest(UserRequest userRequest) {
        String sql = "INSERT INTO user_request "
                + "(username, first_name, last_name, email_address, contact, ticket_id, user_id) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{
            userRequest.getUser().getUsername(), 
            userRequest.getUser().getFirstName(), 
            userRequest.getUser().getLastName(), 
            userRequest.getUser().getEmailAddress(), 
            userRequest.getUser().getContact(), 
            userRequest.getTicket().getTicketId(), 
            userRequest.getRequestor().getUserId()
        });
    }

    @Override
    public List<UserRequest> getUserRequestList() {
        String sql = "SELECT * FROM user_request ORDER BY user_request_id ASC";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);

        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void removeRequest(int userRequestId) {
        String sql = "DELETE FROM user_request WHERE user_request_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userRequestId}); 
    }

    @Override
    public List<UserRequest> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserRequest> userRequestList = new ArrayList<>();

        for (Map row : resultSet) {
            UserRequest userRequest = new UserRequest();

            userRequest.setUserRequestId((int) row.get("user_request_id"));
            userRequest.getUser().setUsername((String) row.get("username"));

            userRequest.getUser().setFirstName((String) row.get("first_name"));
            userRequest.getUser().setLastName((String) row.get("last_name"));

            userRequest.getUser().setEmailAddress((String) row.get("email_address"));
            userRequest.getUser().setContact((String) row.get("contact"));

            userRequest.getTicket().setTicketId((int) row.get("ticket_id"));
            
            userRequest.getRequestor().setUserId((int) row.get("user_id"));

            userRequestList.add(userRequest);
        }
        return userRequestList;
    }
}
