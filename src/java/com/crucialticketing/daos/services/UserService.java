/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserDao;
import com.crucialticketing.entities.Secure;
import com.crucialticketing.entities.User;
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
public class UserService extends JdbcDaoSupport implements UserDao {

    @Override
    public int insertUser(final User user) {
        final String sql = "INSERT INTO user "
                + "(username, hash, first_name, last_name, email_address, contact) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getSecure().getHash());
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setString(5, user.getEmailAddress());
                ps.setString(6, user.getContact());
                return ps;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public User getUserById(int userId, boolean populateInternal) {
        String sql = "SELECT * FROM user WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId});
        if (rs.size() != 1) {
            return new User();
        }
        return (this.rowMapper(rs, populateInternal)).get(0);
    }

    @Override
    public User getUserByUsername(String username, boolean populateInternal) {
        String sql = "SELECT * FROM user WHERE username=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{username});
        if (rs.size() != 1) {
            return new User();
        }
        return (this.rowMapper(rs, populateInternal)).get(0);
    }

    @Override
    public boolean doesUserExist(int userId) {
        String sql = "SELECT COUNT(user_id) AS result FROM user "
                + "WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<User> getUserList(boolean populateInternal) {
        String sql = "SELECT * FROM user";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> userList = this.rowMapper(rs, populateInternal);
        userList.remove(0);
        return userList;
    }

    @Override
    public List<User> rowMapper(List<Map<String, Object>> resultSet, boolean populateInternal) {
        List<User> userList = new ArrayList<>();

        for (Map row : resultSet) {
            User user = new User();

            user.setUserId((int) row.get("user_id"));
            user.setFirstName((String) row.get("first_name"));
            user.setLastName((String) row.get("last_name"));

            if (populateInternal) {
                user.setSecure(new Secure(
                        (String) row.get("hash")));
            }

            userList.add(user);
        }
        return userList;
    }
}
