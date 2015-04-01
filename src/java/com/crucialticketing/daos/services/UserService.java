/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.daos.UserDao;
import com.crucialticketing.util.ActiveFlag;
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
        user.setUserId(insertedUserId);
        user.setActiveFlag(ActiveFlag.INCOMPLETE);

        userChangeLogService.insertUserChangeLog(
                new UserChangeLog(user, ticket, requestor, getTimestamp())
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
    public List<User> getUserListByCriteria(String[] inputList, Object[] objectList, int count, boolean populateInternal) {
        String sql = "SELECT * FROM user WHERE ";

        for (int i = 0; i < count; i++) {
            sql += inputList[i] + "='" + objectList[i] + "'";

            if ((i + 1) < count) {
                sql += " AND ";
            }
        }

        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs, populateInternal);
    }

    @Override
    public List<User> getList() {
        String sql = "SELECT * FROM user";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> userList = this.rowMapper(rs, false);
        return userList;
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
    public void updateToUnprocessed(int userId, Ticket ticket, User requestor) {
        String sql = "UPDATE user SET active_flag=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), userId});

        userChangeLogService.insertUserChangeLog(
                new UserChangeLog(this.getUserById(userId, false),
                        ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOnline(int userId, Ticket ticket, User requestor) {
        String sql = "UPDATE user SET active_flag=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), userId});

        userChangeLogService.insertUserChangeLog(
                new UserChangeLog(this.getUserById(userId, false),
                        ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOffline(int userId, Ticket ticket, User requestor) {
        String sql = "UPDATE user SET active_flag=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), userId});

        userChangeLogService.insertUserChangeLog(
                new UserChangeLog(this.getUserById(userId, false),
                        ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateHash(int userId, String hash) {
        String sql = "UPDATE user SET hash=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{hash, userId});
    }

    @Override
    public void updateFirstName(int userId, String firstName) {
        String sql = "UPDATE user SET first_name=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{firstName, userId});
    }

    @Override
    public void updateLastName(int userId, String lastName) {
        String sql = "UPDATE user SET last_name=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{lastName, userId});
    }

    @Override
    public void updateEmail(int userId, String email) {
        String sql = "UPDATE user SET email_address=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{email, userId});
    }

    @Override
    public void updateContact(int userId, String contact) {
        String sql = "UPDATE user SET contact=? WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{contact, userId});
    }

    @Override
    public void removeUser(int userId) {
        String sql = "DELETE FROM user WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userId});
    }

    @Override
    public List<User> rowMapper(List<Map<String, Object>> resultSet, boolean populateInternal) {
        List<User> userList = new ArrayList<>();

        for (Map row : resultSet) {
            User user = new User();

            user.setUserId((int) row.get("user_id"));
            user.setUsername((String) row.get("username"));
            user.setFirstName((String) row.get("first_name"));
            user.setLastName((String) row.get("last_name"));

            if (populateInternal) {
                user.setSecure(new Secure(
                        (String) row.get("hash")));
            }

            user.setEmailAddress((String) row.get("email_address"));
            user.setContact((String) row.get("contact"));

            user.setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]);
            userList.add(user);
        }
        return userList;
    }
}
