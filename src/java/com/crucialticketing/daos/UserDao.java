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
    
    public int insertUser(final User user);
    
    public User getUserById(int userId, boolean populateInternal);
    
    public User getUserByUsername(String username, boolean populateInternal);
    
    public List<User> getIncompleteUserList();
    
    public List<User> getUnprocessedUserList();
    
    public List<User> getOnlineUserList(boolean populateInternal);
    
    public List<User> getOfflineUserList(boolean populateInternal);
    
    public void updateToUnprocessed(User user);
    
    public void updateToOnline(User user);
    
    public void updateToOffline(User user);
    
    public void updateHash(User user, String hash);
    
    public void removeUserEntry(User user);
    
    public boolean doesUserExist(String username);
    
    public boolean doesUserExistInOnline(String username);
    
    public boolean doesUserExistInOnlineOrOffline(String username);
    
    public List<User> rowMapper(List<Map<String, Object>> resultSet, boolean populateInternal);
}
