/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserDao {
    public int insertUser(User user);
    
    public User getUserById(int userId, boolean populateInternal);
    
    public User getUserByUsername(String username, boolean populateInternal);
    
    public boolean doesUserExist(int userId);
    
    public List<User> getUserList(boolean populateInternal);
    
    public List<User> rowMapper(List<Map<String, Object>> resultSet, boolean populateInternal);
}
