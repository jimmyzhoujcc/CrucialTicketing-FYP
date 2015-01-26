/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.crucialticketing.entities.Application;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Daniel Foley
 */
public class ApplicationService implements ApplicationDao {
    String selectByApplicationId = "SELECT * FROM application WHERE application_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public Application getApplicationById(int applicationId) {
        String sql = selectByApplicationId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{applicationId});
        if (rs.size() != 1) {
            return new Application();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<Application> getApplicationList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }

}
