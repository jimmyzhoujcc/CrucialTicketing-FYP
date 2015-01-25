/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Lock;
import com.crucialticketing.entities.LockEntry;
import com.crucialticketing.entities.User;
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
public class LockService implements DatabaseService {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Override
    public void insert(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> select(String field, String value) {
        List<Object> o = new ArrayList<>();
        Lock lock = new Lock();

        String sql = "SELECT * FROM lock JOIN user ON lock.user_id=user.user_id WHERE " + field + "=" + value;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> lockInfo = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> lockItem : lockInfo) {
            LockEntry lockEntry = new LockEntry();

            lockEntry.setLockId((int) lockItem.get("lock_id"));
            lockEntry.setTicketId((int) lockItem.get("ticket_id"));
            lockEntry.setTimeLocked((int) lockItem.get("time_locked"));

            List<Object> userList = userService.select("user_id", String.valueOf((int) lockItem.get("user_id")));
            lockEntry.setUser((User) userList.get(0));

            lock.addLockEntry(lockEntry);
        }

        o.add((Object) lock);

        return o;
    }

    @Override
    public void update(String filterField, String filterValue, String updateField, String updateValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> getTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
