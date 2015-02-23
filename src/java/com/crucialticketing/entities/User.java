/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.List;

/**
 *
 * @author Daniel Foley
 */
public class User {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private Secure secure;
    private String emailAddress;
    private String contact;
    private List<UserRoleCon> userRoleConList;
    private int activeFlag;

    public User() {
        this.secure = new Secure();
    }

    public User(int userId) {
        this.secure = new Secure();
        this.userId = userId;
    }
    
    public User(int userId, String firstName, String lastName) {
        this.secure = new Secure();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Secure getSecure() {
        return secure;
    }

    public void setSecure(Secure secure) {
        this.secure = secure;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<UserRoleCon> getUserRoleConList() {
        return userRoleConList;
    }

    public void setUserRoleConList(List<UserRoleCon> userRoleConList) {
        this.userRoleConList = userRoleConList;
    }
    
    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    
}
