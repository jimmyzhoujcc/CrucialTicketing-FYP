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
public class UserRoleCon {
    private int userRoleConId;
    private User user;
    private Role role;
    private int validFrom;
    private int validTo;

    public UserRoleCon() {
        this.user = new User();
        this.role = new Role();
    }

    public UserRoleCon(int userRoleConId, User user, Role role, int validFrom, int validTo) {
        this.userRoleConId = userRoleConId;
        this.user = user;
        this.role = role;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public int getUserRoleConId() {
        return userRoleConId;
    }

    public void setUserRoleConId(int userRoleConId) {
        this.userRoleConId = userRoleConId;
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
}
