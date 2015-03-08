/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Severity;
import com.crucialticketing.daos.SeverityDao;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.SeverityChangeLog;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
public class SeverityService extends JdbcDaoSupport implements SeverityDao {

    @Autowired
    SeverityChangeLogService severityChangeLogService;

    @Override
    public int insertSeverity(final Severity severity, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO severity "
                + "(severity_level, severity_name, active_flag) "
                + "VALUES "
                + "(?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, severity.getSeverityLevel());
                ps.setString(2, severity.getSeverityName());
                ps.setInt(3, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedId = holder.getKey().intValue();
        severity.setSeverityId(insertedId);
        severity.setActiveFlag(ActiveFlag.INCOMPLETE);

        severityChangeLogService.insertSeverityChangeLog(
                new SeverityChangeLog(severity, ticket, requestor, getTimestamp())
        );

        return insertedId;
    }

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
    public boolean doesSeverityExistByLevel(int severityLevel) {
        String sql = "SELECT COUNT(severity_level) AS result FROM severity "
                + "WHERE severity_level=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{severityLevel});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesSeverityExistInOnlineById(int severityId) {
        String sql = "SELECT COUNT(severity_id) AS result FROM severity "
                + "WHERE severity_id=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{severityId, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesSeverityExistInOnlineByLevel(int severityLevel) {
        String sql = "SELECT COUNT(severity_level) AS result FROM severity "
                + "WHERE severity_level=? AND active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{severityLevel, ActiveFlag.ONLINE.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public boolean doesSeverityExistInOnlineOrOfflineByLevel(int severityLevel) {
        String sql = "SELECT COUNT(severity_level) AS result FROM severity "
                + "WHERE severity_level=? AND active_flag>?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(
                sql, new Object[]{severityLevel, ActiveFlag.UNPROCESSED.getActiveFlag()});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<Severity> getIncompleteQueueList() {
        String sql = "SELECT * FROM severity WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.INCOMPLETE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Severity> getUnprocessedQueueList() {
        String sql = "SELECT * FROM severity WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Severity> getOnlineSeverityList() {
        String sql = "SELECT * FROM severity WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Severity> getOfflineSeverityList() {
        String sql = "SELECT * FROM severity WHERE active_flag=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag()});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public void updateToUnprocessed(int severityId, Ticket ticket, User requestor) {
        String sql = "UPDATE severity SET active_flag=? WHERE severity_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), severityId});

        severityChangeLogService.insertSeverityChangeLog(
                new SeverityChangeLog(this.getSeverityById(severityId),
                        ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOnline(int severityId, Ticket ticket, User requestor) {
        String sql = "UPDATE severity SET active_flag=? WHERE severity_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), severityId});

        severityChangeLogService.insertSeverityChangeLog(
                new SeverityChangeLog(this.getSeverityById(severityId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOffline(int severityId, Ticket ticket, User requestor) {
        String sql = "UPDATE severity SET active_flag=? WHERE severity_id=?";
        this.getJdbcTemplate().update(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), severityId});

        severityChangeLogService.insertSeverityChangeLog(
                new SeverityChangeLog(this.getSeverityById(severityId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public List<Severity> rowMapper(List<Map<String, Object>> resultSet) {
        List<Severity> severityList = new ArrayList<>();

        for (Map row : resultSet) {
            Severity severity = new Severity();

            severity.setSeverityId((int) row.get("severity_id"));
            severity.setSeverityLevel((int) row.get("severity_level"));
            severity.setSeverityName((String) row.get("severity_name"));

            severity.setActiveFlag(ActiveFlag.values()[((int)row.get("active_flag"))+2]);
            severityList.add(severity);
        }
        return severityList;
    }
}
