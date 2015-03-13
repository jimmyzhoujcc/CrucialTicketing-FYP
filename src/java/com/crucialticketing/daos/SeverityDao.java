/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface SeverityDao {
    
    public int insertSeverity(Severity queue, Ticket ticket, User requestor);

    public Severity getSeverityById(int severityId);

    public boolean doesSeverityExistByLevel(int severityLevel);

    public boolean doesSeverityExistInOnlineById(int severityId);

    public boolean doesSeverityExistInOnlineByLevel(int severityLevel);

    public boolean doesSeverityExistInOnlineOrOfflineByLevel(int severityLevel);

    public List<Severity> getIncompleteList();

    public List<Severity> getUnprocessedList();

    public List<Severity> getOnlineList();

    public List<Severity> getOfflineList();

    public void updateToUnprocessed(int severityId, Ticket ticket, User requestor);

    public void updateToOnline(int severityId, Ticket ticket, User requestor);

    public void updateToOffline(int severityId, Ticket ticket, User requestor);

    public void removeSeverity(int severityId);
    
    public List<Severity> rowMapper(List<Map<String, Object>> resultSet);
}
