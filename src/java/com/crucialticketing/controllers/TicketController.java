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

    @RequestMapping(value = "/update/ticketquery/", method = RequestMethod.GET)
    public String queryTicket(ModelMap map) {
        map.addAttribute("page", "main/queryticket.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/create/preticket/", method = RequestMethod.GET)
    public String preTicketCreation(ModelMap map) {

        map.addAttribute("ticketTypeList", ticketTypeService.getTicketTypeList());

        map.addAttribute("applicationList", applicationService.getOnlineApplicationList());

        map.addAttribute("severityList", severityService.getOnlineList());

        map.addAttribute("page", "main/createticketselection.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/create/createticket/", method = RequestMethod.POST)
    public String ticketCreation(HttpServletRequest request,
            @RequestParam(value = "ticketTypeId", required = false) String ticketTypeId,
            @RequestParam(value = "severityId", required = false) String severityId,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            ModelMap map) {

        // Checks if fields are provided
        if ((ticketTypeId == null) || (severityId == null) || (applicationId == null)) {
            Validation.inputNotProvided(map, "Ticket Type, Severity or Application");
            return preTicketCreation(map);
        } else if ((ticketTypeId.isEmpty()) || (severityId.isEmpty()) || (applicationId.isEmpty())) {
            Validation.inputNotProvided(map, "Ticket Type, Severity or Application");
            return preTicketCreation(map);
        }

        // Pre checks
        if (!ticketTypeService.doesTicketTypeExist(Integer.valueOf(ticketTypeId))) {
            map.addAttribute("alert", "This ticket type no longer exists");
            return preTicketCreation(map);
        }

        if (!severityService.doesSeverityExistInOnlineById(Integer.valueOf(severityId))) {
            map.addAttribute("alert", "This severity no longer exists");
            return preTicketCreation(map);
        }

        if (!applicationService.doesApplicationExistInOnline(Integer.valueOf(applicationId))) {
            map.addAttribute("alert", "This application no longer exists");
            return preTicketCreation(map);
        }

        if (!applicationControlService.doesApplicationControlExistInOnline(
                Integer.valueOf(ticketTypeId),
                Integer.valueOf(applicationId),
                Integer.valueOf(severityId))) {
            map.addAttribute("alert", "An active ticket configuration does not exist");
            return preTicketCreation(map);
        }

        ApplicationControl applicationControl = applicationControlService.getApplicationControlByCriteria(
                Integer.valueOf(ticketTypeId),
                Integer.valueOf(applicationId),
                Integer.valueOf(severityId), true);

        User user = (User) request.getSession().getAttribute("user");

        // Checks if correct role is maintained 
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                applicationControl.getRole().getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return preTicketCreation(map);
        }

        map.addAttribute("userList", userService.getOnlineUserList(false));

        Ticket ticket = new Ticket();
        ticket.setApplicationControl(applicationControl);
        ticket.setCurrentWorkflowStep(applicationControl.getWorkflow().getWorkflowMap().getWorkflow().get(0));
        map.addAttribute("ticketObject", ticket);
        map.addAttribute("page", "main/createticket.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/update/viewticket/", method = RequestMethod.POST)
    public String viewTicket(@RequestParam(value = "ticketid", required = false) String ticketId, ModelMap map) {
        map.addAttribute("page", "main/queryticket.jsp");

        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket ID");
            return "mainview";
        } else if (ticketId.isEmpty()) {
            Validation.inputNotProvided(map, "Ticket ID");
            return "mainview";
        }

        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);

        if (ticket.getTicketId() == 0) {
            Validation.inputIsInvalid(map, "Ticket ID");
            return "mainview";
        }

        // Success
        map.put("ticketObject", ticket);
        map.addAttribute("page", "main/closedticket.jsp");
        return "mainview";
    }

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

        if (ticketLockRequestService.ticketOpenForEditByUser(
                Integer.valueOf(ticketId),
                user.getUserId())) {
            map.addAttribute("severityList", severityService.getOnlineList());
            map.addAttribute("page", "main/openticket.jsp");
        } else {
            ticketLockRequestService.addTicketLockRequest(
                    Integer.valueOf(ticketId),
                    user.getUserId());

            userAlertService.insertUserAlert(user.getUserId(), "Access requested");

            map.addAttribute("page", "main/closedticket.jsp");
        }

        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);
        map.put("ticketObject", ticket);

        return "mainview";
    }

    @RequestMapping(value = "/create/saveticket/", method = RequestMethod.POST)
    public String saveNewTicket(HttpServletRequest request,
            @RequestParam(value = "shortdescription", required = false) String shortDescription,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlId,
            @RequestParam(value = "ticketTypeId", required = false) String ticketTypeId,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            @RequestParam(value = "severityId", required = false) String severityId,
            @RequestParam(value = "newstatus", required = false) String newStatus,
            @RequestParam(value = "reportedById", required = false) String reportedById,
            @RequestParam(value = "logentry", required = false) String logEntry,
            @ModelAttribute("uploadfilelist") UploadedFileLog uploadedFileLog,
            ModelMap map) {

        map.addAttribute("page", "main/createticketselection.jsp");

        // Checks if the IDs provided match the application control ID details from DB
        // This provides a level of security with the application control ID being correct
        ApplicationControl applicationControl = applicationControlService.getApplicationControlById(Integer.valueOf(applicationControlId), true);

        if ((applicationControl.getTicketType().getTicketTypeId() != Integer.valueOf(ticketTypeId))
                || (applicationControl.getApplication().getApplicationId() != Integer.valueOf(applicationId))
                || (applicationControl.getSeverity().getSeverityId() != Integer.valueOf(severityId))) {
            map.addAttribute("alert", "Configuration details received did not match expected");
            return "mainview";
        }

        // Checks if the status move is legal
        int currentStatusId;

        if (newStatus.isEmpty()) {
            currentStatusId = applicationControl.getWorkflow().getWorkflowMap()
                    .getWorkflow().get(0).getWorkflowStatus().getWorkflowStatusId();
        } else {
            currentStatusId = Integer.valueOf(newStatus);
        }

        WorkflowStep workflowStep = applicationControl.getWorkflow().getWorkflowMap().getWorkflow().get(0);
        if ((workflowStep.getWorkflowStatus().getWorkflowStatusId() != currentStatusId) && (!workflowStep.isLegalStep(currentStatusId))) {
            map.addAttribute("alert", "Status conflicts with workflow configuration");
            map.addAttribute("page", "main/createticketselection.jsp");
            return "mainview";
        }

        User user = (User) request.getSession().getAttribute("user");
        Ticket ticket = new Ticket();

        if (newStatus.isEmpty()) {
            ticket.setCurrentWorkflowStep(applicationControl.getWorkflow().getWorkflowMap().getWorkflow().get(0));
        } else {
            boolean found = false;
            Role role = new Role();

            for (WorkflowStep nextWorkflowStep : workflowStep.getNextWorkflowStep()) {
                if (nextWorkflowStep.getWorkflowStatus().getWorkflowStatusId() == currentStatusId) {
                    ticket.setCurrentWorkflowStep(nextWorkflowStep);
                    found = true;
                    role = nextWorkflowStep.getRole();
                }
            }

            if (!found) {
                Validation.pageError(map);
                return "mainview";
            }

            // Checks if correct role is maintained 
            if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                    role.getRoleId())) {
                Validation.userDoesntHaveRole(map);
                return "mainview";
            }
        }

        // Checks if correct role is maintained 
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                applicationControl.getRole().getRoleId())) {
            Validation.userDoesntHaveRole(map);
            map.addAttribute("page", "main/createticketselection.jsp");
            return "mainview";
        }

        // Populates ticket information
        ticket.setShortDescription(shortDescription);
        ticket.setApplicationControl(applicationControl);
        ticket.setReportedBy(new User(Integer.valueOf(reportedById)));
        ticket.setCreatedBy(user);
        ticket.setLastProcessedBy(user);

        // Inserts ticket
        ticketService.insertTicket(ticket);

        // Ticket log
        // Checks if a log entry has been made
        if (logEntry.length() > 0) {
            logEntry = logEntry.replaceAll("(\r\n|\n)", "<br />");
            ticketLogService.addTicketLog(ticket.getTicketId(), user.getUserId(), logEntry);
        }

        // Complete any uploads
        this.fileUploadHandler(request, uploadedFileLog, ticket.getTicketId(), user);

        map.put("ticketObject", ticketService.getTicketById(ticket.getTicketId(), true, true, true, true));
        map.addAttribute("page", "main/closedticket.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/update/saveticket/", method = RequestMethod.POST)
    public String saveExistingTicket(HttpServletRequest request,
            @RequestParam(value = "ticketid", required = false) String ticketId,
            @RequestParam(value = "old_shortdescription", required = false) String oldShortDescription,
            @RequestParam(value = "new_shortdescription", required = false) String newShortDescription,
            @RequestParam(value = "severity", required = false) String severity,
            @RequestParam(value = "newstatus", required = false) String newStatus,
            @RequestParam(value = "logentry", required = false) String logEntry,
            @ModelAttribute("uploadfilelist") UploadedFileLog uploadedFileLog,
            ModelMap map) {

        map.addAttribute("page", "main/closedticket.jsp");
  
        if(ticketId == null) {
            Validation.inputIsInvalid(map, "Ticket ID");
            return "mainview";
        }
        // Checks if this ticket is open for editing by this user
        Ticket ticket = ticketService.getTicketById(Integer.valueOf(ticketId), true, true, true, true);

        if(ticket.getTicketId() == 0) {
            Validation.inputIsInvalid(map, "Ticket ID");
            return "mainview";
        }
        
        map.put("ticketObject", ticket);
        User user = (User) request.getSession().getAttribute("user");

        // Check: check if maintenance role is attached to user
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                ticket.getApplicationControl().getRole().getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        // Check: check if role is maintained for status change
        // Checks if this user has a open ticket lock
        if (!ticketLockRequestService.ticketOpenForEditByUser(Integer.valueOf(ticketId), user.getUserId())) {
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
                    .get(0).getWorkflowStatus().getWorkflowStatusId());

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
                    .get(0).getWorkflowStatus().getWorkflowStatusName());

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
                            .getWorkflowStatus();

                    ticketService.updateStatus(ticket.getTicketId(), workflowStatus.getWorkflowStatusId());

                    ticketLogService.addTicketLog(ticket.getTicketId(), user.getUserId(), "Ticket status has been changed to "
                            + workflowStatus.getWorkflowStatusName());

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
                    ticket.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusId(), 
                    user.getUserId());
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
