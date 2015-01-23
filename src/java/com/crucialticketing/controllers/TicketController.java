/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.services.TicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @RequestMapping(value = "/update/viewticket/", method = RequestMethod.POST)
    public String viewTicket(@RequestParam(value = "ticketid", required = true) String ticketId, ModelMap map) {
        if (ticketId.length() == 0) {
            map.addAttribute("alert", "No search criteria was provided");
            map.addAttribute("page", "main/queryticket.jsp");
            return "mainview";
        }

        List<Object> objectList = ticketService.select("ticket_id", ticketId);

        Ticket ticket = (Ticket) objectList.get(0);

        if (ticket.getTicketId() == null) {
            map.addAttribute("alert", "No ticket was found with that ID, please check and try again");
            map.addAttribute("page", "main/queryticket.jsp");
            return "mainview";
        }

        map.put("ticketObject", (Ticket) objectList.get(0));
        map.addAttribute("page", "main/ticket.jsp");

        return "mainview";
    }
}
