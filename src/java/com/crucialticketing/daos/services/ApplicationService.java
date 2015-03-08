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
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.ApplicationChangeLog;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.entities.User;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Daniel Foley
 */
public class ApplicationService extends JdbcDaoSupport implements ApplicationDao {

    @Autowired
    ApplicationChangeLogService applicationChangeLogService;
    
    @Override
    public int insertApplication(final Application application, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO application "
                + "(application_name, active_flag) "
                + "VALUES "
                + "(?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, application.getApplicationName());
                ps.setInt(2, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedApplicationId = holder.getKey().intValue();
        application.setApplicationId(insertedApplicationId);
        application.setActiveFlag(ActiveFlag.INCOMPLETE);
        
        applicationChangeLogService.insertApplicationChangeLog(
          new ApplicationChangeLog(application, ticket, requestor, getTimestamp())
        );
        
        return insertedApplicationId;
    }

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
    public boolean doesApplicationExist(String applicationName) {
        String sql = "SELECT COUNT(application_id) AS result FROM application "
                + "WHERE application_name=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{applicationName});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesApplicationExistInOnline(int applicationId) {
        String sql = "SELECT COUNT(application_id) AS result FROM application "
                + "WHERE application_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{applicationId, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesApplicationExistInOnline(String applicationName) {
        String sql = "SELECT COUNT(application_id) AS result FROM application "
                + "WHERE application_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{applicationName, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesApplicationExistInOnlineOrOffline(String applicationName) {
        String sql = "SELECT COUNT(application_id) AS result FROM application "
                + "WHERE application_id=? AND active_flag>?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{applicationName, ActiveFlag.UNPROCESSED.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }
    
    @Override
    public List<Application> getIncompleteApplicationList() {
        String sql = "SELECT * FROM application WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Application> getUnprocessedApplicationList() {
        String sql = "SELECT * FROM application WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Application> getOnlineApplicationList() {
        String sql = "SELECT * FROM application WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Application> getOfflineApplicationList() {
        String sql = "SELECT * FROM application WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void updateToUnprocessed(int applicationId, Ticket ticket, User requestor) {
        String sql = "UPDATE application SET active_flag=? WHERE application_id=?";
        this.getJdbcTemplate().update(
                sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), applicationId});
        
        applicationChangeLogService.insertApplicationChangeLog(
          new ApplicationChangeLog(this.getApplicationById(applicationId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOnline(int applicationId, Ticket ticket, User requestor) {
        String sql = "UPDATE application SET active_flag=? WHERE application_id=?";
        this.getJdbcTemplate().update(
                sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), applicationId});
        
        applicationChangeLogService.insertApplicationChangeLog(
          new ApplicationChangeLog(this.getApplicationById(applicationId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOffline(int applicationId, Ticket ticket, User requestor) {
        String sql = "UPDATE application SET active_flag=? WHERE application_id=?";
        this.getJdbcTemplate().update(
                sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), applicationId});
        
        applicationChangeLogService.insertApplicationChangeLog(
          new ApplicationChangeLog(this.getApplicationById(applicationId), ticket, requestor, getTimestamp())
        );
    }
    
    @Override
    public List<Application> rowMapper(List<Map<String, Object>> resultSet) {
        List<Application> applicationList = new ArrayList<>();

        for (Map row : resultSet) {
            Application application = new Application();

            application.setApplicationId((int) row.get("application_id"));
            application.setApplicationName((String) row.get("application_name"));

            application.setActiveFlag(ActiveFlag.values()[((int) row.get("active_flag")) + 2]); // +2 offset for array

            applicationList.add(application);
        }
        return applicationList;
    }
}
