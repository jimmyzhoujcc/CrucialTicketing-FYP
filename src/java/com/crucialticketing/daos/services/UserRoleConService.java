/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRoleCon;
import com.crucialticketing.daos.UserRoleConDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.UserRoleConChangeLog;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
public class UserRoleConService extends JdbcDaoSupport implements UserRoleConDao {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleConChangeLogService changeLogService;

    @Override
    public int insertUserRoleCon(final UserRoleCon userRoleCon, final boolean newUserFlag, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO user_role_con "
                + "(user_id, role_id, valid_from, valid_to, new_user_flag, active_flag) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userRoleCon.getUser().getUserId());
                ps.setInt(2, userRoleCon.getRole().getRoleId());
                ps.setInt(3, userRoleCon.getValidFrom());
                ps.setInt(4, userRoleCon.getValidTo());
                ps.setInt(5, (newUserFlag) ? 1 : 0);
                ps.setInt(6, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedId = holder.getKey().intValue();
        userRoleCon.setUserRoleConId(insertedId);
        userRoleCon.setActiveFlag(ActiveFlag.INCOMPLETE);
        
        changeLogService.insertChangeLog(
                new UserRoleConChangeLog(userRoleCon, ticket, requestor, getTimestamp())
        );

        return insertedId;
    }
    
    @Override
    public UserRoleCon getUserRoleConById(int userRoleConId) {
        String sql = "SELECT * FROM user_role_con WHERE user_role_con_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userRoleConId});
        if (rs.size() != 1) {
            return new UserRoleCon();
        }
        return this.rowMapper(rs).get(0);
    }
    
    @Override
    public List<UserRoleCon> getUserListByRoleId(int roleId) {
        String sql = "SELECT * FROM user_role_con WHERE role_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getRoleListByUserId(int userId) {
        String sql = "SELECT * FROM user_role_con WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public boolean doesUserRoleConExist(int userId, int roleId) {
        String sql = "SELECT COUNT(user_role_con_id) AS result FROM user_role_con "
                + "WHERE user_id=? AND role_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{userId, roleId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesUserRoleConExistInOnline(int userId, int roleId) {
        String sql = "SELECT COUNT(user_role_con_id) AS result FROM user_role_con "
                + "WHERE user_id=? AND role_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{userId, roleId, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<UserRoleCon> getIncompleteUserRoleConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag(), (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getUnprocessedUserRoleConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getUnprocessedUserRoleConListByRoleId(int roleId, boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE role_id=? AND active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{roleId, ActiveFlag.INCOMPLETE.getActiveFlag(), (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getOnlineUserRoleConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getOfflineUserRoleConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void updateToUnprocessed(int userRoleConId, Ticket ticket, User requestor) {
        String sql = "UPDATE user_role_con SET active_flag=? WHERE user_role_con_id=?";
        this.getJdbcTemplate().update(
                sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), userRoleConId});
        
        changeLogService.insertChangeLog(
                new UserRoleConChangeLog(this.getUserRoleConById(userRoleConId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOnline(int userRoleConId, Ticket ticket, User requestor) {
        String sql = "UPDATE user_role_con SET active_flag=? WHERE user_role_con_id=?";
        this.getJdbcTemplate().update(
                sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), userRoleConId});
        
        changeLogService.insertChangeLog(
                new UserRoleConChangeLog(this.getUserRoleConById(userRoleConId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOffline(int userRoleConId, Ticket ticket, User requestor) {
        String sql = "UPDATE user_role_con SET active_flag=? WHERE user_role_con_id=?";
        this.getJdbcTemplate().update(
                sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), userRoleConId});
        
        changeLogService.insertChangeLog(
                new UserRoleConChangeLog(this.getUserRoleConById(userRoleConId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public List<UserRoleCon> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserRoleCon> userRoleConList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Role> roleList = new HashMap<>();

        for (Map row : resultSet) {
            UserRoleCon userRoleCon = new UserRoleCon();

            userRoleCon.setUserRoleConId((int) row.get("user_role_con_id"));

            // User
            if (userList.containsKey((int) row.get("user_id"))) {
                userRoleCon.setUser(userList.get((int) row.get("user_id")));
            } else {
                User user = userService.getUserById((int) row.get("user_id"), false);
                userRoleCon.setUser(user);
                userList.put((int) row.get("user_id"), user);
            }

            // Role
            if (roleList.containsKey((int) row.get("role_id"))) {
                userRoleCon.setRole(roleList.get((int) row.get("role_id")));
            } else {
                Role role = roleService.getRoleById((int) row.get("role_id"));
                userRoleCon.setRole(role);
                roleList.put((int) row.get("role_id"), role);
            }

            userRoleCon.setValidFrom((int) row.get("valid_from"));
            userRoleCon.setValidTo((int) row.get("valid_to"));

            userRoleCon.setActiveFlag(ActiveFlag.values()[((int)row.get("active_flag"))+2]);
            
            userRoleConList.add(userRoleCon);
        }
        return userRoleConList;
    }
}
