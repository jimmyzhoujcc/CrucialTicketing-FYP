/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.UserAlert;
import com.crucialticketing.daos.UserAlertDao;
import static com.crucialticketing.entities.Timestamp.getTimestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class UserAlertService extends JdbcDaoSupport implements UserAlertDao {

    @Override
    public void insertUserAlert(int userId, String message) {
        String sql = "INSERT INTO user_alert (user_id, message, `read`, stamp) "
            + "VALUES (?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{userId, message, 0, getTimestamp()});
    }
    
    @Override
    public UserAlert getUserAlertById(int userAlertId) {
        String sql = "SELECT * FROM user_alert WHERE user_alert_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userAlertId});
        if (rs.isEmpty()) {
            return new UserAlert();
        }
        return rowMapper(rs).get(0);
    }
    
    @Override
    public int getAlertCountByUserId(int userId) {
        return (this.getUserAlertListByUserId(userId)).size();
    }

    @Override
    public List<UserAlert> getUserAlertListByUserId(int userId) {
        String sql = "SELECT * FROM user_alert WHERE user_id=? ORDER BY user_alert_id DESC";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public void clearNotificationCount(int userId, int marker) {
        String sql = "UPDATE user_alert SET `read`=1 WHERE `user_id`=" + userId + " AND `stamp`<=" + marker;
        this.getJdbcTemplate().update(sql);
    }
    
    @Override
    public List<UserAlert> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserAlert> userAlertList = new ArrayList<>();

        for (Map row : resultSet) {
            UserAlert userAlert = new UserAlert();

            userAlert.setUserAlertId((int) row.get("user_alert_id"));
            userAlert.setUserId((int) row.get("user_id"));
            userAlert.setMessage((String) row.get("message"));
            userAlert.setRead((boolean) ((int)row.get("read") != 0));
             userAlert.setStamp((int) row.get("stamp"));

            userAlertList.add(userAlert);
        }
        return userAlertList;
    }
}
