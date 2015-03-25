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
public class ApplicationLockRequest {

    private int applicationLockRequestId;
    private Application application;
    private User requestor;
    private int requestTime;
    private int requestPassTime;

    public ApplicationLockRequest() {
        this.application = new Application();
        this.requestor = new User();
    }

    public ApplicationLockRequest(Application application, User requestor) {
        this.application = application;
        this.requestor = requestor;
    }

    public int getApplicationLockRequestId() {
        return applicationLockRequestId;
    }

    public void setApplicationLockRequestId(int applicationLockRequestId) {
        this.applicationLockRequestId = applicationLockRequestId;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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
