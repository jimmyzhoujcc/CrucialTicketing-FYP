/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.UserAlert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class UserAlertService implements UserAlertDao {

    private JdbcTemplate con;
    private String select = "SELECT * FROM user_alert WHERE `user_id`=?";
    private String insert = "INSERT INTO user_alert (`user_id`, `ticket_id`, `message`, `stamp`, `read`) "
            + "VALUES (?, ?, ?, ?, ?)";
    
    @Override
    public void insertUserAlert(int userId, int ticketId, String message) {
        int unixTime = (int)(System.currentTimeMillis() / 1000);
        con.update(insert, new Object[]{userId, ticketId, message, unixTime, 0});
    }
    
    @Override
    public int getAlertCountByUserId(int userId) {
        return (this.getUserAlertListByUserId(userId)).size();
    }

    @Override
    public List<UserAlert> getUserAlertListByUserId(int userId) {
        List<Map<String, Object>> rs = con.queryForList(select, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public void clearNotificationCount(int userId, int marker) {
        String sql = "UPDATE user_alert SET `read`=1 WHERE `user_id`=" + userId + " AND `stamp`<=" + marker;
        con.update(sql);
    }
    
    @Override
    public List<UserAlert> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserAlert> userAlertList = new ArrayList<>();

        for (Map row : resultSet) {
            UserAlert userAlert = new UserAlert();

            userAlert.setUserAlertId((int) row.get("user_alert_id"));
            userAlert.setUserId((int) row.get("user_id"));
            userAlert.setTicketId((int) row.get("ticket_id"));
            userAlert.setMessage((String) row.get("message"));
            userAlert.setStamp((int) row.get("stamp"));
            userAlert.setRead((boolean) ((int)row.get("read") != 0));

            userAlertList.add(userAlert);
        }
        return userAlertList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        this.con = con;
    }
    
}
