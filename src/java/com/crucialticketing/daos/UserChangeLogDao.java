/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserChangeLogDao {

    public void insertUserChangeLog(UserChangeLog userChangeLog);

    public List<UserChangeLog> getUserChangeLogList();

    public List<UserChangeLog> getUserChangeLogListByUserId(int userId);

    public void removeUserChangeLogEntryByUserId(int userId);
            
    public List<UserChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
