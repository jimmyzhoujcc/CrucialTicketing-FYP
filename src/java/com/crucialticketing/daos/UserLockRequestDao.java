/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface UserLockRequestDao {

    public void addUserLockRequest(UserLockRequest userLockRequest);

    public boolean checkIfOpen(int userId, int requestorUserId);

    public boolean checkIfOpen(int userId);
    
    public boolean checkIfOutstanding(int userId, int requestorUserId);

    public void grantAccess(int lockRequestId);

    public void denyAccess(int lockRequestId, User user, int requestorUserId);

    public List<UserLockRequest> getOpenRequestList();

    public void closeRequest(int userId, int requestorUserId);

    public List<UserLockRequest> rowMapper(List<Map<String, Object>> resultSet);
}
