/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Severity;
import com.crucialticketing.daos.SeverityDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Daniel Foley
 */
public class SeverityService extends JdbcDaoSupport implements SeverityDao {

    @Override
    public Severity getSeverityById(int severityId) {
        String sql = "SELECT * FROM severity WHERE severity_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{severityId});
        if (rs.size() != 1) {
            return new Severity();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<Severity> getSeverityList() {
        String sql = "SELECT * FROM severity";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public boolean doesSeverityExist(int severityId) {
        return this.getSeverityById(severityId).getSeverityId() != 0;
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
}
