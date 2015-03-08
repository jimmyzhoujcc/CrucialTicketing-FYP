/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Role;
import com.crucialticketing.daos.RoleDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.RoleChangeLog;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.entities.User;
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
public class RoleService extends JdbcDaoSupport implements RoleDao {
    
    @Autowired
    RoleChangeLogService roleChangeLogService;
    
    @Override
    public int insertRole(final Role role, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO role "
                    + "(role_name, role_description, protected, active_flag) "
                    + "VALUES "
                    + "(?, ?, ?, ?)";
            
            KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,  role.getRoleName());
                ps.setString(2, role.getRoleDescription());
                ps.setInt(3, 0);
                ps.setInt(4, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedId = holder.getKey().intValue();
        role.setRoleId(insertedId);
        role.setActiveFlag(ActiveFlag.INCOMPLETE);
                
        roleChangeLogService.insertRoleChange(
          new RoleChangeLog(role, ticket, requestor, getTimestamp())
        );
        
        return insertedId;
    }
    
    @Override
    public Role getRoleById(int roleId) {
        String sql = "SELECT * FROM role WHERE role_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleId});
        if (rs.size() != 1) {
            return new Role();
        }
        return (this.rowMapper(rs)).get(0);
    }
    
    @Override
    public Role getRoleByRoleName(String roleName) {
        String sql = "SELECT * FROM role WHERE role_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleName});
        if (rs.size() != 1) {
            return new Role();
        }
        return (this.rowMapper(rs)).get(0);
    }
     
    @Override
    public boolean doesRoleExist(String roleName) {
        String sql = "SELECT COUNT(role_id) AS result FROM role "
                + "WHERE role_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleName});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesRoleExistInOnline(int roleId) {
        String sql = "SELECT COUNT(role_id) AS result FROM role "
                + "WHERE role_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleId, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesRoleExistInOnline(String roleName) {
        String sql = "SELECT COUNT(role_id) AS result FROM role "
                + "WHERE role_name=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleName, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public boolean doesRoleExistInOnlineOrOffline(String roleName) {
        String sql = "SELECT COUNT(role_id) AS result FROM role "
                + "WHERE role_name=? AND active_flag>?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{roleName, ActiveFlag.UNPROCESSED.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public List<Role> getIncompleteRoleList() {
        String sql = "SELECT * FROM role WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Role> getUnprocessedRoleList() {
        String sql = "SELECT * FROM role WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Role> getOnlineRoleList() {
        String sql = "SELECT * FROM role WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Role> getOfflineRoleList() {
        String sql = "SELECT * FROM role WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void updateToUnprocessed(int roleId, Ticket ticket, User requestor) {
        String sql = "UPDATE role SET active_flag=? WHERE role_id=? AND protected=0";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), roleId});
        
        roleChangeLogService.insertRoleChange(
          new RoleChangeLog(this.getRoleById(roleId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOnline(int roleId, Ticket ticket, User requestor) {
        String sql = "UPDATE role SET active_flag=? WHERE role_id=? AND protected=0";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), roleId});
        
        roleChangeLogService.insertRoleChange(
          new RoleChangeLog(this.getRoleById(roleId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOffline(int roleId, Ticket ticket, User requestor) {
        String sql = "UPDATE role SET active_flag=? WHERE role_id=? AND protected=0";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), roleId});
        
        roleChangeLogService.insertRoleChange(
          new RoleChangeLog(this.getRoleById(roleId), ticket, requestor, getTimestamp())
        );
    }
    
    @Override
    public List<Role> rowMapper(List<Map<String, Object>> resultSet) {
        List<Role> roleList = new ArrayList<>();

        for (Map row : resultSet) {
            Role role = new Role();

            role.setRoleId((int) row.get("role_id"));
            role.setRoleName((String) row.get("role_name"));
            role.setActiveFlag(ActiveFlag.values()[((int)row.get("active_flag"))+2]);
            roleList.add(role);
        }
        return roleList;
    }
}
