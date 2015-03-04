/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Secure;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserChangeLog;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author DanFoley
 */
public class UserService extends JdbcDaoSupport implements UserDao {

    @Autowired
    UserRoleConService userRoleConService;
    
    @Autowired
    UserChangeLogService userChangeLogService;
    
    @Override
    public int insertUser(final User user, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO user "
                + "(username, hash, first_name, last_name, email_address, contact, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";

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
                ps.setInt(7, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedUserId = holder.getKey().intValue();
        
        userChangeLogService.insertUserChangeLog(
          new UserChangeLog(user, user.getSecure().getHash(), user.getEmailAddress(), 
                  user.getContact(), ticket, requestor, getTimestamp(), ActiveFlag.INCOMPLETE)
        );
        
        return insertedUserId;
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
    public boolean doesUserExist(String username) {
        String sql = "SELECT COUNT(user_id) AS result FROM user "
                + "WHERE username=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{username});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesUserExistInOnline(int userId) {
        String sql = "SELECT COUNT(user_id) AS result FROM user "
                + "WHERE user_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{userId, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesUserExistInOnline(String username) {
        String sql = "SELECT COUNT(user_id) AS result FROM user "
                + "WHERE username=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{username, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesUserExistInOnlineOrOffline(String username) {
        String sql = "SELECT COUNT(user_id) AS result FROM user "
                + "WHERE username=? AND active_flag>?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{username, ActiveFlag.UNPROCESSED.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public List<User> getIncompleteUserList() {
        String sql = "SELECT * FROM user WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> userList = this.rowMapper(rs, false);
        return userList;
    }

    @Override
    public List<User> getUnprocessedUserList() {
        String sql = "SELECT * FROM user WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> userList = this.rowMapper(rs, false);
        return userList;
    }

    @Override
    public List<User> getOnlineUserList(boolean populateInternal) {
        String sql = "SELECT * FROM user WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> userList = this.rowMapper(rs, populateInternal);
        return userList;
    }

    @Override
    public List<User> getOfflineUserList(boolean populateInternal) {
        String sql = "SELECT * FROM user WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> userList = this.rowMapper(rs, populateInternal);
        return userList;
    }

    @Override
    public void updateToUnprocessed(User user, Ticket ticket, User requestor) {
        String sql = "UPDATE user SET active_flag=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), user.getUserId()});
    }

    @Override
    public void updateToOnline(User user, Ticket ticket, User requestor) {
        String sql = "UPDATE user SET active_flag=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), user.getUserId()});
    }

    @Override
    public void updateToOffline(User user, Ticket ticket, User requestor) {
        String sql = "UPDATE user SET active_flag=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), user.getUserId()});
    }

    @Override
    public void updateHash(User user, String hash) {
        String sql = "UPDATE user SET hash=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{hash, user.getUserId()});
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

            user.setEmailAddress((String) row.get("email_address"));
            user.setContact((String) row.get("contact"));
            
            user.setActiveFlag((int)row.get("active_flag"));
            userList.add(user);
        }
        return userList;
    }
}
