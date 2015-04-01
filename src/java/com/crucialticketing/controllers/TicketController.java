/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.UploadedFile;
import com.crucialticketing.entities.UploadedFileLog;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStep;
import com.crucialticketing.daos.services.ApplicationControlService;
import com.crucialticketing.daos.services.ApplicationService;
import com.crucialticketing.daos.services.AttachmentService;
import com.crucialticketing.daos.services.TicketChangeLogService;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.daos.services.SeverityService;
import com.crucialticketing.daos.services.TicketLockRequestService;
import com.crucialticketing.daos.services.TicketLogService;
import com.crucialticketing.daos.services.TicketService;
import com.crucialticketing.daos.services.TicketTypeService;
import com.crucialticketing.daos.services.UserAlertService;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.entities.Role;
import com.crucialticketing.util.Validation;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    ApplicationControlService applicationControlService;

    @Autowired
    TicketTypeService ticketTypeService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    SeverityService severityService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    TicketLockRequestService ticketLockRequestService;

    @Autowired
    UserAlertService userAlertService;

    @Autowired
    TicketChangeLogService changeLogService;

    @Autowired
    TicketLogService ticketLogService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    UserRoleConService userRoleConService;


    

    

    

    @RequestMapping(value = "/update/editticket/", method = RequestMethod.POST)
    public String editTicket(@RequestParam(value = "ticketid", required = true) String ticketId,
            HttpServletRequest request,
            ModelMap map) {

        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket ID");
            return "mainview";
        } else if (ticketId.isEmpty()) {
            Validation.inputNotProvided(map, "Ticket ID");
            return "mainview";
        }

        User user = (User) request.getSession().getAttribute("user");

/*        if (ticketLockRequestService.ticketOpenForEditByUser(
                Integer.valueOf(ticketId),
                user.getUserId())) {
            map.addAttribute("severityList", severityService.getOnlineList());
            map.addAttribute("page", "main/openticket.jsp");
        } else {
            ticketLockRequestService.addTicketLockRequest(
                    Integer.valueOf(ticketId),
                    user.getUserId());

            map.addAttribute("page", "main/closedticket.jsp");
        }*/

       // Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);
        //map.put("ticketObject", ticket);

        return "mainview";
    }

   
   

    

}
