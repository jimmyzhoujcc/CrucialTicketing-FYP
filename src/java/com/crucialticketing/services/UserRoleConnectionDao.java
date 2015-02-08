/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.UserRoleConnection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserRoleConnectionDao extends DatabaseService {
    public List<UserRoleConnection> getUserRoleConListByUserId(int userId);
    
    public List<UserRoleConnection> rowMapper(List<Map<String, Object>> resultSet);
}
