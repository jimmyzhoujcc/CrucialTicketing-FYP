/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.UserRequest;
import com.crucialticketing.daos.UserRequestDao;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author DanFoley
 */
public class UserRequestService extends JdbcDaoSupport implements UserRequestDao {

    @Override
    public int insertUserRequest(final UserRequest userRequest) {
        final String sql = "INSERT INTO user_request "
                + "(username, first_name, last_name, email_address, contact, ticket_id, requestor_user_id, ready_to_process) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, userRequest.getUser().getUsername());
                ps.setString(2, userRequest.getUser().getFirstName());
                ps.setString(3, userRequest.getUser().getLastName());
                ps.setString(4, userRequest.getUser().getFirstName());
                ps.setString(5, userRequest.getUser().getFirstName());
                ps.setInt(6, userRequest.getTicket().getTicketId());
                ps.setInt(7, userRequest.getRequestor().getUserId());
                ps.setInt(8,0);
                return ps;
            }
        }, holder);

        return holder.getKey().intValue();
    }
    
    @Override
    public void setReadyToProcess(int userRequestId) {
        String sql = "UPDATE user_request SET ready_to_process=1 WHERE user_request_id=?";
        this.getJdbcTemplate().update(sql, new Object[] {userRequestId});
    }

    @Override
    public List<UserRequest> getUserRequestList() {
        String sql = "SELECT * FROM user_request WHERE ready_to_process=1 ORDER BY user_request_id ASC";
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
            userRequest.getUser().setUserId((int) row.get("user_request_id"));
            userRequest.getUser().setUsername((String) row.get("username"));

            userRequest.getUser().setFirstName((String) row.get("first_name"));
            userRequest.getUser().setLastName((String) row.get("last_name"));

            userRequest.getUser().setEmailAddress((String) row.get("email_address"));
            userRequest.getUser().setContact((String) row.get("contact"));

            userRequest.getTicket().setTicketId((int) row.get("ticket_id"));
            
            userRequest.getRequestor().setUserId((int) row.get("requestor_user_id"));

            userRequestList.add(userRequest);
        }
        return userRequestList;
    }
}
