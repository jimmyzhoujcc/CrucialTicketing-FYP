/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import java.util.ArrayList;
import java.util.List;
import com.crucialticketing.entities.Application;
import com.crucialticketing.daos.ApplicationDao;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Daniel Foley
 */
public class ApplicationService extends JdbcDaoSupport implements ApplicationDao {

    @Override
    public Application getApplicationById(int applicationId) {
        String sql = "SELECT * FROM application WHERE application_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{applicationId});
        if (rs.size() != 1) {
            return new Application();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<Application> getApplicationList() {
        String sql = "SELECT * FROM application";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public boolean doesApplicationExist(int applicationId) {
        return this.getApplicationById(applicationId).getApplicationId() != 0;
    }

    @Override
    public List<Application> rowMapper(List<Map<String, Object>> resultSet) {
        List<Application> applicationList = new ArrayList<>();

        for (Map row : resultSet) {
            Application application = new Application();

            application.setApplicationId((int) row.get("application_id"));
            application.setApplicationName((String) row.get("application_name"));

            applicationList.add(application);
        }
        return applicationList;
    }
}
