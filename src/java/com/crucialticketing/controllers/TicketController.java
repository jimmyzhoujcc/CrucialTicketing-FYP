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
import com.crucialticketing.daos.services.ChangeLogService;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.daos.services.SeverityService;
import com.crucialticketing.daos.services.TicketLockRequestService;
import com.crucialticketing.daos.services.TicketLogService;
import com.crucialticketing.daos.services.TicketService;
import com.crucialticketing.daos.services.TicketTypeService;
import com.crucialticketing.daos.services.UserAlertService;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
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
    ChangeLogService changeLogService;

    @Autowired
    TicketLogService ticketLogService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    UserRoleConService userRoleConService;

    @RequestMapping(value = "/update/ticketquery/", method = RequestMethod.GET)
    public String queryTicket(ModelMap map) {
        map.addAttribute("page", "main/queryticket.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/create/preticket/", method = RequestMethod.GET)
    public String preTicketCreation(ModelMap map) {

        map.addAttribute("ticketTypeList", ticketTypeService.getTicketTypeList());

        map.addAttribute("applicationList", applicationService.getApplicationList());

        map.addAttribute("severityList", severityService.getSeverityList());

        map.addAttribute("page", "main/createticketselection.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/create/createticket/", method = RequestMethod.POST)
    public String ticketCreation(HttpServletRequest request,
            @RequestParam(value = "ticketTypeId", required = true) String ticketTypeId,
            @RequestParam(value = "severityId", required = true) String severityId,
            @RequestParam(value = "applicationId", required = true) String applicationId,
            ModelMap map) {

        // Pre checks
        if (!ticketTypeService.doesTicketTypeExist(Integer.valueOf(ticketTypeId))) {
            map.addAttribute("alert", "This ticket type no longer exists");
            return preTicketCreation(map);
        }

        if (!severityService.doesSeverityExist(Integer.valueOf(severityId))) {
            map.addAttribute("alert", "This severity no longer exists");
            return preTicketCreation(map);
        }

        if (!applicationService.doesApplicationExist(Integer.valueOf(applicationId))) {
            map.addAttribute("alert", "This application no longer exists");
            return preTicketCreation(map);
        }

        ApplicationControl applicationControl = applicationControlService.getApplicationControlByCriteria(
                Integer.valueOf(ticketTypeId),
                Integer.valueOf(applicationId),
                Integer.valueOf(severityId),
                true);

        if (applicationControl.getApplicationControlId() == 0) {
            map.addAttribute("alert", "A workflow is not currently setup for this configuration");
            return preTicketCreation(map);
        }

        User user = (User) request.getSession().getAttribute("user");

        // Checks if correct role is maintained 
        if (!userRoleConService.doesConExist(user.getUserId(),
                roleService.getRoleByRoleName(
                        "MAINT_"
                        + ticketTypeId + "_TICKET_"
                        + applicationId).getRoleId())) {
            // Not allowed
            map.addAttribute("alert", "You do not have the correct role privledges to perform this operation");
            return preTicketCreation(map);
        }

        map.addAttribute("userList", userService.getUserList(false));

        Ticket ticket = new Ticket();
        ticket.setApplicationControl(applicationControl);
        ticket.setCurrentWorkflowStep(applicationControl.getWorkflow().getWorkflowMap().getWorkflow().get(0));
        map.addAttribute("ticketObject", ticket);
        map.addAttribute("page", "main/createticket.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/update/viewticket/", method = RequestMethod.POST)
    public String viewTicket(@RequestParam(value = "ticketid", required = true) String ticketId, ModelMap map) {
        if (ticketId.length() == 0) {
            map.addAttribute("alert", "No search criteria was provided");
            map.addAttribute("page", "main/queryticket.jsp");
            return "mainview";
        }

        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);

        if (ticket.getTicketId() == 0) {
            map.addAttribute("alert", "No ticket was found with that ID, please check and try again");
            map.addAttribute("page", "main/queryticket.jsp");
            return "mainview";
        }

        map.put("ticketObject", ticket);

        map.addAttribute("page", "main/closedticket.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/update/editticket/", method = RequestMethod.POST)
    public String editTicket(@RequestParam(value = "ticketid", required = true) String ticketId,
            HttpServletRequest request,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (ticketLockRequestService.ticketOpenForEditByUser(
                Integer.valueOf(ticketId),
                user.getUserId())) {
            map.addAttribute("severityList", severityService.getSeverityList());
            map.addAttribute("page", "main/openticket.jsp");
        } else {
            ticketLockRequestService.addTicketLockRequest(
                    Integer.valueOf(ticketId),
                    user.getUserId());

            userAlertService.insertUserAlert(user.getUserId(), "(" + ticketId + ") Access requested");

            map.addAttribute("page", "main/closedticket.jsp");
        }

        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);
        map.put("ticketObject", ticket);

        return "mainview";
    }

    @RequestMapping(value = "/create/saveticket/", method = RequestMethod.POST)
    public String saveNewTicket(HttpServletRequest request,
            @RequestParam(value = "shortdescription", required = true) String shortDescription,
            @RequestParam(value = "applicationControlId", required = true) String applicationControlId,
            @RequestParam(value = "ticketTypeId", required = true) String ticketTypeId,
            @RequestParam(value = "applicationId", required = true) String applicationId,
            @RequestParam(value = "severityId", required = true) String severityId,
            @RequestParam(value = "newstatus", required = true) String newStatus,
            @RequestParam(value = "logentry", required = true) String logEntry,
            @ModelAttribute("uploadfilelist") UploadedFileLog uploadedFileLog,
            ModelMap map) {

        // Checks if the IDs provided match the application control ID details from DB
        // This provides a level of security with the application control ID being correct
        ApplicationControl applicationControl = applicationControlService.getApplicationControlById(Integer.valueOf(applicationControlId), true);

        if ((applicationControl.getTicketType().getTicketTypeId() != Integer.valueOf(ticketTypeId))
                || (applicationControl.getApplication().getApplicationId() != Integer.valueOf(applicationId))
                || (applicationControl.getSeverity().getSeverityId() != Integer.valueOf(severityId))) {
            map.addAttribute("alert", "Configuration details received did not match expected");
            map.addAttribute("page", "main/createticketselection.jsp");
            return "mainview";
        }

        // Checks if the status move is legal
        int currentStatusId;

        if (newStatus.isEmpty()) {
            currentStatusId = applicationControl.getWorkflow().getWorkflowMap()
                    .getWorkflow().get(0).getStatus().getStatusId();
        } else {
            currentStatusId = Integer.valueOf(newStatus);
        }

        WorkflowStep workflowStep = applicationControl.getWorkflow().getWorkflowMap().getWorkflow().get(0);
        if ((workflowStep.getStatus().getStatusId() != currentStatusId) && (!workflowStep.isLegalStep(currentStatusId))) {
            map.addAttribute("alert", "Status conflicts with workflow configuration");
            map.addAttribute("page", "main/createticketselection.jsp");
            return "mainview";
        }

        User user = (User) request.getSession().getAttribute("user");

        // Checks if correct role is maintained 
        if (!userRoleConService.doesConExist(user.getUserId(),
                roleService.getRoleByRoleName(
                        "MAINT_"
                        + applicationControl.getTicketType().getTicketTypeId()
                        + "_TICKET_"
                        + applicationControl.getApplication().getApplicationId()).getRoleId())) {
            map.addAttribute("alert", "You do not have the correct role privledges to perform this operation");
            map.addAttribute("page", "main/createticketselection.jsp");
            return "mainview";
        }

        int ticketId = ticketService.insertTicket(
                shortDescription,
                applicationControl.getApplicationControlId(),
                user.getUserId(),
                user.getUserId(),
                currentStatusId);

        if (ticketId == 0) {
            map.addAttribute("alert", "The ticket could not be saved");
            map.addAttribute("page", "main/createticketselection.jsp");
            return "mainview";
        }

        // Adds change log entry
        changeLogService.addChangeLogEntry(
                ticketId,
                applicationControl.getApplicationControlId(),
                user.getUserId(),
                applicationControl.getWorkflow().getWorkflowMap()
                .getWorkflow().get(0).getStatus().getStatusId());

        if (currentStatusId != applicationControl.getWorkflow().getWorkflowMap()
                .getWorkflow().get(0).getStatus().getStatusId()) {
            changeLogService.addChangeLogEntry(
                    ticketId,
                    applicationControl.getApplicationControlId(),
                    user.getUserId(),
                    currentStatusId);
        }
        // Ticket log

        // Checks if a log entry has been made
        if (logEntry.length() > 0) {
            logEntry = logEntry.replaceAll("(\r\n|\n)", "<br />");
            ticketLogService.addTicketLog(ticketId, user.getUserId(), logEntry);
        }

        // Complete any uploads
        this.fileUploadHandler(request, uploadedFileLog, Integer.valueOf(ticketId), user);

        map.put("ticketObject", ticketService.getTicketById(ticketId, true, true, true, true));
        map.addAttribute("page", "main/closedticket.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/update/saveticket/", method = RequestMethod.POST)
    public String saveExistingTicket(HttpServletRequest request,
            @RequestParam(value = "ticketid", required = true) String ticketId,
            @RequestParam(value = "old_shortdescription", required = true) String oldShortDescription,
            @RequestParam(value = "new_shortdescription", required = true) String newShortDescription,
            @RequestParam(value = "severity", required = true) String severity,
            @RequestParam(value = "newstatus", required = true) String newStatus,
            @RequestParam(value = "logentry", required = true) String logEntry,
            @ModelAttribute("uploadfilelist") UploadedFileLog uploadedFileLog,
            ModelMap map) {

        // Checks if this ticket is open for editing by this user
        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);

        User user = (User) request.getSession().getAttribute("user");

        // Checks if correct role is maintained 
        // Checks if correct role is maintained 
        if (!userRoleConService.doesConExist(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_"
                        + ticket.getApplicationControl().getTicketType().getTicketTypeId() + "_TICKET_"
                        + ticket.getApplicationControl().getApplication().getApplicationId()).getRoleId())) {
            map.put("ticketObject", ticket);
            map.addAttribute("page", "main/closedticket.jsp");
            map.addAttribute("alert", "You do not have the correct role privledges to perform a save operation");
            return "mainview";
        }

        // Checks if this user has a open ticket lock
        if (!ticketLockRequestService.ticketOpenForEditByUser(Integer.valueOf(ticketId), user.getUserId())) {
            map.put("ticketObject", ticket);
            map.addAttribute("page", "main/closedticket.jsp");
            map.addAttribute("alert", "You do not have exclusive rights to save this ticket");
            return "mainview";
        }

        boolean changeLogEntryRequired = false;
        boolean severityChange = false;

        if (Integer.valueOf(severity) != ticket.getApplicationControl().getSeverity().getSeverityId()) {
            severityChange = true;

            ApplicationControl applicationControl = applicationControlService.getApplicationControlByCriteria(
                    ticket.getApplicationControl().getTicketType().getTicketTypeId(),
                    ticket.getApplicationControl().getApplication().getApplicationId(),
                    Integer.valueOf(severity),
                    false);

            if (applicationControl.getApplicationControlId() == 0) {
                map.put("ticketObject", ticket);
                map.addAttribute("page", "main/closedticket.jsp");
                map.addAttribute("alert", "A workflow is not currently setup for this configuration");
                return "mainview";
            }

            ticketService.updateApplicationControl(ticket.getTicketId(), applicationControl.getApplicationControlId());
            ticketService.updateStatus(ticket.getTicketId(),
                    ticket.getApplicationControl()
                    .getWorkflow()
                    .getWorkflowMap()
                    .getWorkflow()
                    .get(0).getNextWorkflowStep()
                    .get(0).getStatus().getStatusId());

            ticketLogService.addTicketLog(ticket.getTicketId(), user.getUserId(),
                    "Ticket severity has been changed from "
                    + ticket.getApplicationControl().getSeverity().getSeverityLevel() + ":"
                    + ticket.getApplicationControl().getSeverity().getSeverityName()
                    + " to "
                    + applicationControl.getSeverity().getSeverityLevel() + ":"
                    + applicationControl.getSeverity().getSeverityName());

            ticketLogService.addTicketLog(ticket.getTicketId(), 0, "Ticket status reset to "
                    + ticket.getApplicationControl()
                    .getWorkflow()
                    .getWorkflowMap()
                    .getWorkflow()
                    .get(0).getNextWorkflowStep()
                    .get(0).getStatus().getStatusName());

            changeLogEntryRequired = true;
        }

        // Checks if the status has changed
        if (!severityChange) {
            if (newStatus.length() > 0) {
                // Checks legality of the status move
                WorkflowStep workflowStep = ticket.getCurrentWorkflowStep();

                if (!workflowStep.isLegalStep(Integer.valueOf(newStatus))) {
                    map.put("ticketObject", ticket);
                    map.addAttribute("page", "main/closedticket.jsp");
                    map.addAttribute("alert", "Cannot move ticket to status selected, illegal move");
                    return "mainview";
                } else {
                    WorkflowStatus workflowStatus = ticket.getApplicationControl()
                            .getWorkflow()
                            .getWorkflowMap()
                            .getWorkflowStageByStatus(Integer.valueOf(newStatus))
                            .getStatus();

                    ticketService.updateStatus(ticket.getTicketId(), workflowStatus.getStatusId());

                    ticketLogService.addTicketLog(ticket.getTicketId(), user.getUserId(), "Ticket status has been changed to "
                            + workflowStatus.getStatusName());

                    changeLogEntryRequired = true;
                }
            }
        }

        // Checks if the description changed
        if (!oldShortDescription.equals(newShortDescription)) {
            ticketService.updateDescription(Integer.valueOf(ticketId), newShortDescription);
        }

        // Checks if a log entry has been made
        if (logEntry.length() > 0) {
            logEntry = logEntry.replaceAll("(\r\n|\n)", "<br />");
            ticketLogService.addTicketLog(Integer.valueOf(ticketId), user.getUserId(), logEntry);
        }

        // Complete any uploads
        this.fileUploadHandler(request, uploadedFileLog, Integer.valueOf(ticketId), user);

        // Grabs fresh version of ticket from DB
        ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);

        // Adds change log entry if required
        if (changeLogEntryRequired) {
            changeLogService.addChangeLogEntry(
                    ticket.getTicketId(),
                    ticket.getApplicationControl().getApplicationControlId(),
                    user.getUserId(),
                    ticket.getCurrentWorkflowStep().getStatus().getStatusId());
        }

        ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);

        map.put("ticketObject", ticket);
        map.addAttribute("page", "main/closedticket.jsp");

        return "mainview";
    }

    private void fileUploadHandler(
            HttpServletRequest request,
            UploadedFileLog uploadedFileLog,
            int ticketId,
            User user) {
        try {
            for (UploadedFile uploadedFile : uploadedFileLog.getFiles()) {

                if (!uploadedFile.getFile().isEmpty()) {
                    byte[] bytes = uploadedFile.getFile().getBytes();

                    String fileName = uploadedFile.getFile().getOriginalFilename();

                    String extension = "unknown";

                    int i = fileName.lastIndexOf('.');

                    if (i > 0) {
                        extension = fileName.substring(i + 1);
                        fileName = fileName.substring(0, i);
                    }

                    String saveFile = request.getServletContext().getRealPath("/WEB-INF/upload/" + ticketId + "/" + fileName + "." + extension);

                    File fileDirectory = new File(request.getServletContext().getRealPath("/WEB-INF/upload/" + ticketId + "/"));

                    if (!fileDirectory.exists()) {
                        fileDirectory.mkdir();
                    }

                    try (FileOutputStream output = new FileOutputStream(new File(saveFile))) {
                        output.write(bytes);

                        attachmentService.insertAttachment(
                                ticketId,
                                user.getUserId(), fileName + "." + extension,
                                uploadedFile.getName(),
                                uploadedFile.getDescription());
                    }
                }
            }
        } catch (Exception e) {

        }
    }

}
