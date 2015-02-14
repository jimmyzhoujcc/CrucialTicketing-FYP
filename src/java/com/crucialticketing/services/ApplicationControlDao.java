/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.ApplicationControl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ApplicationControlDao extends DatabaseService {
    public ApplicationControl getApplicationControlById(int ticketId, boolean populateWorkflowMap);
    
    public ApplicationControl getApplicationControlByCriteria(int ticketTypeId, int applicationId, int severityId, boolean populateWorkflowMap);
    
    public List<ApplicationControl> getApplicationControlList(boolean populateWorkflowMap);
    
    public List<ApplicationControl> rowMapper(List<Map<String, Object>> resultSet, boolean populateWorkflowMap);
}
