/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserDao;
import com.crucialticketing.entities.Secure;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class UserService extends JdbcDaoSupport implements UserDao {

    @Override
    public String insertUser(User user) {
        PasswordHash passwordHash = new PasswordHash();
        String generatedPassword = passwordHash.generatePassword(8);

        try {
            String hash = passwordHash.createHash(generatedPassword);
            int activated = (int) (System.currentTimeMillis() / 1000);
            String sql = "INSERT INTO user "
                    + "(username, hash, first_name, last_name, email_address, contact) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?)";
            this.getJdbcTemplate().update(sql, new Object[]{
                user.getUsername(),
                hash,
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getContact(),
            });

            return generatedPassword;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | DataAccessException e) {
        }

        return generatedPassword;
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
