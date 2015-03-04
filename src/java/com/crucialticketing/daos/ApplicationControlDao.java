/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ApplicationControlDao {

    public int insertApplicationControl(ApplicationControl applicationControl, Ticket ticket, User requestor);

    public ApplicationControl getApplicationControlById(int applicationControlId, boolean populateWorkflowMap);

    public boolean doesApplicationControlExist(int ticketTypeId, int applicationId, int severityId);

    public boolean doesApplicationControlExistInOnline(int ticketTypeId, int applicationId, int severityId);

    public boolean doesApplicationControlExistInOnlineOrOffline(int ticketTypeId, int applicationId, int severityId);

     public List<ApplicationControl> getIncompleteApplicationControlList(boolean populateWorkflowMap);

    public List<ApplicationControl> getUnprocessedApplicationControlList(boolean populateWorkflowMap);

    public List<ApplicationControl> getOnlineApplicationControlList(boolean populateWorkflowMap);

    public List<ApplicationControl> getOfflineApplicationControlList(boolean populateWorkflowMap);

    public void updateToUnprocessed(int applicationControlId, Ticket ticket, User requestor);

    public void updateToOnline(int applicationControlId, Ticket ticket, User requestor);

    public void updateToOffline(int applicationControlId, Ticket ticket, User requestor);

    public List<ApplicationControl> rowMapper(List<Map<String, Object>> resultSet, boolean populateWorkflowMap);
}
