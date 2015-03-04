/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserQueueCon;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserQueueConDao {
    public int insertUserQueueCon(final UserQueueCon userQueueCon, final boolean newUserFlag);
    
    public List<UserQueueCon> getIncompleteUserQueueConList(boolean newUserFlag);
    
    public List<UserQueueCon> getUnprocessedUserQueueConList(boolean newUserFlag);
    
    public List<UserQueueCon> getUnprocessedUserQueueConListByQueueId(Queue queue, boolean newUserFlag);
    
    public List<UserQueueCon> getOnlineUserQueueConList(boolean newUserFlag);
    
    public List<UserQueueCon> getOfflineUserQueueConList(boolean newUserFlag);
    
    public void updateToUnprocessed(UserQueueCon userQueueCon);
    
    public void updateToOnline(UserQueueCon userQueueCon);
    
    public void updateToOffline(UserQueueCon userQueueCon);
    
    public void removeUserQueueConEntry(UserQueueCon userQueueCon);
    
    public void removeAllUserQueueConEntries(User user);
    
    public List<UserQueueCon> getUserListByQueueId(int queueId);
    
    public List<UserQueueCon> getQueueListByUserId(int userId);
    
    public boolean doesUserQueueConExistInOnline(int userId, int queueId);
    
    public List<UserQueueCon> rowMapper(List<Map<String, Object>> resultSet);  
}
