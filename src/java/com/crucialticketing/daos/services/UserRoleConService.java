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

    @Override
    public int insertUserRoleCon(final UserRoleCon userRoleCon, final boolean newUserFlag) {
        final String sql = "INSERT INTO user_role_con "
                + "(user_id, role_id, valid_from, valid_to, active_flag, new_user_flag) "
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
                ps.setInt(5, ActiveFlag.INCOMPLETE.getActiveFlag());
                ps.setInt(6, (newUserFlag) ? 1 : 0);
                return ps;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public List<UserRoleCon> getIncompleteUserRoleConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{-2, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getUnprocessedUserRoleConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{-1, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getUnprocessedUserRoleConListByRoleId(Role role, boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE role_id=? AND active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{role.getRoleId(), -1, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public List<UserRoleCon> getUnprocessedUserRoleConListByUserId(User user, boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE user_id=? AND active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{user.getUserId(), -1, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getOnlineUserRoleConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{1, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getOfflineUserRoleConList(boolean newUserFlag) {
        String sql = "SELECT * FROM user_role_con WHERE active_flag=? AND new_user_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{0, (newUserFlag) ? 1 : 0});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void updateToUnprocessed(UserRoleCon userRoleCon) {
        String sql = "UPDATE user_role_con SET active_flag=-1 WHERE user_role_con_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userRoleCon.getUserRoleConId()});
    }

    @Override
    public void updateToOnline(UserRoleCon userRoleCon) {
        String sql = "UPDATE user_role_con SET active_flag=1 WHERE user_role_con_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userRoleCon.getUserRoleConId()});
    }

    @Override
    public void updateToOffline(UserRoleCon userRoleCon) {
        String sql = "UPDATE user_role_con SET active_flag=0 WHERE user_role_con_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userRoleCon.getUserRoleConId()});
    }

    @Override
    public void removeUserRoleConEntry(UserRoleCon userRoleCon) {
        String sql = "DELETE FROM user_role_con WHERE user_role_con_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{userRoleCon.getUserRoleConId()});
    }
    
    @Override
    public void removeAllUserRoleConEntries(User user) {
        String sql = "DELETE FROM user_role_con WHERE user_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{user.getUserId()});
    }

    @Override
    public List<UserRoleCon> getUserListByRoleId(Role role) {
        String sql = "SELECT * FROM user_role_con WHERE role_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{role.getRoleId()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> getRoleListByUserId(User user) {
        String sql = "SELECT * FROM user_role_con WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{user.getUserId()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public boolean doesUserRoleConExistInOnline(User user, Role role) {
        String sql = "SELECT COUNT(user_role_con_id) AS result FROM user_role_con "
                + "WHERE user_id=? AND role_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{user.getUserId(), role.getRoleId(), ActiveFlag.ONLINE});
        int result = Integer.valueOf(rs.get(0).get("result").toString());

        return result != 0;
    }

    @Override
    public List<UserRoleCon> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserRoleCon> userRoleConList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();
        Map<Integer, Role> roleList = new HashMap<>();
        
        for (Map row : resultSet) {
            UserRoleCon userRoleCon = new UserRoleCon();

            userRoleCon.setUserRoleConId((int) row.get("user_role_con_id"));
            
            if (userList.containsKey((int) row.get("user_id"))) {
                userRoleCon.setUser(userList.get((int) row.get("user_id")));
            } else {
                User user = userService.getUserById((int) row.get("user_id"), false);
                userRoleCon.setUser(user);
                userList.put((int) row.get("user_id"), user);
            }

            if (roleList.containsKey((int) row.get("role_id"))) {
                userRoleCon.setRole(roleList.get((int) row.get("role_id")));
            } else {
                Role role = roleService.getRoleById((int) row.get("role_id"));
                userRoleCon.setRole(role);
                roleList.put((int) row.get("role_id"), role);
            }
            
            userRoleCon.setUser(new User((int) row.get("user_id")));

            userRoleCon.setRole(roleService.getRoleById((int) row.get("role_id")));

            userRoleCon.setValidFrom((int) row.get("valid_from"));
            userRoleCon.setValidTo((int) row.get("valid_to"));

            userRoleConList.add(userRoleCon);
        }
        return userRoleConList;
    }
}
