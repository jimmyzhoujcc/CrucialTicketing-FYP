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
public class UserRequest {
    private int userRequestId;
    private User user;
    private User requestor;
    
    public UserRequest() {
        this.user = new User();
        this.requestor = new User();
    }
    
    public UserRequest(User user, User requestor) {
        this.user = user;
        this.requestor = requestor;
    }

    public int getUserRequestId() {
        return userRequestId;
    }

    public void setUserRequestId(int userRequestId) {
        this.userRequestId = userRequestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getRequestor() {
        return requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    
    
    
}
