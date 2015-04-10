/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daoimpl.ApplicationControlService;
import com.crucialticketing.daoimpl.ApplicationService;
import com.crucialticketing.daoimpl.AttachmentService;
import com.crucialticketing.daoimpl.QueueService;
import com.crucialticketing.entities.User;
import com.crucialticketing.daoimpl.RoleService;
import com.crucialticketing.daoimpl.SeverityService;
import com.crucialticketing.daoimpl.TicketLinkService;
import com.crucialticketing.daoimpl.TicketLogService;
import com.crucialticketing.daoimpl.TicketService;
import com.crucialticketing.daoimpl.TicketTypeService;
import com.crucialticketing.daoimpl.UserQueueConService;
import com.crucialticketing.daoimpl.UserRoleConService;
import com.crucialticketing.daoimpl.UserService;
import com.crucialticketing.daoimpl.WorkflowMapService;
import com.crucialticketing.daoimpl.WorkflowService;
import com.crucialticketing.daoimpl.WorkflowStatusService;
import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.TicketLink;
import com.crucialticketing.entities.UploadedFile;
import com.crucialticketing.entities.UploadedFileLog;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.UserRoleCon;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStep;
import com.crucialticketing.logic.CheckLogicService;
import com.crucialticketing.util.Timestamp;
import com.crucialticketing.util.Validation;
import com.crucialticketing.util.WorkflowStatusType;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
 * @author DanFoley
 */
@RequestMapping(value = "/create/")
@Controller
public class CreationController {

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleConService userRoleConService;

    //
    @Autowired
    QueueService queueService;

    @Autowired
    UserQueueConService userQueueConService;

    //
    @Autowired
    ApplicationService applicationService;

    //
    @Autowired
    SeverityService severityService;

    //
    @Autowired
    TicketTypeService ticketTypeService;

    //
    @Autowired
    WorkflowService workflowService;

    //
    @Autowired
    WorkflowMapService workflowMapService;

    //
    @Autowired
    WorkflowStatusService workflowStatusService;

    @Autowired
    ApplicationControlService applicationControlService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    TicketLogService ticketLogService;

    @Autowired
    TicketLinkService ticketLinkService;

    //
    @Autowired
    CheckLogicService checkService;

