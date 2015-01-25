/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.TicketLogEntry;
import com.crucialticketing.entities.User;
import com.crucialticketing.services.TicketLogService;
import com.crucialticketing.services.TicketService;
import com.crucialticketing.services.WorkflowStatusService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    
    @Autowired
    TicketLogService ticketLogService;

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

        map.addAttribute("message", "this is a test message");
        
        map.put("ticketObject", (Ticket) objectList.get(0));
        map.addAttribute("page", "main/ticket.jsp");

        map.addAttribute("editMode", false);
        return "mainview";
    }

    @RequestMapping(value = "/update/editticket/", method = RequestMethod.POST)
    public String editTicket(@RequestParam(value = "ticketid", required = true) String ticketId, ModelMap map) {

        List<Object> objectList = ticketService.select("ticket_id", ticketId);

        Ticket ticket = (Ticket) objectList.get(0);

        //if (ticket.isLock()) {
            map.addAttribute("alert", "Ticket is currently locked, please try again later");
            map.addAttribute("page", "main/ticket.jsp");
            map.addAttribute("editMode", false);
       // } else {
            ticketService.update("ticket_id", ticketId, "lock", "1");
            map.addAttribute("alert", "You are now in edit mode, please save ticket to exit and confirm changes");
            map.addAttribute("page", "main/ticket.jsp");
            map.addAttribute("editMode", true);
       // }

        map.put("ticketObject", (Ticket) objectList.get(0));

        return "mainview";
    }

    @RequestMapping(value = "/update/saveticket/", method = RequestMethod.POST)
    public String saveTicket(HttpServletRequest request,
            @RequestParam(value = "ticketid", required = true) String ticketId,
            @RequestParam(value = "old_shortdescription", required = true) String oldShortDescription,
            @RequestParam(value = "new_shortdescription", required = true) String newShortDescription,
            @RequestParam(value = "newstatus", required = true) String newStatus,
            @RequestParam(value = "logentry", required = true) String logEntry,
            ModelMap map) {

        List<Object> objectList = ticketService.select("ticket_id", ticketId);
        Ticket ticket = (Ticket) objectList.get(0);

        // Checking description has changed
        if (!oldShortDescription.equals(newShortDescription)) {
            User userInControl = (User)request.getSession().getAttribute("user");
            ticketService.update("ticket_id", ticketId, "short_description", newShortDescription);
            TicketLogEntry ticketLogEntry = new TicketLogEntry(Integer.valueOf(ticketId), -1, new User(4, "System", "Message"), "Short description was changed by ("+userInControl.getUserId() + ") "+userInControl.getFirstName() + " "+userInControl.getLastName(), -1);
            ticketLogService.insert(ticketLogEntry);
        }

        // Checking if a new status has been selected
        if (newStatus.length() > 0) {
            ticketService.update("ticket_id", ticketId, "current_status_id", newStatus);
        }
        // Checking if a log entry has been added

        ticketService.update("ticket_id", ticketId, "lock", "0");
        
        objectList = ticketService.select("ticket_id", ticketId);
        ticket = (Ticket) objectList.get(0);

        map.put("ticketObject", ticket);
        map.addAttribute("page", "main/ticket.jsp");
        map.addAttribute("editMode", false);

        return "mainview";
    }

}
