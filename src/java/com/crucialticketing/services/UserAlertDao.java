/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.UserAlert;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserAlertDao extends DatabaseService {
    public void insertUserAlert(int userId, int ticketId, String message);
    
    public int getAlertCountByUserId(int userId);
    
    public List<UserAlert> getUserAlertListByUserId(int userId);

    public void clearNotificationCount(int userId, int marker);
    
    public List<UserAlert> rowMapper(List<Map<String, Object>> resultSet);
}
