/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import java.util.List;

import com.crucialticketing.entities.User;

public interface UserDao {

    public void insertUser(User user);

    public List<User> getUserList();

    public void updateUser(User user);

    public void deleteUser(String id);

    public User getUserById(String id);
    
    public User getUserByUsername(String username);
}
