/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.daos.UserQueueConDao;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.UserQueueConChangeLog;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author DanFoley
 */
public class UserQueueConService extends JdbcDaoSupport implements UserQueueConDao {

    @Autowired
    UserService userService;

    @Autowired
    QueueService queueService;

    @Autowired
    UserQueueConChangeLogService changeLogService;

    @Override
    public int insertUserQueueCon(final UserQueueCon userQueueCon, final boolean newUserFlag, Ticket ticket, User requestor) {
        final String sql = "INSERT INTO user_queue_con (user_id, queue_id, new_queue_flag, active_flag) VALUES "
                + "(?, ?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        this.getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userQueueCon.getUser().getUserId());
                ps.setInt(2, userQueueCon.getQueue().getQueueId());
                ps.setInt(3, 0);
                ps.setInt(4, ActiveFlag.INCOMPLETE.getActiveFlag());
                return ps;
            }
        }, holder);

        int insertedId = holder.getKey().intValue();
        userQueueCon.setUserQueueConId(insertedId);
        userQueueCon.setActiveFlag(ActiveFlag.INCOMPLETE);
        
        changeLogService.insertChangeLog(
                new UserQueueConChangeLog(userQueueCon, ticket, requestor, getTimestamp())
        );

        return insertedId;
    }
    
    @Override
    public UserQueueCon getUserQueueConById(int userQueueConId) {
        String sql = "SELECT * FROM user_queue_con WHERE user_queue_con_id=?";
        return this.queryForSingle(sql, new Object[]{userQueueConId});
    }

    @Override
    public boolean doesUserQueueConExist(int userId, int queueId) {
        String sql = "SELECT COUNT(user_queue_con_id) AS result FROM user_queue_con "
                + "WHERE user_id=? AND queue_id=?";
        return this.queryForExistCheck(sql, new Object[]{userId, queueId});
    }
    
    @Override
    public boolean doesUserQueueConExistOnlineOrOffline(int userId, int queueId) {
        String sql = "SELECT COUNT(user_queue_con_id) AS result FROM user_queue_con "
                + "WHERE user_id=? AND queue_id=? AND active_flag>=?";
        return this.queryForExistCheck(sql, new Object[]{userId, queueId, ActiveFlag.OFFLINE});
    }

    @Override
    public boolean doesUserQueueConExistInOnline(int userId, int queueId) {
        String sql = "SELECT COUNT(user_queue_con_id) AS result FROM user_queue_con "
                + "WHERE user_id=? AND queue_id=? AND active_flag=?";
        return this.queryForExistCheck(sql, new Object[]{userId, queueId, ActiveFlag.ONLINE});
    }

    @Override
    public List<UserQueueCon> getUserListByQueueId(int queueId) {
        String sql = "SELECT * FROM user_queue_con WHERE queue_id=?";
        return this.queryForArray(sql, new Object[]{queueId});
    }

    @Override
    public List<UserQueueCon> getQueueListByUserId(int userId) {
        String sql = "SELECT * FROM user_queue_con WHERE user_id=?";
        return this.queryForArray(sql, new Object[]{userId});
    }

    @Override
    public List<UserQueueCon> getIncompleteUserQueueConList(boolean newQueueFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE active_flag=? AND new_queue_flag=?";
        return this.queryForArray(sql, new Object[]{-2, (newQueueFlag) ? 1 : 0});
    }

    @Override
    public List<UserQueueCon> getUnprocessedUserQueueConList(boolean newQueueFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE active_flag=? AND new_queue_flag=?";
        return this.queryForArray(sql, new Object[]{-1, (newQueueFlag) ? 1 : 0});
    }

    @Override
    public List<UserQueueCon> getUnprocessedUserQueueConListByQueueId(Queue queue, boolean newQueueFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE queue_id=? AND active_flag=? AND new_queue_flag=?";
        return this.queryForArray(sql, new Object[]{queue.getQueueId(), -1, (newQueueFlag) ? 1 : 0});
    }

    @Override
    public List<UserQueueCon> getOnlineUserQueueConList(boolean newQueueFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE active_flag=? AND new_queue_flag=?";
        return this.queryForArray(sql, new Object[]{1, (newQueueFlag) ? 1 : 0});
    }

    @Override
    public List<UserQueueCon> getOfflineUserQueueConList(boolean newQueueFlag) {
        String sql = "SELECT * FROM user_queue_con WHERE active_flag=? AND new_queue_flag=?";
        return this.queryForArray(sql, new Object[]{0, (newQueueFlag) ? 1 : 0});
    }

    @Override
    public void updateToUnprocessed(int userQueueConId, Ticket ticket, User requestor) {
        String sql = "UPDATE user_queue_con SET active_flag=? WHERE user_queue_con_id=?";
        this.queryUpdate(sql, new Object[]{ActiveFlag.UNPROCESSED.getActiveFlag(), userQueueConId});
        
        changeLogService.insertChangeLog(
          new UserQueueConChangeLog(this.getUserQueueConById(userQueueConId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOnline(int userQueueConId, Ticket ticket, User requestor) {
        String sql = "UPDATE user_queue_con SET active_flag=? WHERE user_queue_con_id=?";
        this.queryUpdate(sql, new Object[]{ActiveFlag.ONLINE.getActiveFlag(), userQueueConId});
        
        changeLogService.insertChangeLog(
          new UserQueueConChangeLog(this.getUserQueueConById(userQueueConId), ticket, requestor, getTimestamp())
        );
    }

    @Override
    public void updateToOffline(int userQueueConId, Ticket ticket, User requestor) {
        String sql = "UPDATE user_queue_con SET active_flag=? WHERE user_queue_con_id=?";
        this.queryUpdate(sql, new Object[]{ActiveFlag.OFFLINE.getActiveFlag(), userQueueConId});
        
        changeLogService.insertChangeLog(
          new UserQueueConChangeLog(this.getUserQueueConById(userQueueConId), ticket, requestor, getTimestamp())
        );
    }
    
    @Override
    public void removeUserQueueCon(int userQueueConId) {
        String sql = "DELETE FROM user_queue_con WHERE user_queue_con_id=?";
        this.queryUpdate(sql, new Object[]{userQueueConId});    
    }

    private UserQueueCon queryForSingle(String sql, Object[] objectList) {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, objectList);
        if (rs.size() != 1) {
            return new UserQueueCon();
        }
        return this.rowMapper(rs).get(0);
    }

    private List<UserQueueCon> queryForArray(String sql, Object[] objectList) {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, objectList);
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    private void queryUpdate(String sql, Object[] objectList) {
        this.getJdbcTemplate().update(sql, objectList);
    }

    private boolean queryForExistCheck(String sql, Object[] objectList) {
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, objectList);
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    @Override
    public List<UserQueueCon> rowMapper(List<Map<String, Object>> resultSet) {
        List<UserQueueCon> userQueueConList = new ArrayList<>();
        Map<Integer, User> userList = new HashMap<>();

        for (Map row : resultSet) {
            UserQueueCon userQueueCon = new UserQueueCon();

            userQueueCon.setUserQueueConId((int) row.get("user_queue_con_id"));

            if (userList.containsKey((int) row.get("user_id"))) {
                userQueueCon.setUser(userList.get((int) row.get("user_id")));
            } else {
                User user = userService.getUserById((int) row.get("user_id"), false);
                userQueueCon.setUser(user);
                userList.put((int) row.get("user_id"), user);
            }

            userQueueCon.setActiveFlag(ActiveFlag.values()[((int)row.get("active_flag"))+2]);
            
            userQueueConList.add(userQueueCon);
        }
        return userQueueConList;
    }

}
