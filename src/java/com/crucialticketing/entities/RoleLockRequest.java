/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author DanFoley
 */
public class RoleLockRequest {

    private int lockRequestId;
    private Role role;
    private User requestor;
    private int requestTime;
    private int requestPassTime;

    public RoleLockRequest() {
        this.role = new Role();
        this.requestor = new User();
    }

    public RoleLockRequest(Role role, User requestor) {
        this.role = role;
        this.requestor = requestor;
    }

    public int getLockRequestId() {
        return lockRequestId;
    }

    public void setLockRequestId(int lockRequestId) {
        this.lockRequestId = lockRequestId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getRequestor() {
        return requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequestPassTime() {
        return requestPassTime;
    }

    public void setRequestPassTime(int requestPassTime) {
        this.requestPassTime = requestPassTime;
    }

}
