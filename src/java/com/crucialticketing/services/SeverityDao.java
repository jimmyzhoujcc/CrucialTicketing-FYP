/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Severity;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface SeverityDao extends DatabaseService {
    public Severity getSeverityById(int severityId);
    
    public List<Severity> getSeverityList();
    
    public boolean doesSeverityExist(int severityId);
    
    public List<Severity> rowMapper(List<Map<String, Object>> resultSet);
}
