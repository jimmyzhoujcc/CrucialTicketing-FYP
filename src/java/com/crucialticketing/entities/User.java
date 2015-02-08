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
    private String firstName;
    private String lastName;
    private Login login;
    private List<UserRoleConnection> roleList;

    public User() {
    }

    public User(int userId) {
        this.userId = userId;
    }
    
    public User(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public User(Login login, int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public User(Login login) {
        this.login = login;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<UserRoleConnection> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<UserRoleConnection> roleList) {
        this.roleList = roleList;
    }

    public boolean hasRole(String role) {
        for (UserRoleConnection roleCon : roleList) {
            if(roleCon.getRole().getRoleName().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
