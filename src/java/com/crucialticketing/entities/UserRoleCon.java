/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import com.crucialticketing.util.ActiveFlag;

/**
 *
 * @author DanFoley
 */
public class UserRoleCon {
    private int userRoleConId;
    private User user;
    private Role role;
    private int validFrom;
    private String validFromStr;
    private int validTo;
    private String validToStr;
    
    private ActiveFlag activeFlag;

    public UserRoleCon() {
        this.user = new User();
        this.role = new Role();
    }

    public UserRoleCon(User user, Role role, int validFrom, int validTo, ActiveFlag activeFlag) {
        this.user = user;
        this.role = role;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.activeFlag = activeFlag;
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

    public String getValidFromStr() {
        return validFromStr;
    }

    public void setValidFromStr(String validFromStr) {
        this.validFromStr = validFromStr;
    }

    public int getValidTo() {
        return validTo;
    }

    public void setValidTo(int validTo) {
        this.validTo = validTo;
    }

    public String getValidToStr() {
        return validToStr;
    }

    public void setValidToStr(String validToStr) {
        this.validToStr = validToStr;
    }

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }

  
    
}
