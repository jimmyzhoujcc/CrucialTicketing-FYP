/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRoleCon;
import com.crucialticketing.daos.UserRoleConDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class UserRoleConService extends JdbcDaoSupport implements UserRoleConDao {

    @Autowired
    RoleService roleService;

    @Override
    public void insertUserRoleCon(int userId, int roleId, int validFrom, int validTo) {
        String sql = "INSERT INTO user_role_con "
                + "(user_id, role_id, valid_from, valid_to) "
                + "VALUES "
                + "(?, ?, ?, ?)";

        this.getJdbcTemplate().update(sql, new Object[]{
            userId,
            roleId,
            validFrom,
            validTo});
    }

    @Override
    public boolean doesConExist(int userId, int roleId) {
        String sql = "SELECT COUNT(user_role_con_id) AS result FROM user_role_con "
                + "WHERE user_id=? AND role_id=?";
         List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId, roleId});
         int result = Integer.valueOf(rs.get(0).get("result").toString());
         
        return result != 0;
    }

    @Override
    public List<UserRoleCon> getUserRoleConListByUserId(int userId) {
        String sql = "SELECT * FROM user_role_con WHERE user_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{userId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<UserRoleCon> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserRoleCon> userRoleConnectionList = new ArrayList<>();

        for (Map row : resultSet) {
            UserRoleCon userRoleConnection = new UserRoleCon();

            userRoleConnection.setUserRoleConId((int) row.get("user_role_con_id"));
            userRoleConnection.setUser(new User((int) row.get("user_id")));

            userRoleConnection.setRole(roleService.getRoleById((int) row.get("role_id")));

            userRoleConnection.setValidFrom((int) row.get("valid_from"));
            userRoleConnection.setValidTo((int) row.get("valid_to"));

            userRoleConnectionList.add(userRoleConnection);
        }
        return userRoleConnectionList;
    }
}
