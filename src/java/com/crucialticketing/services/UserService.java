/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

/**
 *
 * @author Daniel Foley
 */
import com.crucialticketing.entities.Login;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.crucialticketing.entities.User;
import java.util.Map;

public class UserService implements DatabaseService {

    @Autowired
    DataSource dataSource;

    @Override
    public void insert(Object o) {

        String sql = "INSERT INTO user "
                + "(name,age) VALUES (?, ?)";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        /*
         jdbcTemplate.update(
         sql,
         new Object[]{User.getUsername(), User.getAge()});*/
    }

    @Override
    public List<Object> select(String field, String value) {
        List<Object> o = new ArrayList<Object>();

        String sql = "SELECT * FROM user WHERE " + field + "='" + value + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> userReturn = jdbcTemplate.queryForList(sql);

        User user;
        
        if (!userReturn.isEmpty()) {
            for (Map<String, Object> userItem : userReturn) {

                user = new User(
                        (int) userItem.get("user_id"),
                        (String) userItem.get("first_name"),
                        (String) userItem.get("last_name"));

                user.setLogin(new Login(
                        (String) userItem.get("username"),
                        (String) userItem.get("password")));

                o.add((Object) user);
            }
        } else {
            user = new User();
        }

        return o;
    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {
        User user = (User) o;
        String sql = "DELETE FROM user WHERE user_id=" + user.getUserId();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql);
    }

    @Override
    public List<Object> getTable() {
        List<Object> userList = new ArrayList<Object>();

        String sql = "SELECT * FROM user";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> row = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> dbItem : row) {
            userList.add(new User(
                    (int) dbItem.get("user_id"),
                    (String) dbItem.get("first_name"),
                    (String) dbItem.get("last_name")));
        }
        return userList;
    }

}
