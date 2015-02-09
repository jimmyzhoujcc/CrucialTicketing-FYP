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
import com.crucialticketing.services.TicketLogService;
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

        User user = (User) request.getSession().getAttribute("user");

        // Checks if correct role is maintained 
        if (!user.hasRole("MAINT_"
                + ticket.getApplicationControl().getTicketType().getTicketTypeId() + "_TICKET_"
                + ticket.getApplicationControl().getApplication().getApplicationId())) {
            map.put("ticketObject", ticket);
            map.addAttribute("editMode", false);
            map.addAttribute("page", "main/ticket.jsp");
            map.addAttribute("alert", "You do not have the correct role privledges to perform a save operation");
            return "mainview";
        }

        // Checks if this user has a open ticket lock
        TicketLockRequestService ticketLockRequestService = new TicketLockRequestService();
        ticketLockRequestService.setCon(con);

        if (!ticketLockRequestService.ticketOpenForEditByUser(Integer.valueOf(ticketId), user.getUserId())) {
            map.put("ticketObject", ticket);
            map.addAttribute("editMode", false);
            map.addAttribute("page", "main/ticket.jsp");
            map.addAttribute("alert", "You do not have exclusive rights to save this ticket");
            return "mainview";
        }

        // Checks if the description changed
        if (!oldShortDescription.equals(newShortDescription)) {
            ticketService.updateDescription(Integer.valueOf(ticketId), newShortDescription);
        }

        // Checks if the status has changed
        if (newStatus.length() > 0) {
            // Check if the status is a legal status

            ticketService.updateStatus(Integer.valueOf(ticketId), Integer.valueOf(newStatus));
        }

        // Checks if a log entry has been made
        if (logEntry.length() > 0) {
            logEntry = logEntry.replaceAll("(\r\n|\n)", "<br />");
            TicketLogService ticketLogService = new TicketLogService();
            ticketLogService.setCon(con);
            ticketLogService.addTicketLog(Integer.valueOf(ticketId), user.getUserId(), logEntry);
        }

        ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true);

        map.put("ticketObject", ticket);
        map.addAttribute("page", "main/ticket.jsp");
        map.addAttribute("editMode", false);

        return "mainview";
    }

}