    @RequestMapping(value = "/ticket/p1/", method = RequestMethod.GET)
    public String preTicketCreation(ModelMap map) {
        map.addAttribute("ticketTypeList", ticketTypeService.getTicketTypeList());
        map.addAttribute("applicationList", applicationService.getOnlineApplicationList());
        map.addAttribute("severityList", severityService.getOnlineList());
        this.setRoot("main/create/ticket/createticketselection.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/ticket/p2/", method = RequestMethod.POST)
    public String ticketCreation(HttpServletRequest request,
            @RequestParam(value = "ticketTypeId", required = false) String ticketTypeId,
            @RequestParam(value = "severityId", required = false) String severityId,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            ModelMap map) {

        // Checks if fields are provided
        if ((Validation.toInteger(ticketTypeId) == 0) || (Validation.toInteger(severityId) == 0) || (Validation.toInteger(severityId) == 0)) {
            Validation.inputNotProvided(map, "Ticket Type, Severity or Application");
            return this.preTicketCreation(map);
        } else if ((ticketTypeId.isEmpty()) || (severityId.isEmpty()) || (applicationId.isEmpty())) {
            Validation.inputNotProvided(map, "Ticket Type, Severity or Application");
            return this.preTicketCreation(map);
        }

        // Pre checks
        if (!ticketTypeService.doesTicketTypeExist(Validation.toInteger(ticketTypeId))) {
            Validation.inputIsInvalid(map, "Ticket Type");
            return preTicketCreation(map);
        }

        if (!severityService.doesSeverityExistInOnlineById(Integer.valueOf(severityId))) {
            Validation.inputIsInvalid(map, "Severity");
            return preTicketCreation(map);
        }

        if (!applicationService.doesApplicationExistInOnline(Integer.valueOf(applicationId))) {
            Validation.inputIsInvalid(map, "Application");
            return preTicketCreation(map);
        }

        if (!applicationControlService.doesApplicationControlExistInOnline(
                Integer.valueOf(ticketTypeId),
                Integer.valueOf(applicationId),
                Integer.valueOf(severityId))) {
            Validation.fieldDoesNotExist(map, "Configuration");
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

        map.addAttribute("ticket", ticket);

        map.addAttribute("newTicket", true);
        map.addAttribute("view", false);
        map.addAttribute("edit", false);

        this.setRoot("main/update/updateticket.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/ticket/save/", method = RequestMethod.POST)
    public String saveNewTicket(HttpServletRequest request,
            @RequestParam(value = "shortDescription", required = false) String shortDescription,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlId,
            @RequestParam(value = "ticketTypeId", required = false) String ticketTypeId,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            @RequestParam(value = "severityId", required = false) String severityId,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusId,
            @RequestParam(value = "reportedByUserId", required = false) String reportedByUserId,
            @RequestParam(value = "logentry", required = false) String logEntry,
            @RequestParam(value = "linkedTicketList", required = false) String[] linkedTicketList,
            @ModelAttribute("uploadfilelist") UploadedFileLog uploadedFileLog,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        Ticket ticket = new Ticket();

        // Checks if the IDs provided match the application control ID details from DB
        // This provides a level of security with the application control ID being correct
        ApplicationControl applicationControl = applicationControlService.getApplicationControlById(Integer.valueOf(applicationControlId), true);

        if ((applicationControl.getTicketType().getTicketTypeId() != Validation.toInteger(ticketTypeId))
                || (applicationControl.getApplication().getApplicationId() != Validation.toInteger(applicationId))
                || (applicationControl.getSeverity().getSeverityId() != Validation.toInteger(severityId))) {
            Validation.inputIsInvalid(map, "Configuration");
            return this.preTicketCreation(map);
        }

        ticket.setApplicationControl(applicationControl);

        // Sets first workflow step
        ticket.setCurrentWorkflowStep(ticket
                .getApplicationControl()
                .getWorkflow()
                .getWorkflowMap()
                .getWorkflow()
                .get(0));

        // Checks if the ticket has been moved from the opening status
        if (Validation.toInteger(workflowStatusId) != 0) {
            if (!ticket.getApplicationControl().getWorkflow().getWorkflowMap().doesStepExist(Validation.toInteger(workflowStatusId))) {
                Validation.inputIsInvalid(map, "Workflow Status");
                return this.preTicketCreation(map);
            }

            boolean found = false;
            Role role = new Role();
            int nextWorkflowStatusId = Validation.toInteger(workflowStatusId);

            for (int i = 0; i < ticket.getApplicationControl().getWorkflow().getWorkflowMap().getWorkflow().get(0).getNextWorkflowStep().size(); i++) {

                if (ticket.getApplicationControl().getWorkflow().getWorkflowMap().getWorkflow().get(0).getNextWorkflowStep().get(i).getWorkflowStatus().getWorkflowStatusId() == nextWorkflowStatusId) {

                    ticket.setCurrentWorkflowStep(ticket.getApplicationControl().getWorkflow().getWorkflowMap().getWorkflow().get(0).getNextWorkflowStep().get(i));
                    found = true;
                    role = ticket.getCurrentWorkflowStep().getRole();
                }
            }

            if (!found) {
                Validation.pageError(map);
                return this.preTicketCreation(map);
            }

            // Checks if correct role is maintained 
            if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                    role.getRoleId())) {
                Validation.userDoesntHaveRole(map);
                return this.preTicketCreation(map);
            }
        }

        // Populates ticket information
        if (!Validation.isStringSet(shortDescription, 50)) {
            Validation.inputIsInvalid(map, "Short Description");
            return this.preTicketCreation(map);
        }

        ticket.setShortDescription(shortDescription);

        if ((Validation.toInteger(reportedByUserId) == 0)
                || (!userService.doesUserExistInOnline(Validation.toInteger(reportedByUserId)))) {
            Validation.inputIsInvalid(map, "Reported By User");
            return this.preTicketCreation(map);
        }

        ticket.setReportedBy(new User(Validation.toInteger(reportedByUserId)));
        ticket.setCreatedBy(user);
        ticket.setLastUpdatedBy(user);

        // Inserts ticket
        ticketService.insertTicket(ticket);

        map.addAttribute("newTicket", false);
        map.addAttribute("view", true);
        map.addAttribute("edit", false);

        setRoot("main/update/updateticket.jsp", map);

        // Checks if the log entry is valid - buffer for <br />
        if (Validation.isStringSet(logEntry, (1000 - 200))) {
            if ((logEntry.isEmpty()) || ((logEntry.replace(" ", "")).length() == 0)) {
                Validation.inputIsInvalid(map, "Ticket Log Entry");
                map.addAttribute("ticket", ticketService.getTicketById(ticket.getTicketId(), true, true, true, true, true));
                return "mainview";
            }
        }

        // Updates log entry if required
        if (logEntry.length() > 0) {
            logEntry = logEntry.replaceAll("(\r\n|\n)", "<br />");
            ticketLogService.addTicketLog(ticket.getTicketId(), user.getUserId(), logEntry);
        }

        // Complete any uploads if reuqired
        if (this.fileUploadHandler(request, uploadedFileLog, ticket.getTicketId(), user, map)) {
            Validation.fieldAlreadyExists(map, "Upload");
            map.addAttribute("ticket", ticketService.getTicketById(ticket.getTicketId(), true, true, true, true, true));
            return "mainview";
        }

        // Adds any links
        if (linkedTicketList != null) {
            for (String link : linkedTicketList) {
                int ticketLinkId = Validation.toInteger(link);

                if (ticketLinkId == 0) {
                    Validation.inputIsInvalid(map, "Ticket Link");
                    map.addAttribute("ticket", ticketService.getTicketById(ticket.getTicketId(), true, true, true, true, true));
                    return "mainview";
                }

                if (!ticketService.doesTicketExist(ticketLinkId)) {
                    Validation.inputIsInvalid(map, "Ticket Link");
                    map.addAttribute("ticket", ticketService.getTicketById(ticket.getTicketId(), true, true, true, true, true));
                    return "mainview";
                }

                if (!ticketLinkService.doesTicketLinkExist(ticket.getTicketId(), ticketLinkId)) {
                    ticketLinkService.insertTicketLink(new TicketLink(new Ticket(ticket.getTicketId()), new Ticket(ticketLinkId)));
                }
            }
        }

        ticket = ticketService.getTicketById(ticket.getTicketId(), true, true, true, true, true);

        map.addAttribute("ticket", ticket);
        return "mainview";
    }

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public String createUserForm(HttpServletRequest request,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_USER_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("roleList", roleService.getOnlineRoleList());
        map.addAttribute("user", new User());

        this.setRoot("main/create/createuser.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/user/create/", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request,
            @ModelAttribute("user") User userForCreation,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.createUserForm(request, map);
        map.addAttribute("user", userForCreation);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // check if username is taken
        if (userService.doesUserExist(userForCreation.getUsername())) {
            Validation.fieldAlreadyExists(map, "User");
            return "mainview";
        }

        // If roles have been selected, checks if they are valid
        for (int i = 0, length = userForCreation.getUserRoleConList().size(); i < length; i++) {
            UserRoleCon userRoleCon = userForCreation.getUserRoleConList().get(i);

            if (!roleService.doesRoleExistInOnline(userRoleCon.getRole().getRoleId())) {
                userForCreation.getUserRoleConList().remove(userRoleCon);
                i--;
                length--;
            }
        }

        // Inserts user and gets ID to store roles against
        int userId = userService.insertUser(userForCreation, new Ticket(Integer.valueOf(ticketId)), user);

        for (UserRoleCon userRoleCon : userForCreation.getUserRoleConList()) {
            userRoleCon.getUser().setUserId(userId);
            userRoleCon.setActiveFlag(userForCreation.getActiveFlag());

            int userRoleConId = userRoleConService.insertUserRoleCon(userRoleCon, true, new Ticket(Integer.valueOf(ticketId)), user);
            userRoleConService.updateToUnprocessed(userRoleConId, new Ticket(Integer.valueOf(ticketId)), user);
        }

        userService.updateToUnprocessed(userForCreation.getUserId(), new Ticket(Integer.valueOf(ticketId)), user);

        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/role/", method = RequestMethod.GET)
    public String createRoleForm(HttpServletRequest request,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_ROLE_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }
        map.addAttribute("role", new Role());
        map.addAttribute("page", "main/create/createrole.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/role/create/", method = RequestMethod.POST)
    public String createRole(HttpServletRequest request,
            @ModelAttribute("role") Role roleForCreation,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.createRoleForm(request, map);
        map.addAttribute("role", roleForCreation);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if ((!checkService.doesTicketCheckPass(ticketId, map))) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Checks if role exists already
        if (roleService.doesRoleExist(roleForCreation.getRoleName())) {
            Validation.fieldAlreadyExists(map, "Role");
            return "mainview";
        }

        roleService.insertRole(roleForCreation, new Ticket(Integer.valueOf(ticketId)), user);
        roleService.updateToUnprocessed(roleForCreation.getRoleId(), new Ticket(Integer.valueOf(ticketId)), user);

        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/userrolecon/", method = RequestMethod.GET)
    public String createUserRoleConForm(HttpServletRequest request,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_USER_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("userList", userService.getOnlineUserList(false));
        map.addAttribute("roleList", roleService.getOnlineRoleList());

        UserRoleCon userRoleCon = new UserRoleCon();
        userRoleCon.setValidFromStr(Timestamp.convTimestamp(Timestamp.getTimestamp()));

        map.addAttribute("userRoleCon", userRoleCon);
        setRoot("main/create/createuserrolecon.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/userrolecon/create/", method = RequestMethod.POST)
    public String createUserRoleCon(HttpServletRequest request,
            @ModelAttribute("userRoleCon") UserRoleCon userRoleCon,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.createUserRoleConForm(request, map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Check if user role con already exists
        if (userRoleConService.doesUserRoleConExistInOnline(userRoleCon.getUser().getUserId(), userRoleCon.getRole().getRoleId())) {
            Validation.fieldAlreadyExists(map, "User Role Connection");
            return "mainview";
        }

        // Checks user is valid and active
        if (!userService.doesUserExistInOnline(userRoleCon.getUser().getUserId())) {
            Validation.inputIsInvalid(map, "User");
        }

        // Checks role is valid and active
        if (!roleService.doesRoleExistInOnline(userRoleCon.getRole().getRoleId())) {
            Validation.inputIsInvalid(map, "Role");
        }

        // Checks if the validity dates are correct 
        userRoleCon.setValidFrom(Timestamp.convTimestamp(userRoleCon.getValidFromStr()));
        userRoleCon.setValidTo(Timestamp.convTimestamp(userRoleCon.getValidToStr()));

        if (userRoleCon.getValidTo() == 0) {
            userRoleCon.setValidTo(userRoleCon.getValidFrom());
        }

        if (userRoleCon.getValidFrom() > userRoleCon.getValidTo()) {
            Validation.inputIsInvalid(map, "Validity");
            return "mainview";
        }

        // Inserts user role connection 
        userRoleConService.insertUserRoleCon(userRoleCon, false, new Ticket(Integer.valueOf(ticketId)), user);
        userRoleConService.updateToUnprocessed(userRoleCon.getUserRoleConId(), new Ticket(Integer.valueOf(ticketId)), user);

        // Success
        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/queue/", method = RequestMethod.GET)
    public String createQueueForm(HttpServletRequest request,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_QUEUE_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("userList", userService.getOnlineUserList(false));
        map.addAttribute("queue", new Queue());
        setRoot("main/create/createqueue.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/queue/create/", method = RequestMethod.POST)
    public String createQueue(HttpServletRequest request,
            @ModelAttribute("queue") Queue queue,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.createQueueForm(request, map);
        map.addAttribute("queue", queue);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Check if queue name already exists
        if (queueService.doesQueueExist(queue.getQueueName())) {
            Validation.fieldAlreadyExists(map, "Queue");
            return "mainview";
        }

        // If users have been selected, checks if they are valid
        for (int i = 0, length = queue.getUserQueueConList().size(); i < length; i++) {
            UserQueueCon userQueueCon = queue.getUserQueueConList().get(i);

            if (!userService.doesUserExistInOnline(userQueueCon.getUser().getUserId())) {
                queue.getUserQueueConList().remove(userQueueCon);
                i--;
                length--;
            }
        }

        // Inserts queue into queue request
        queueService.insertQueue(queue, new Ticket(Integer.valueOf(ticketId)), user);

        // Inserts any user connection
        for (UserQueueCon userQueueCon : queue.getUserQueueConList()) {
            userQueueCon.getQueue().setQueueId(queue.getQueueId());
            userQueueCon.getQueue().setActiveFlag(queue.getActiveFlag());

            int userQueueConId = userQueueConService.insertUserQueueCon(userQueueCon, true, new Ticket(Integer.valueOf(ticketId)), user);
            userQueueConService.updateToUnprocessed(userQueueConId, new Ticket(Integer.valueOf(ticketId)), user);
        }

        queueService.updateToUnprocessed(queue.getQueueId(), new Ticket(Integer.valueOf(ticketId)), user);

        // Success
        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/userqueuecon/", method = RequestMethod.GET)
    public String createUserQueueConForm(HttpServletRequest request,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_QUEUE_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("userList", userService.getOnlineUserList(false));
        map.addAttribute("queueList", queueService.getOnlineQueueList());
        map.addAttribute("userQueueCon", new UserQueueCon());
        setRoot("main/create/createuserqueuecon.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/userqueuecon/create/", method = RequestMethod.POST)
    public String createUserQueueCon(HttpServletRequest request,
            @ModelAttribute("userQueueCon") UserQueueCon userQueueCon,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.createUserQueueConForm(request, map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Check if user role con already exists
        if (userQueueConService.doesUserQueueConExistInOnline(userQueueCon.getUser().getUserId(), userQueueCon.getQueue().getQueueId())) {
            Validation.fieldAlreadyExists(map, "User Queue Connection");
            return "mainview";
        }

        // Checks user is valid and active
        if (!userService.doesUserExistInOnline(userQueueCon.getUser().getUserId())) {
            Validation.inputIsInvalid(map, "User");
        }

        // Checks role is valid and active
        if (!queueService.doesQueueExistInOnline(userQueueCon.getQueue().getQueueId())) {
            Validation.inputIsInvalid(map, "Queue");
        }

        // Inserts user role connection 
        userQueueConService.insertUserQueueCon(userQueueCon, false, new Ticket(Integer.valueOf(ticketId)), user);
        userQueueConService.updateToUnprocessed(userQueueCon.getUserQueueConId(), new Ticket(Integer.valueOf(ticketId)), user);

        // Success
        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/application/", method = RequestMethod.GET)
    public String createApplicationForm(HttpServletRequest request,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_APPLICATION_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("application", new Application());
        this.setRoot("main/create/createapplication.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/application/create/", method = RequestMethod.POST)
    public String createApplication(HttpServletRequest request,
            @ModelAttribute("application") Application application,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.createApplicationForm(request, map);
        map.addAttribute("application", application);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Check if application name already exists
        if (applicationService.doesApplicationExist(application.getApplicationName())) {
            Validation.fieldAlreadyExists(map, "Application");
            return "mainview";
        }

        // Inserts application 
        applicationService.insertApplication(application, new Ticket(Integer.valueOf(ticketId)), user);
        applicationService.updateToUnprocessed(application.getApplicationId(), new Ticket(Integer.valueOf(ticketId)), user);

        // Success
        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);

        return "mainview";
    }

    @RequestMapping(value = "/severity/", method = RequestMethod.GET)
    public String createSeverityForm(HttpServletRequest request,
            ModelMap map) {
        // Resets defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_SEVERITY_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("severity", new Severity());
        this.setRoot("main/create/createseverity.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/severity/create/", method = RequestMethod.POST)
    public String createSeverity(HttpServletRequest request,
            @ModelAttribute("severity") Severity severity,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.createSeverityForm(request, map);
        map.addAttribute("severity", severity);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Check if severity level already exists
        if (severityService.doesSeverityExistByLevel(severity.getSeverityLevel())) {
            Validation.fieldAlreadyExists(map, "Severity");
            return "mainview";
        }

        // Inserts Severity 
        severityService.insertSeverity(severity, new Ticket(Integer.valueOf(ticketId)), user);
        severityService.updateToUnprocessed(severity.getSeverityId(), new Ticket(Integer.valueOf(ticketId)), user);

        // Success
        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/configuration/", method = RequestMethod.GET)
    public String createConfigurationForm(HttpServletRequest request,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_CONFIGURATION_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("ticketTypeList", ticketTypeService.getTicketTypeList());
        map.addAttribute("severityList", severityService.getOnlineList());
        map.addAttribute("applicationList", applicationService.getOnlineApplicationList());
        map.addAttribute("workflowList", workflowService.getOnlineList());
        map.addAttribute("applicationControl", new ApplicationControl());
        map.addAttribute("roleList", roleService.getOnlineRoleList());

        this.setRoot("main/create/createconfiguration.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/configuration/create/", method = RequestMethod.POST)
    public String createConfiguration(HttpServletRequest request,
            @ModelAttribute("applicationControl") ApplicationControl applicationControl,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.createConfigurationForm(request, map);
        map.addAttribute("applicationControl", applicationControl);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Checks if each configuration is valid
        if (!ticketTypeService.doesTicketTypeExist(applicationControl.getTicketType().getTicketTypeId())) {
            Validation.inputIsInvalid(map, "Ticket Type");
            return "mainview";
        }

        if (!severityService.doesSeverityExistInOnlineById(applicationControl.getSeverity().getSeverityId())) {
            Validation.inputIsInvalid(map, "Severity");
            return "mainview";
        }

        if (!applicationService.doesApplicationExistInOnline(applicationControl.getApplication().getApplicationId())) {
            Validation.inputIsInvalid(map, "Application");
            return "mainview";
        }

        if (!workflowService.doesWorkflowExistInOnline(applicationControl.getWorkflow().getWorkflowId())) {
            Validation.inputIsInvalid(map, "Workflow");
            return "mainview";
        }

        // Checks if configuration already exists
        if (applicationControlService.doesApplicationControlExistInOnline(
                applicationControl.getTicketType().getTicketTypeId(),
                applicationControl.getApplication().getApplicationId(),
                applicationControl.getSeverity().getSeverityId())) {
            Validation.fieldAlreadyExists(map, "Workflow Configuration");
            return "mainview";
        }

        applicationControlService.insertApplicationControl(applicationControl, new Ticket(Integer.valueOf(ticketId)), user);
        applicationControlService.updateToUnprocessed(applicationControl.getApplicationControlId(), new Ticket(Integer.valueOf(ticketId)), user);

        // Success
        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflowstatus/", method = RequestMethod.GET)
    public String createWorkflowStatusForm(HttpServletRequest request,
            ModelMap map) {

        // Set defaults - overriden if success
        this.setRoot("menu/create.jsp", map);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOWSTATUS_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("workflowStatus", new WorkflowStatus());
        this.setRoot("main/create/createworkflowstatus.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflowstatus/create/", method = RequestMethod.POST)
    public String createWorkflowStatus(HttpServletRequest request,
            @ModelAttribute("workflowStatus") WorkflowStatus workflowStatus,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.createWorkflowStatusForm(request, map);
        map.addAttribute("workflowStatus", workflowStatus);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        if (workflowStatusService.doesWorkflowStatusExistInOnline(workflowStatus.getWorkflowStatusName())) {
            Validation.fieldAlreadyExists(map, "Workflow Status");
            return "mainview";
        }

        workflowStatusService.insertWorkflowStatus(workflowStatus, new Ticket(Integer.valueOf(ticketId)), user);
        workflowStatusService.updateToUnprocessed(workflowStatus.getWorkflowStatusId(), new Ticket(Integer.valueOf(ticketId)), user);

        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflow/", method = RequestMethod.GET)
    public String createWorkflowFormPart1(HttpServletRequest request,
            ModelMap map) {

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOW_CREATION", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("queueList", queueService.getOnlineQueueList());
        map.addAttribute("workflowStatusList", workflowStatusService.getOnlineList());
        map.addAttribute("workflow", new Workflow());

        this.setRoot("main/create/workflow/createworkflowp1.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflow/p2/", method = RequestMethod.POST)
    public String createWorkflowPage2(HttpServletRequest request,
            @ModelAttribute("workflow") Workflow workflow,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.createWorkflowFormPart1(request, map);
        map.addAttribute("workflow", workflow);

        // Basic checks
        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Adds ticketId
        map.addAttribute("ticketId", ticketId);

        // Adds each status chosen and checks validity 
        for (WorkflowStep workflowStep : workflow.getWorkflowMap().getWorkflow()) {
            if (!workflowStatusService.doesWorkflowStatusExistInOnline(workflowStep.getWorkflowStatus().getWorkflowStatusId())) {
                this.createWorkflowFormPart1(request, map);
                return "mainview";
            }
            workflowStep.getWorkflowStatus().setWorkflowStatusName(
                    workflowStatusService.getWorkflowStatus(
                            workflowStep.getWorkflowStatus().getWorkflowStatusId()).getWorkflowStatusName());
        }

        // Adds role and queue list
        map.addAttribute("roleList", roleService.getOnlineRoleList());
        map.addAttribute("queueList", queueService.getOnlineQueueList());

        map.addAttribute("workflow", workflow);
        this.setRoot("main/create/workflow/createworkflowp2.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflow/create/", method = RequestMethod.POST)
    public String createWorkflow(HttpServletRequest request,
            @ModelAttribute("workflow") Workflow workflow,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Resets defaults - overriden if success
        this.createWorkflowFormPart1(request, map);
        map.addAttribute("workflow", workflow);

        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesTicketCheckPass(ticketId, map)) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Start
        List<Integer> baseNodeList = new ArrayList<>();
        List<Integer> baseNodeCheckList = new ArrayList();
        List<Integer> closureNodeList = new ArrayList<>();
        List<Integer> closureNodeCheckList = new ArrayList<>();

        HashMap<Integer, WorkflowStatus> statusCheckList = new HashMap<>();
        HashMap<Integer, Queue> queueCheckList = new HashMap<>();
        HashMap<Integer, Role> roleCheckList = new HashMap();

        // Checks if all status' are valid
        for (WorkflowStep workflowStep : workflow.getWorkflowMap().getWorkflow()) {

            // Adds for future checks
            statusCheckList.put(workflowStep.getWorkflowStatus().getWorkflowStatusId(), workflowStep.getWorkflowStatus());

            // Role
            if (workflowStep.getRole().getRoleId() != 0) {
                roleCheckList.put(workflowStep.getRole().getRoleId(), workflowStep.getRole());
            }

            // Queue
            if (workflowStep.getQueue().getQueueId() != 0) {
                queueCheckList.put(workflowStep.getQueue().getQueueId(), workflowStep.getQueue());
            }

            // Adds a node to visit
            baseNodeList.add(workflowStep.getWorkflowStatus().getWorkflowStatusId());

            // Checks next steps
            for (WorkflowStep nextWorkflowStep : workflowStep.getNextWorkflowStep()) {

                // Adds for future checks
                statusCheckList.put(nextWorkflowStep.getWorkflowStatus().getWorkflowStatusId(), nextWorkflowStep.getWorkflowStatus());
                roleCheckList.put(nextWorkflowStep.getRole().getRoleId(), nextWorkflowStep.getRole());

                closureNodeList.add(nextWorkflowStep.getWorkflowStatus().getWorkflowStatusId());

            }
        }

        // Checks for duplicates
        boolean found = false;
        for (int i = 0; (i < baseNodeList.size()) && (!found); i++) {
            int baseNode = baseNodeList.get(i);
            int closureNode = closureNodeList.get(i);

            for (int j = i + 1; (j < baseNodeList.size()) && (!found); j++) {
                if ((baseNode == baseNodeList.get(j)) && (closureNode == closureNodeList.get(j))) {
                    found = true;
                }
            }
        }
        if (found) {
            Validation.inputIsInvalid(map, "Workflow");
            return "mainview";
        }

        // Comparison between start and end nodes to extract base and closure nodes
        for (Integer baseNode : baseNodeList) {
            if (!closureNodeList.contains(baseNode)) {
                if (!baseNodeCheckList.contains(baseNode)) {
                    baseNodeCheckList.add(baseNode);
                }
            }
        }
        for (Integer closureNode : closureNodeList) {
            if (!baseNodeList.contains(closureNode)) {
                if (!closureNodeCheckList.contains(closureNode)) {
                    closureNodeCheckList.add(closureNode);
                }
            }
        }

        // At this point you have:
        // baseNodeCheckList = all base nodes which have no connections to (No DUPS)
        // closureNodeCheckList = all closure nodes which have no connections from (No DUPS)
        // StatusCheckList = all status' on this workflow
        // RoleCheckList = all roles attached to this workflow
        // QueueCheckList = all queues attached to this workflow
        // Duplicate connections have been checked
        // Checks performed below
        Iterator mapIterator;

        // Status
        List<WorkflowStatus> workflowStatusList = workflowStatusService.getOnlineList();
        mapIterator = statusCheckList.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) mapIterator.next();
            found = false;
            for (WorkflowStatus workflowStatus : workflowStatusList) {
                if (workflowStatus.getWorkflowStatusId() == (int) pair.getKey()) {

                    // Checks if status is a base or closure node - validates against db
                    for (Integer baseNode : baseNodeCheckList) {
                        if (baseNode == workflowStatus.getWorkflowStatusId()) {
                            if (!(workflowStatus.getWorkflowStatusType() == WorkflowStatusType.BASE)) {
                                Validation.illegalWorkflow(map);
                                return "mainview";
                            }
                        }
                    }
                    for (Integer closureNode : closureNodeCheckList) {
                        if (closureNode == workflowStatus.getWorkflowStatusId()) {
                            if (!(workflowStatus.getWorkflowStatusType() == WorkflowStatusType.CLOSURE)) {
                                Validation.illegalWorkflow(map);
                                return "mainview";
                            }
                        }
                    }

                    found = true;
                }
            }
            mapIterator.remove(); // avoids a ConcurrentModificationException

            if (!found) {
                Validation.inputIsInvalid(map, "Status");
                return "mainview";
            }
        }

        // Role
        List<Role> roleList = roleService.getOnlineRoleList();
        mapIterator = roleCheckList.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) mapIterator.next();
            found = false;
            for (Role role : roleList) {
                if (role.getRoleId() == (int) pair.getKey()) {
                    found = true;
                }
            }
            mapIterator.remove(); // avoids a ConcurrentModificationException

            if (!found) {
                Validation.inputIsInvalid(map, "Role");
                return "mainview";
            }
        }

        // Queue
        List<Queue> queueList = queueService.getOnlineQueueList();
        mapIterator = queueCheckList.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) mapIterator.next();
            found = false;
            for (Queue queue : queueList) {
                if (queue.getQueueId() == (int) pair.getKey()) {
                    found = true;
                }
            }
            mapIterator.remove(); // avoids a ConcurrentModificationException

            if (!found) {
                Validation.inputIsInvalid(map, "Queue");
                return "mainview";
            }
        }

        // Checks if name is taken
        if (workflowService.doesWorkflowExistInOnline(workflow.getWorkflowName())) {
            Validation.fieldAlreadyExists(map, "Workflow");
            return "mainview";
        }

        // All status', queues and roles are valid
        // All paths are complete
        // No duplicate or abandoned paths
        // Adds workflow template
        workflowService.insertWorkflow(workflow, new Ticket(Integer.valueOf(ticketId)), user);

        workflowMapService.insertWorkflowStep(workflow);

        workflowService.updateToUnprocessed(workflow.getWorkflowId(), new Ticket(Integer.valueOf(ticketId)), user);

        // Adds workflow steps
        // Sets workflow template to unprocessed
        map.addAttribute("success", "Request submission received");
        this.setRoot("menu/create.jsp", map);
        return "mainview";
    }

    // Sub-controller - set default file return
    private void setRoot(String file, ModelMap map) {
        map.addAttribute("page", file);
    }

    private boolean fileUploadHandler(
            HttpServletRequest request,
            UploadedFileLog uploadedFileLog,
            int ticketId,
            User user,
            ModelMap map) {
        try {
            for (UploadedFile uploadedFile : uploadedFileLog.getFiles()) {

                if (!uploadedFile.getFile().isEmpty()) {
                    byte[] bytes = uploadedFile.getFile().getBytes();

                    String fileName = uploadedFile.getFile().getOriginalFilename();

                    if (attachmentService.doesFileUploadExist(fileName, ticketId)) {
                        return true;
                    }
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

        return false;
    }
}
