/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Login;
import com.crucialticketing.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class UserService implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Override
    public User getUserById(int userId, boolean populateLogin) {
        String sql = "SELECT * FROM user WHERE user_id=?";
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{userId});
        if (rs.size() != 1) {
            return new User();
        }
        return (this.rowMapper(rs, populateLogin)).get(0);
    }
    
    @Override
    public User getUserByUsername(String username, boolean populateLogin) {
        String sql = "SELECT * FROM user WHERE username=?";
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{username});
        if (rs.size() != 1) {
            return new User();
        }
        return (this.rowMapper(rs, populateLogin)).get(0);
    }

    @Override
    public List<User> getUserList(boolean populateLogin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> rowMapper(List<Map<String, Object>> resultSet, boolean populateLogin) {
        List<User> userList = new ArrayList<>();

        for (Map row : resultSet) {
            User user = new User();

            user.setUserId((int) row.get("user_id"));
            user.setFirstName((String) row.get("first_name"));
            user.setLastName((String) row.get("last_name"));

            if (populateLogin) {
                user.setLogin(new Login(
                        (String) row.get("username"),
                        (String) row.get("password")));
            }

            userList.add(user);
        }
        return userList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }

}
