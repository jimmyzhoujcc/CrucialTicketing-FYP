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
public class TicketLog {
    List<TicketLogEntry> entryList;
    
    public TicketLog() {
        entryList = new ArrayList<>();
    }

    public TicketLog(List<TicketLogEntry> entryList) {
        entryList = new ArrayList<>();
        this.entryList = entryList;
    }
    
    public void addEntry(int ticketLogId, User user, String logEntry, int stamp) {
        entryList.add(new TicketLogEntry(ticketLogId, user, logEntry, stamp));
    }
    
    public List<TicketLogEntry> getEntryList() {
        return this.entryList;
    }
    
    
}
