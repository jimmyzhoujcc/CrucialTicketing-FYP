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
public class SeverityServiceImpl implements SeverityService {
    
    @Autowired
    SeverityDao severityDao;
    
    @Override
    public void insertSeverity(Severity severity) {
        severityDao.insertSeverity(severity);
    }

    @Override
    public List<Severity> getSeverityList() {
        return severityDao.getSeverityList();
    }

    @Override
    public void updateSeverity(Severity severity) {
        severityDao.updateSeverity(severity);
    }

    @Override
    public void deleteSeverity(String id) {
        severityDao.deleteSeverity(id);
    }

    @Override
    public Severity getSeverityById(String id) {
        return severityDao.getSeverityById(id);
    }
}
