/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Role;
import com.crucialticketing.daos.RoleDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class RoleService extends JdbcDaoSupport implements RoleDao {
    
    @Override
    public void insertRole(Role role) {
        String sql = "INSERT INTO role "
                    + "(role_name, role_description) "
                    + "VALUES "
                    + "(?, ?)";
            this.getJdbcTemplate().update(sql, new Object[]{
            role.getRoleName(), role.getRoleDescription()});
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
    public List<Role> getRoleList() {
        String sql = "SELECT * FROM role";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public boolean doesRoleExist(int roleId) {
        return this.getRoleById(roleId).getRoleId() != 0;
    }
    
    @Override
    public boolean doesRoleExist(String roleName) {
        return this.getRoleByRoleName(roleName).getRoleId() != 0;
    }
    
    @Override
    public List<Role> rowMapper(List<Map<String, Object>> resultSet) {
        List<Role> roleList = new ArrayList<>();

        for (Map row : resultSet) {
            Role role = new Role();

            role.setRoleId((int) row.get("role_id"));
            role.setRoleName((String) row.get("role_name"));

            roleList.add(role);
        }
        return roleList;
    }
}
