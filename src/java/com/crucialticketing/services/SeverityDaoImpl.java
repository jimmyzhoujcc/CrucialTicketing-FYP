/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Severity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Daniel Foley
 */
public class SeverityDaoImpl implements SeverityDao {
    
    @Autowired
    DataSource dataSource;
    
    @Override
    public void insertSeverity(Severity severity) {
        
    }

    @Override
    public List<Severity> getSeverityList() {
        List<Severity> severityList = new ArrayList<>();
        return severityList;
    }

    @Override
    public void updateSeverity(Severity severity) {
        
    }

    @Override
    public void deleteSeverity(String id) {
        
    }

    @Override
    public Severity getSeverityById(String id) {
        Severity severity = new Severity();
        
        String sql = "SELECT * FROM severity WHERE severity_id= " + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> severityData = jdbcTemplate.queryForList(sql);
        
//        severity.setSeverityId((int)severityData.get(0).get("severity_id"));
        severity.setSeverityLevel((int)severityData.get(0).get("severity_level"));
        severity.setSeverityName((String)severityData.get(0).get("severity_name"));
        
        return severity;
    }
}
