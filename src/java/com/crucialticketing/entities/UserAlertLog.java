/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.List;

/**
 *
 * @author DanFoley
 */
public class UserAlertLog {
    private List<UserAlert> userAlertLog;
    private int lastUpdated;
    private int unread;
    
    public UserAlertLog() {}

    public UserAlertLog(List<UserAlert> userAlertLog, int lastUpdated) {
        this.userAlertLog = userAlertLog;
        this.lastUpdated = lastUpdated;
        recalculateUnread();
    }
    
    private void recalculateUnread() {
        unread = 0;
        for (UserAlert userAlert : userAlertLog) {
            if(!userAlert.isRead()) {
                unread++;
            }
        }
    }
    
    public void addUserAlert(UserAlert log) {
        userAlertLog.add(log);
        recalculateUnread();
    }
    
    public void removeUserAlert(UserAlert log) {
        userAlertLog.remove(log);
        recalculateUnread();
    }

    public List<UserAlert> getUserAlertLog() {
        return userAlertLog;
    }

    public void setUserAlertLog(List<UserAlert> userAlertLog) {
        this.userAlertLog = userAlertLog;
    }

    public int getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }
    
    
    
}
