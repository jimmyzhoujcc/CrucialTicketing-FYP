/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.TicketLockRequest;
import com.crucialticketing.entities.User;
import com.crucialticketing.services.TicketLockRequestService;
import com.crucialticketing.services.TicketService;
import com.crucialticketing.services.UserAlertService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    DataSource dataSource;

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

        JdbcTemplate con = new JdbcTemplate(dataSource);
        TicketService ticketService = new TicketService();
        ticketService.setCon(con);

        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true);

        if (ticket.getTicketId() == null) {
            map.addAttribute("alert", "No ticket was found with that ID, please check and try again");
            map.addAttribute("page", "main/queryticket.jsp");
            return "mainview";
        }

        map.addAttribute("message", "this is a test message");

        map.put("ticketObject", ticket);
        map.addAttribute("page", "main/ticket.jsp");

        map.addAttribute("editMode", false);
        return "mainview";
    }

    @RequestMapping(value = "/update/editticket/", method = RequestMethod.POST)
    public String editTicket(@RequestParam(value = "ticketid", required = true) String ticketId,
            HttpServletRequest request,
            ModelMap map) {

        JdbcTemplate con = new JdbcTemplate(dataSource);
        TicketLockRequestService ticketLockRequest = new TicketLockRequestService();
        ticketLockRequest.setCon(con);

         User user = (User) request.getSession().getAttribute("user");
         
        if (ticketLockRequest.ticketOpenForEditByUser(
                Integer.valueOf(ticketId),
                user.getUserId())) {
            map.addAttribute("editMode", true);
        } else {
            ticketLockRequest.addTicketLockRequest(
                    Integer.valueOf(ticketId),
                    user.getUserId());
            
            UserAlertService userAlertService = new UserAlertService();
            userAlertService.setCon(con);
            userAlertService.insertUserAlert(user.getUserId(), Integer.valueOf(ticketId), "(" + ticketId + ") Access requested");

            map.addAttribute("editMode", false);
        }

        TicketService ticketService = new TicketService();
        ticketService.setCon(con);

        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true);
        map.put("ticketObject", ticket);
        map.addAttribute("page", "main/ticket.jsp");

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

        JdbcTemplate con = new JdbcTemplate(dataSource);

        // Checks if this ticket is open for editing by this user
        TicketService ticketService = new TicketService();
        ticketService.setCon(con);

        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true);

        // Checks if the description changed
        if (!oldShortDescription.equals(newShortDescription)) {

        }

        // Checks if the status has changed
        if (newStatus.length() > 0) {
            // Check if the status is a legal status
        }

        // Checks if a log entry has been made
        if (logEntry.length() > 0) {

        }
        /*
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
         */
        return "mainview";
    }

}
