/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class RoleService implements RoleDao {
    
    String selectByRoleId = "SELECT * FROM role WHERE role_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public Role getRoleById(int roleId) {
        String sql = selectByRoleId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{roleId});
        if (rs.size() != 1) {
            return new Role();
        }
        return (this.rowMapper(rs)).get(0);
    }
    
    @Override
    public List<Role> getRoleList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }

}
