/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.WorkflowStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class WorkflowStatusService implements DatabaseService {

    @Autowired
    DataSource dataSource;

    @Override
    public void insert(Object o) {

    }

    @Override
    public List<Object> select(String field, String value) {
        List<Object> o = new ArrayList<>();

        String sql = "SELECT * FROM workflow_status WHERE " + field + "=" + value;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> workflowStatusInfo = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> workflowStatusItem : workflowStatusInfo) {
            WorkflowStatus workflowStatus = new WorkflowStatus(
                    (int) workflowStatusItem.get("workflow_status_id"),
                    (String) workflowStatusItem.get("workflow_status_name"));

            o.add((Object) workflowStatus);
        }

        return o;
    }

    @Override
    public void update(String filterField, String filterValue, String updateField, String updateValue) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public List<Object> getTable() {
        return new ArrayList<Object>();
    }
}
