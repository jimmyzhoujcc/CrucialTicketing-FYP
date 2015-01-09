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

public class UserDaoImpl implements UserDao {

    @Autowired
    DataSource dataSource;

    @Override
    public void insertUser(User user) {

        String sql = "INSERT INTO user "
                + "(name,age) VALUES (?, ?)";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        /*
         jdbcTemplate.update(
         sql,
         new Object[]{User.getUsername(), User.getAge()});*/
    }

    @Override
    public List<User> getUserList() {

        List userList = new ArrayList();
        /*
         String sql = "SELECT * FROM person";

         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         List<Map<String, Object>> row = jdbcTemplate.queryForList(sql);

         for (Map<String, Object> dbItem : row) {
         userList.add(new Person(
         Integer.valueOf((String)dbItem.get("person_id")), 
         (String)dbItem.get("name"), 
         Integer.valueOf((String)dbItem.get("age"))));
         }
         */
        return userList;
    }

    @Override
    public void deleteUser(String id) {
        String sql = "DELETE FROM user WHERE user_id=" + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql);
    }

    @Override
    public void updateUser(User user) {
        /*
         String sql = "UPDATE user SET name = ?,age = ? WHERE person_id = ?";
         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

         jdbcTemplate.update(
         sql,
         new Object[]{person.getName(), person.getAge()});
         */
    }

    @Override
    public User getUserById(String id) {

        String sql = "SELECT * FROM person WHERE person_id= " + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> personList = jdbcTemplate.queryForList(sql);
        return new User();
    }

    @Override
    public User getUserByUsername(String username) {
        User user = new User();
        String sql = "SELECT * FROM user WHERE username='" + username + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql);

        if (userList.size() > 0) {
            user = new User(new Login(
                    (String) userList.get(0).get("username"),
                    (String) userList.get(0).get("password")));
        } else {
            user = new User(new Login(
                    "",
                    ""));
        }

        return user;
    }

}
