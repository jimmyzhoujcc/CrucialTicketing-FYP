/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.User;
import java.util.List;

/**
 *
 * @author Owner
 */
public interface UserService {

    public void insertUser(User user);

    public List<User> getUserList();

    public void deleteUser(String id);

    public void updateUser(User user);
    
    public User getUserById(String id);
    
    public User getUserByUsername(String username);
}
