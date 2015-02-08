/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRoleConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class UserRoleConnectionService implements UserRoleConnectionDao {

    private JdbcTemplate con;
    
    @Override
    public List<UserRoleConnection> getUserRoleConListByUserId(int userId) {
        String sql = "SELECT * FROM user_role_con WHERE user_id=?";
        List<Map<String, Object>> rs = con.queryForList(sql, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleConnection> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserRoleConnection> userRoleConnectionList = new ArrayList<>();

        for (Map row : resultSet) {
            UserRoleConnection userRoleConnection = new UserRoleConnection();

            userRoleConnection.setUserRoleConnectionId((int) row.get("user_role_con_id"));        
            userRoleConnection.setUser(new User((int) row.get("user_id")));
            
            RoleService roleService = new RoleService();
            roleService.setCon(con);
            
            userRoleConnection.setRole(roleService.getRoleById((int) row.get("role_id")));
            
            userRoleConnection.setValidFrom((int) row.get("valid_from"));
            userRoleConnection.setValidTo((int) row.get("valid_to"));
            userRoleConnection.setAdded((int) row.get("added"));

            userRoleConnectionList.add(userRoleConnection);
        }
        return userRoleConnectionList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        this.con = con;
    }
    
}
