/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface TicketDao {

    public int insertTicket(final Ticket ticket);

    public Ticket getTicketById(int ticketId, 
            boolean popTicketLog, boolean popAttachments, 
            boolean popTicketLinks, boolean populateAllChangeLog, 
            boolean popSLA);

    public List<Ticket> getListByCriteria(
            ArrayList<String> ticketList,
            ArrayList<String> ticketTypeList,
            ArrayList<String> applicationList,
            ArrayList<String> severityList,
            ArrayList<String> workflowList,
            ArrayList<String> workflowStatusList,
            ArrayList<String> queueList,
            ArrayList<String> reportedByUserList,
            ArrayList<String> createdByUserList,
            ArrayList<String> lastUpdatedByUserList,
            String dateCreatedFrom, String dateCreatedTo,
            String dateLastUpdatedFrom, String dateLastUpdatedTo);

    public void updateDescription(int ticketId, String newDescription);

    public boolean doesTicketExist(int ticketId);
}
