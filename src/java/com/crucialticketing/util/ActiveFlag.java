/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.util;

/**
 *
 * @author DanFoley
 */
public enum ActiveFlag {

    INCOMPLETE(-2),
    UNPROCESSED(-1),
    OFFLINE(0),
    ONLINE(1);

    private final int activeFlag;

    private ActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public int getActiveFlag() {
        return activeFlag;
    }
 
}
