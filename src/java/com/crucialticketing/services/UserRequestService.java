/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.UserRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class UserRequestService implements UserRequestDao {

    private JdbcTemplate con;

    @Override
    public void insertUserRequest(UserRequest userRequest) {
        String sql = "INSERT INTO user_request "
                + "(username, first_name, last_name, email_address, contact, ticket_id, user_id) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";
        con.update(sql, new Object[]{
            userRequest.getUser().getUsername(), 
            userRequest.getUser().getFirstName(), 
            userRequest.getUser().getLastName(), 
            userRequest.getUser().getEmailAddress(), 
            userRequest.getUser().getContact(), 
            userRequest.getUser().getTicket().getTicketId(), 
            userRequest.getRequestor().getUserId()
        });
    }

    @Override
    public List<UserRequest> getUserRequestList() {
        String sql = "SELECT * FROM user_request ORDER BY user_request_id ASC";
        List<Map<String, Object>> rs = con.queryForList(sql);

        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void removeRequest(int userRequestId) {
        String sql = "DELETE FROM user_request WHERE user_request_id=?";
        con.update(sql, new Object[]{userRequestId}); 
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

            userRequest.getUser().getTicket().setTicketId((int) row.get("ticket_id"));
            
            userRequest.getRequestor().setUserId((int) row.get("user_id"));

            userRequestList.add(userRequest);
        }
        return userRequestList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        this.con = con;
    }

}
