/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserDao extends DatabaseService {
    public User getUserById(int userId, boolean populateLogin);
    
    public User getUserByUsername(String username, boolean populateLogin);
    
    public List<User> getUserList(boolean populateLogin);
    
    public List<User> rowMapper(List<Map<String, Object>> resultSet, boolean populateLogin);
}
