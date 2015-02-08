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
public class UserRoleConnection {
    private int userRoleConnectionId;
    private User user;
    private Role role;
    private int validFrom;
    private int validTo;
    private int added;
    
    public UserRoleConnection() {}

    public UserRoleConnection(int userRoleConnectionId, User user, Role role, int validFrom, int validTo, int added) {
        this.userRoleConnectionId = userRoleConnectionId;
        this.user = user;
        this.role = role;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.added = added;
    }

    public int getUserRoleConnectionId() {
        return userRoleConnectionId;
    }

    public void setUserRoleConnectionId(int userRoleConnectionId) {
        this.userRoleConnectionId = userRoleConnectionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(int validFrom) {
        this.validFrom = validFrom;
    }

    public int getValidTo() {
        return validTo;
    }

    public void setValidTo(int validTo) {
        this.validTo = validTo;
    }

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }
    
    
    
    
}
