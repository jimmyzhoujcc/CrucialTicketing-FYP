/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.UserQueueCon;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserQueueConRequestDao {
    public  void insertUserQueueConRequest(UserQueueCon userQueueConRequest);
    
    public List<UserQueueCon> getUserQueueConRequestList();
    
    public void removeUserQueueConRequest(int userQueueConRequestId);
    
    public List<UserQueueCon> rowMapper(List<Map<String, Object>> resultSet);  
}
