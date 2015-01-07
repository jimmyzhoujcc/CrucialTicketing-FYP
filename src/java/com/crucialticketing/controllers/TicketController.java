/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Owner
 */
@Controller
public class TicketController {
    @Autowired
    TicketService ticketService;
    
    @RequestMapping(value = "/update/ticketquery/", method = RequestMethod.GET)
    public String queryTicket(ModelMap map) { 
        map.addAttribute("page", "main/queryticket.jsp");
        return "mainview";
    }
    
    @RequestMapping(value = "/update/viewticket/", method = RequestMethod.GET)
    public String viewTicket(@RequestParam(value = "ticketid", required = true) String ticketId, ModelMap map) {
        Ticket ticket = ticketService.getTicketById(ticketId);
       
        map.put("ticketObject", ticket);     
        map.addAttribute("page", "main/ticket.jsp");
        return "mainview";
    }
}
