/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.TicketLockRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface TicketLockRequestDao {

    public void addLockRequest(TicketLockRequest lockRequest);

    public boolean checkIfOpen(int ticketId, int requestorUserId);

    public boolean checkIfOutstanding(int ticketId, int requestorUserId);

    public void grantAccess(int lockRequestId);

    public void denyAccess(int lockRequestId, Ticket ticket, int requestorUserId);

    public List<TicketLockRequest> getOpenRequestList();

    public void closeRequest(int roleId, int requestorUserId);
}
