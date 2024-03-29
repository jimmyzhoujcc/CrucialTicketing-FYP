/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserDao {
    
    public int insertUser(final User user, Ticket ticket, User requestor);
    
    public User getUserById(int userId, boolean populateInternal);
    
    public User getUserByUsername(String username, boolean populateInternal);
        
    public boolean doesUserExist(String username);
    
    public boolean doesUserExistInOnline(int userId);
    
    public boolean doesUserExistInOnline(String username);
    
    public boolean doesUserExistInOnlineOrOffline(String username);
    
    public List<User> getUserListByCriteria(String[] inputList, Object[] objectList, int count, boolean populateInternal);
    
    public List<User> getList();
    
    public List<User> getIncompleteUserList();
    
    public List<User> getUnprocessedUserList();
    
    public List<User> getOnlineUserList(boolean populateInternal);
    
    public List<User> getOfflineUserList(boolean populateInternal);
    
    public void updateToUnprocessed(int userId, Ticket ticket, User requestor);
    
    public void updateToOnline(int userId, Ticket ticket, User requestor);
    
    public void updateToOffline(int userId, Ticket ticket, User requestor);
    
    public void updateHash(int userId, String hash);
    
    public void updateFirstName(int userId, String firstName);
    
    public void updateLastName(int userId, String lastName);
    
    public void updateEmail(int userId, String email);
    
    public void updateContact(int userId, String contact);
    
    public void removeUser(int userId);
    
    public List<User> rowMapper(List<Map<String, Object>> resultSet, boolean populateInternal);
}
