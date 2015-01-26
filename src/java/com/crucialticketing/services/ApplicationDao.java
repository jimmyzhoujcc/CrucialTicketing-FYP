/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Application;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ApplicationDao extends DatabaseService {
    public Application getApplicationById(int applicationId);
    
    public List<Application> getApplicationList();
    
    public List<Application> rowMapper(List<Map<String, Object>> resultSet);
}
