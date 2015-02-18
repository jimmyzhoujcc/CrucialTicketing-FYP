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
public class Role {
    private int roleId;
    private String roleName;
    private String roleDescription;
    private List<RoleChangeLog> roleChangeLog;
    
    public Role() {}

    public int getRoleId() {
        return roleId;
    }

    public Role(int roleId, String roleName, String roleDescription, List<RoleChangeLog> roleChangeLog) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.roleChangeLog = roleChangeLog;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public List<RoleChangeLog> getRoleChangeLog() {
        return roleChangeLog;
    }

    public void addRoleChange(int roleChangeId, User user, int stamp, int flag) {
        this.roleChangeLog.add(new RoleChangeLog(roleChangeId, user, stamp, flag));
    } 
}
