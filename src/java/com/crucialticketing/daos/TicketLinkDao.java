/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.TicketLink;
import java.util.List;

/**
 *
 * @author DanFoley
 */
public interface TicketLinkDao {
    public void insertTicketLink(TicketLink ticketLink);

    public List<TicketLink> getTicketLink(int ticketId);
    
    public boolean doesTicketLinkExist(int fromTicketId, int toTicketId);
}
