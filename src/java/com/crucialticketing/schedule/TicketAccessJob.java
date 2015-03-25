/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.schedule;

import com.crucialticketing.entities.TicketLockRequest;
import com.crucialticketing.daos.services.TicketLockRequestService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author DanFoley
 */
@Component
public class TicketAccessJob {

    @Autowired
    TicketLockRequestService ticketLockRequestService;
    /**
     *
     */

    public void executeTicketAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;
        
        List<TicketLockRequest> ticketLockRequestList = ticketLockRequestService.getOpenRequestList();

        for (TicketLockRequest ticketLockRequest : ticketLockRequestList) {
            found = false;
            for(int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == ticketLockRequest.getTicketId()) {
                    found = true;
                }
            }

            if(found) {
                ticketLockRequestService.denyAccess(ticketLockRequest.getTicketId(), ticketLockRequest.getUserId());
            } else {
                processedList.add(ticketLockRequest.getTicketId());
                ticketLockRequestService.grantAccess(ticketLockRequest.getTicketId(), ticketLockRequest.getUserId()); 
            }
        }
    }
    
    
}
