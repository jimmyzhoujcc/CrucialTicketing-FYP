/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ApplicationDao {

    public int insertApplication(final Application application, Ticket ticket, User requestor);

    public Application getApplicationById(int applicationId);

    public boolean doesApplicationExist(String applicationName);

    public boolean doesApplicationExistInOnline(int applicationId);

    public boolean doesApplicationExistInOnline(String applicationName);

    public boolean doesApplicationExistInOnlineOrOffline(String applicationName);
    
    public List<Application> getIncompleteApplicationList();

    public List<Application> getUnprocessedApplicationList();

    public List<Application> getOnlineApplicationList();

    public List<Application> getOfflineApplicationList();

    public void updateToUnprocessed(int applicationId, Ticket ticket, User requestor);

    public void updateToOnline(int applicationId, Ticket ticket, User requestor);

    public void updateToOffline(int applicationId, Ticket ticket, User requestor);

    public void removeApplication(int applicationId);
    
    public List<Application> rowMapper(List<Map<String, Object>> resultSet);
}
