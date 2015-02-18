/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Secure;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class UserService implements UserDao {

    private JdbcTemplate con;

    @Override
    public String insertUser(User user) {
        PasswordHash passwordHash = new PasswordHash();
        String generatedPassword = passwordHash.generatePassword(8);

        try {
            String hash = passwordHash.createHash(generatedPassword);
            int activated = (int) (System.currentTimeMillis() / 1000);
            String sql = "INSERT INTO user "
                    + "(username, hash, first_name, last_name, email_address, contact, ticket_id, activated, deactivated) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            con.update(sql, new Object[]{
                user.getUsername(),
                hash,
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getContact(),
                user.getTicket().getTicketId(),
                activated,
                0
            });

            return generatedPassword;
        } catch (Exception e) {
        }

        return generatedPassword;
    }

    @Override
    public User getUserById(int userId, boolean populateInternal) {
        String sql = "SELECT * FROM user WHERE user_id=?";
        List<Map<String, Object>> rs = con.queryForList(sql, new Object[]{userId});
        if (rs.size() != 1) {
            return new User();
        }
        return (this.rowMapper(rs, populateInternal)).get(0);
    }

    @Override
    public User getUserByUsername(String username, boolean populateInternal) {
        String sql = "SELECT * FROM user WHERE username=?";
        List<Map<String, Object>> rs = con.queryForList(sql, new Object[]{username});
        if (rs.size() != 1) {
            return new User();
        }
        return (this.rowMapper(rs, populateInternal)).get(0);
    }

    @Override
    public List<User> getUserList(boolean populateInternal) {
        String sql = "SELECT * FROM user";
        List<Map<String, Object>> rs = con.queryForList(sql);
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

    @Override
    public void setCon(JdbcTemplate con) {
        this.con = con;
    }

}
