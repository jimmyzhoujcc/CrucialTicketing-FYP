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
public class Application {
    private int applicationId;
    private String applicationName;
    private boolean protectedFlag;
    private ActiveFlag activeFlag;

    public Application() {
    }

    public Application(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public boolean isProtectedFlag() {
        return protectedFlag;
    }

    public void setProtectedFlag(boolean protectedFlag) {
        this.protectedFlag = protectedFlag;
    }

    public ActiveFlag getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(ActiveFlag activeFlag) {
        this.activeFlag = activeFlag;
    }

   
}
