/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.UserRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserRequestDao extends DatabaseService {
    public void insertUserRequest(UserRequest userRequest);
    
    public List<UserRequest> getUserRequestList();
    
    public void removeRequest(int userRequestId);
    
    public List<UserRequest> rowMapper(List<Map<String, Object>> resultSet);
}
