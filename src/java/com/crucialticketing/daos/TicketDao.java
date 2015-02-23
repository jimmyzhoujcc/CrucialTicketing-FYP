/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Ticket;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface TicketDao {
    public int insertTicket(String shortDescription, int applicationControlId, int createdByUserId, int reportedByUserId, int currentStatusId);
            
    public Ticket getTicketById(int ticketId, boolean popWorkflowMap, boolean popTicketLog, boolean popAttachments, boolean popChangeLog);
    
    public List<Ticket> getTicketList(boolean popWorkflowMap, boolean popTicketLog, boolean popAttachments, boolean popChangeLog);
    
    public void updateDescription(int ticketId, String newDescription);
    
    public void updateStatus(int ticketId, int newStatusId);
    
    public void updateApplicationControl(int ticketId, int applicationControlId);
    
    public boolean doesTicketExist(int ticketId);
    
    public List<Ticket> rowMapper(List<Map<String, Object>> resultSet, boolean popWorkflowMap, boolean popTicketLog, boolean popAttachments, boolean popChangeLog);
}
