/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DanFoley
 */
public class Lock {
    List<LockEntry> lockEntryList;
    
    public Lock() {
        lockEntryList = new ArrayList<>();
    }
    
    public void addLockEntry(LockEntry lockEntry) {
        lockEntryList.add(lockEntry);
    }
    
    public void removeLockEntry(LockEntry lockEntry) {
        lockEntryList.remove(lockEntry);
    }

    public List<LockEntry> getLockEntryList() {
        return lockEntryList;
    }

    public void setLockEntryList(List<LockEntry> lockEntryList) {
        this.lockEntryList = lockEntryList;
    }
    
    
    
    
    
    
    
}
