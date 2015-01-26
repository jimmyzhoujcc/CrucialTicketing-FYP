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
public class SeverityService implements SeverityDao {

    String selectBySeverityId = "SELECT * FROM severity WHERE severity_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public Severity getSeverityById(int severityId) {
        String sql = selectBySeverityId;
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, new Object[]{severityId});
        if (rs.size() != 1) {
            return new Severity();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<Severity> getSeverityList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Severity> rowMapper(List<Map<String, Object>> resultSet) {
        List<Severity> severityList = new ArrayList<>();

        for (Map row : resultSet) {
            Severity severity = new Severity();

            severity.setSeverityId((int) row.get("severity_id"));
            severity.setSeverityLevel((int) row.get("severity_level"));
            severity.setSeverityName((String) row.get("severity_name"));

            severityList.add(severity);
        }
        return severityList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }

}
