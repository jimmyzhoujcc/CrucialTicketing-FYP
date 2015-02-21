/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.UserRoleCon;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserRoleConDao {
    public void insertUserRoleCon(int userId, int roleId, int validFrom, int validTo);
    
    public boolean doesConExist(int userId, int roleId);
    
    public List<UserRoleCon> getUserRoleConListByUserId(int userId);
    
    public List<UserRoleCon> rowMapper(List<Map<String, Object>> resultSet);
}
