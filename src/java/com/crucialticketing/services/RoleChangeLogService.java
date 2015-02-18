/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.RoleChangeLog;
import static com.crucialticketing.entities.Timestamp.getTimestamp;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class RoleChangeLogService extends JdbcDaoSupport implements RoleChangeLogDao {

  
    @Override
    public void insertRoleChange(int roleId, int userId) {
        String sql = "INSERT INTO role_change_log "
                + "(role_id, user_id, stamp) "
                + "VALUES "
                + "(?, ?, ?)";
       this.getJdbcTemplate().update(sql, new Object[]{roleId, userId, getTimestamp()});
    }

    @Override
    public List<RoleChangeLog> getChangeLogById(int roleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RoleChangeLog> rowMapper(List<Map<String, Object>> resultSet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCon(JdbcTemplate con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
