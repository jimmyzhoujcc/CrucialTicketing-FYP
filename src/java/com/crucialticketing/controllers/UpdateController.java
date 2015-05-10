/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daoimpl.ApplicationControlLockRequestService;
import com.crucialticketing.daoimpl.ApplicationControlService;
import com.crucialticketing.daoimpl.ApplicationLockRequestService;
import com.crucialticketing.daoimpl.ApplicationService;
import com.crucialticketing.daoimpl.AttachmentService;
import com.crucialticketing.daoimpl.QueueLockRequestService;
import com.crucialticketing.daoimpl.QueueService;
import com.crucialticketing.daoimpl.RoleLockRequestService;
import com.crucialticketing.daoimpl.RoleService;
import com.crucialticketing.daoimpl.SeverityLockRequestService;
import com.crucialticketing.daoimpl.SeverityService;
import com.crucialticketing.daoimpl.TicketChangeLogService;
import com.crucialticketing.daoimpl.TicketLinkService;
import com.crucialticketing.daoimpl.TicketLockRequestService;
import com.crucialticketing.daoimpl.TicketLogService;
import com.crucialticketing.daoimpl.TicketService;
import com.crucialticketing.daoimpl.TicketTypeService;
import com.crucialticketing.daoimpl.UserLockRequestService;
import com.crucialticketing.daoimpl.UserQueueConService;
import com.crucialticketing.daoimpl.UserRoleConService;
import com.crucialticketing.daoimpl.UserService;
import com.crucialticketing.daoimpl.WorkflowLockRequestService;
import com.crucialticketing.daoimpl.WorkflowMapService;
import com.crucialticketing.daoimpl.WorkflowService;
import com.crucialticketing.daoimpl.WorkflowStatusLockRequestService;
import com.crucialticketing.daoimpl.WorkflowStatusService;
import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.ApplicationControlLockRequest;
import com.crucialticketing.entities.ApplicationLockRequest;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.QueueLockRequest;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleLockRequest;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.SeverityLockRequest;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.TicketLink;
import com.crucialticketing.entities.TicketLockRequest;
import com.crucialticketing.entities.UploadedFile;
import com.crucialticketing.entities.UploadedFileLog;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserLockRequest;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowLockRequest;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStatusLockRequest;
import com.crucialticketing.entities.WorkflowStep;
import com.crucialticketing.logic.CheckLogicService;
import com.crucialticketing.logic.UserLogicService;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.util.Timestamp;
import com.crucialticketing.util.Validation;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author DanFoley
 */
@RequestMapping(value = "/update/")
@Controller
public class UpdateController {

    // Logical services
    @Autowired
    UserLogicService userLogicService;

    //
    @Autowired
    TicketLockRequestService ticketLockRequestService;

    @Autowired
    UserLockRequestService userLockRequestService;

    @Autowired
    RoleLockRequestService roleLockRequestService;

    @Autowired
    QueueLockRequestService queueLockRequestService;

    @Autowired
    ApplicationLockRequestService applicationLockRequestService;

    @Autowired
    SeverityLockRequestService severityLockRequestService;

    @Autowired
    ApplicationControlLockRequestService applicationControlLockRequestService;

    @Autowired
    WorkflowLockRequestService workflowLockRequestService;

    @Autowired
    WorkflowStatusLockRequestService workflowStatusLockRequestService;

    //
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    QueueService queueService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationControlService applicationControlService;

    @Autowired
    TicketTypeService ticketTypeService;

    @Autowired
    SeverityService severityService;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    WorkflowMapService workflowMapService;

    @Autowired
    WorkflowStatusService workflowStatusService;

    //
    @Autowired
    UserRoleConService userRoleConService;

    @Autowired
    UserQueueConService userQueueConService;

    //
    @Autowired
    TicketService ticketService;

    @Autowired
    TicketLogService ticketLogService;

    @Autowired
    TicketChangeLogService ticketChangeLogService;

    @Autowired
    CheckLogicService checkService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    TicketLinkService ticketLinkService;

    @RequestMapping(value = "/query/{pagename}/", method = RequestMethod.GET)
    public String index(HttpServletRequest request,
            @PathVariable(value = "pagename") String pageName, ModelMap map) {

        setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_" + pageName.toUpperCase() + "_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        setRoot("main/update/query/query" + pageName + ".jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/query/configuration/", method = RequestMethod.GET)
    public String configuration(HttpServletRequest request, ModelMap map) {
        setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_CONFIGURATION_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        map.addAttribute("ticketTypeList", ticketTypeService.getTicketTypeList());
        map.addAttribute("applicationList", applicationService.getApplicationList());
        map.addAttribute("severityList", severityService.getList());
        map.addAttribute("workflowList", workflowService.getList());
        map.addAttribute("roleList", roleService.getList());

        this.setRoot("main/update/query/queryconfiguration.jsp", map);
        return "mainview";
    }

    // Beginning of each section
    // Ticket
    @RequestMapping(value = "/ticket/", method = RequestMethod.POST)
    public String queryTicket(HttpServletRequest request,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            @RequestParam(value = "lastUpdatedFrom", required = false) String lastUpdatedFrom,
            @RequestParam(value = "lastUpdatedTo", required = false) String lastUpdatedTo,
            ModelMap map) {

        setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_TICKET_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        ArrayList<String> ticketIdList = new ArrayList<>();
        ArrayList<String> emptyList = new ArrayList<>();

        List<Ticket> ticketList = new ArrayList<>();

        if (Validation.isStringSet(ticketId, 10)) {
            ticketIdList.add(ticketId);

            ticketList = ticketService.getListByCriteria(ticketIdList, emptyList,
                    emptyList, emptyList,
                    emptyList, emptyList,
                    emptyList, emptyList,
                    emptyList, emptyList,
                    "", "",
                    String.valueOf(Timestamp.convTimestamp(lastUpdatedFrom)), String.valueOf(Timestamp.convTimestamp(lastUpdatedTo)));
        }

        // Success
        map.put("ticketList", ticketList);
        setRoot("main/update/query/queryticket.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/ticket/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewTicket(HttpServletRequest request,
            @RequestParam(value = "ticketId", required = false) String ticketIdStr,
            ModelMap map) {
        setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_TICKET_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketIdStr))) {
            Validation.inputIsInvalid(map, "query");
            this.setRoot("main/update/query/queryticket.jsp", map);
            return "mainview";
        }

        Ticket ticket = ticketService.getTicketById(Validation.toInteger(ticketIdStr), true, true, true, true, true);

        map.addAttribute("ticket", ticket);
        map.addAttribute("newTicket", false);
        map.addAttribute("view", true);
        map.addAttribute("edit", false);

        setRoot("main/update/updateticket.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/ticket/edit/", method = RequestMethod.POST)
    public String editTicket(HttpServletRequest request,
            @RequestParam(value = "ticketId", required = false) String ticketIdStr,
            ModelMap map) {
        boolean editMode = false;
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_TICKET_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewTicket(request, ticketIdStr, map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketIdStr))) {
            this.setRoot("main/update/query/queryticket.jsp", map);
            Validation.inputIsInvalid(map, "query");
            return "mainview";
        }

        int ticketId = Validation.toInteger(ticketIdStr);

        if (ticketLockRequestService.checkIfOpen(ticketId, user.getUserId())) {
            editMode = true;
            map.addAttribute("edit", editMode);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE);
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE);
        } else if (ticketLockRequestService.checkIfOutstanding(ticketId, user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "Ticket");
        } else {
            ticketLockRequestService.addLockRequest(
                    new TicketLockRequest(new Ticket(ticketId), user));
            Validation.requestCreated(map, "Ticket");
        }

        Ticket ticket = ticketService.getTicketById(ticketId, true, true, true, true, true);
        map.addAttribute("ticket", ticket);

        if (editMode) {
            map.addAttribute("severityList", severityService.getOnlineList());
        }

        map.addAttribute("newTicket", false);
        map.addAttribute("view", !editMode);
        map.addAttribute("edit", editMode);

        this.setRoot("main/update/updateticket.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/ticket/save/", method = RequestMethod.POST)
    public String saveTicket(HttpServletRequest request,
            @RequestParam(value = "ticketId", required = false) String ticketIdStr,
            @RequestParam(value = "shortDescription", required = false) String shortDescription,
            @RequestParam(value = "severity", required = false) String severity,
            @RequestParam(value = "workflowStatus", required = false) String workflowStatus,
            @RequestParam(value = "logentry", required = false) String logEntry,
            @RequestParam(value = "linkedTicketList", required = false) String[] linkedTicketList,
            @ModelAttribute("uploadfilelist") UploadedFileLog uploadedFileLog,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_TICKET_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewTicket(request, ticketIdStr, map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketIdStr))) {
            map.addAttribute("page", "main/update/query/queryticket.jsp");
            Validation.inputIsInvalid(map, "query");
            return "mainview";
        }

        // Checks if this ticket is open for editing by this user
        Ticket oldVersion = ticketService.getTicketById(Validation.toInteger(ticketIdStr), true, true, true, true, false);

        // Check: check if role is maintained for status change
        // Checks if this user has a open ticket lock
        if (!ticketLockRequestService.checkIfOpen(oldVersion.getTicketId(), user.getUserId())) {
            Validation.noOpenRequest(map, String.valueOf(oldVersion));
            return this.viewTicket(request, String.valueOf(oldVersion.getTicketId()), map);
        }

        boolean changeLogEntryRequired = false;
        boolean severityChange = false;
        Ticket newVersion = new Ticket(oldVersion.getTicketId());

        if ((Validation.toInteger(severity) != 0)
                && (Validation.toInteger(severity) != oldVersion.getApplicationControl().getSeverity().getSeverityId())) {
            severityChange = true;

            ApplicationControl applicationControl = applicationControlService.getApplicationControlByCriteria(
                    oldVersion.getApplicationControl().getTicketType().getTicketTypeId(),
                    oldVersion.getApplicationControl().getApplication().getApplicationId(),
                    Validation.toInteger(severity),
                    true);

            if (applicationControl.getApplicationControlId() == 0) {
                map.addAttribute("alert", "A workflow is not currently setup for this configuration");
                return this.viewTicket(request, ticketIdStr, map);
            }

            // Currents the ticket details
            newVersion.setApplicationControl(applicationControl);
            newVersion.setCurrentWorkflowStep(applicationControl.getWorkflow().getWorkflowMap().getWorkflow().get(0));

            ticketLogService.addTicketLog(newVersion.getTicketId(), user.getUserId(),
                    "Ticket severity has been changed from "
                    + oldVersion.getApplicationControl().getSeverity().getSeverityLevel() + ": "
                    + oldVersion.getApplicationControl().getSeverity().getSeverityName()
                    + " to "
                    + newVersion.getApplicationControl().getSeverity().getSeverityLevel() + ": "
                    + newVersion.getApplicationControl().getSeverity().getSeverityName());

            ticketLogService.addTicketLog(newVersion.getTicketId(), 1, "Ticket status reset to "
                    + newVersion.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusName());

            changeLogEntryRequired = true;
        } else {
            newVersion.setApplicationControl(oldVersion.getApplicationControl());
            newVersion.setCurrentWorkflowStep(oldVersion.getCurrentWorkflowStep());
        }

        // Checks if the status has changed
        // Skips this set if the severity changed as the status if overridden when that occurs
        if (!severityChange) {
            if (Validation.toInteger(workflowStatus) != 0) {
                // Checks legality of the status move
                WorkflowStep workflowStep = newVersion.getCurrentWorkflowStep();

                if (!workflowStep.isLegalStep(Validation.toInteger(workflowStatus))) {
                    Validation.inputIsInvalid(map, "Workflow Status");
                    return this.viewTicket(request, ticketIdStr, map);
                } else {
                    newVersion.setCurrentWorkflowStep(newVersion.getApplicationControl()
                            .getWorkflow()
                            .getWorkflowMap()
                            .getWorkflowStageByStatus(Validation.toInteger(workflowStatus)));

                    ticketLogService.addTicketLog(newVersion.getTicketId(), user.getUserId(), "Ticket status has been changed to "
                            + newVersion.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusName());

                    changeLogEntryRequired = true;
                }
            }
        }

        // Checks if the description is valid 
        if (Validation.isStringSet(shortDescription, 50)) {
            if ((shortDescription.isEmpty()) || ((shortDescription.replace(" ", "")).length() == 0)) {
                Validation.inputIsInvalid(map, "Short Description");
            }
        }

        // Checks if the log entry is valid - buffer for <br />
        if (Validation.isStringSet(logEntry, (1000 - 200))) {
            if ((logEntry.isEmpty()) || ((logEntry.replace(" ", "")).length() == 0)) {
                Validation.inputIsInvalid(map, "Ticket Log Entry");
            }
        }

        //***** UPDATE SECTION 
        // Updates short description if required
        if (!oldVersion.getShortDescription().equals(shortDescription)) {
            ticketService.updateDescription(newVersion.getTicketId(), shortDescription);
        }

        // Updates log entry if required
        if (logEntry.length() > 0) {
            logEntry = logEntry.replaceAll("(\r\n|\n)", "<br />");
            ticketLogService.addTicketLog(newVersion.getTicketId(), user.getUserId(), logEntry);
        }

        // Complete any uploads if reuqired
        if (this.fileUploadHandler(request, uploadedFileLog, newVersion.getTicketId(), user, map)) {
            Validation.fieldAlreadyExists(map, "Upload");
            return this.viewTicket(request, ticketIdStr, map);
        }

        // Adds any links
        if (linkedTicketList != null) {
            for (String link : linkedTicketList) {
                int ticketLinkId = Validation.toInteger(link);

                if (ticketLinkId == 0) {
                    Validation.inputIsInvalid(map, "Ticket Link");
                    return this.viewTicket(request, ticketIdStr, map);
                }

                if (!ticketService.doesTicketExist(ticketLinkId)) {
                    Validation.inputIsInvalid(map, "Ticket Link");
                    return this.viewTicket(request, ticketIdStr, map);
                }

                if (!ticketLinkService.doesTicketLinkExist(newVersion.getTicketId(), ticketLinkId)) {
                    ticketLinkService.insertTicketLink(new TicketLink(new Ticket(newVersion.getTicketId()), new Ticket(ticketLinkId)));
                }
            }
        }

        // Adds change log entry if required
        if (changeLogEntryRequired) {
            ticketChangeLogService.addChangeLogEntry(
                    newVersion.getTicketId(),
                    newVersion.getApplicationControl().getApplicationControlId(),
                    newVersion.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusId(),
                    user.getUserId());
        }

        ticketLockRequestService.closeRequest(newVersion.getTicketId(), user.getUserId());
        return this.viewTicket(request, ticketIdStr, map);
    }

    @RequestMapping(value = "/ticket/cancel/", method = RequestMethod.GET)
    public String cancelTicketEdit(HttpServletRequest request,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_TICKET_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewTicket(request, ticketId, map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketId))) {
            map.addAttribute("page", "main/update/query/queryticket.jsp");
            Validation.inputIsInvalid(map, "query");
            return "mainview";
        }

        ticketLockRequestService.closeRequest(Integer.valueOf(ticketId), user.getUserId());
        return this.viewTicket(request, ticketId, map);
    }

    // User
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public String queryUser(HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            ModelMap map) {
        setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_USER_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        String[] inputList = new String[4];
        Object[] objectList = new Object[4];
        int count = 0;

        if (Validation.toInteger(userId) != 0) {
            inputList[count] = "user_id";
            objectList[count] = Integer.valueOf(userId);
            count++;
        }

        if (Validation.isStringSet(username, 25)) {
            inputList[count] = "username";
            objectList[count] = username;
            count++;
        }

        if (Validation.isStringSet(firstName, 25)) {
            inputList[count] = "first_name";
            objectList[count] = firstName;
            count++;
        }

        if (Validation.isStringSet(lastName, 25)) {
            inputList[count] = "last_name";
            objectList[count] = lastName;
            count++;
        }

        List<User> userList;

        try {
            userList = userService.getUserListByCriteria(inputList, objectList, count, false);
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "query");
            userList = new ArrayList<>();
        }

        map.addAttribute("userList", userList);

        setRoot("main/update/query/queryuser.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/user/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewUser(HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userIdStr,
            ModelMap map) {
        setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_USER_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        int userId = Validation.toInteger(userIdStr);

        if (userId == 0) {
            Validation.inputIsInvalid(map, "query");
            this.setRoot("main/update/query/queryuser.jsp", map);
            return "mainview";
        }

        User retrievedUser = userService.getUserById(userId, false);
        retrievedUser.setUserRoleConList(userRoleConService.getRoleListByUserId(retrievedUser.getUserId()));

        map.addAttribute("user", retrievedUser);

        setRoot("main/update/updateuser.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/user/edit/", method = RequestMethod.POST)
    public String editUser(HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userIdStr,
            ModelMap map) {
        boolean editMode = false;
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_USER_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewUser(request, userIdStr, map);
        }

        int userId = Validation.toInteger(userIdStr);

        if (userId == 0) {
            this.setRoot("menu/update.jsp", map);
            Validation.inputIsInvalid(map, "query");
            return "mainview";
        }

        if (userLockRequestService.checkIfOpen(userId, user.getUserId())) {
            editMode = true;
            map.addAttribute("edit", editMode);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE);
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE);
        } else if (userLockRequestService.checkIfOutstanding(userId, user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "User");
        } else {
            userLockRequestService.addUserLockRequest(
                    new UserLockRequest(new User(userId), user));
            Validation.requestCreated(map, "User");
        }

        User retrievedUser = userService.getUserById(userId, editMode);
        retrievedUser.setUserRoleConList(userRoleConService.getRoleListByUserId(retrievedUser.getUserId()));

        map.addAttribute("user", retrievedUser);

        this.setRoot("main/update/updateuser.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/user/save/", method = RequestMethod.POST)
    public String saveUser(HttpServletRequest request,
            @ModelAttribute("user") User userForChange,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        userForChange.getSecure().setTextPassword(password);

        this.setRoot("menu/update.jsp", map);

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_USER_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewUser(request, String.valueOf(userForChange.getUserId()), map);
        }

        int userId = userForChange.getUserId();

        if (!userLockRequestService.checkIfOpen(userId, user.getUserId())) {
            Validation.noOpenRequest(map, "User");
            return this.viewUser(request, String.valueOf(userForChange.getUserId()), map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            Validation.inputIsInvalid(map, "Ticket");
            return this.viewUser(request, String.valueOf(userForChange.getUserId()), map);
        }

        userLogicService.updateUser(userForChange, ticketId, user, map);

        userLockRequestService.closeRequest(userId, user.getUserId());

        return this.viewUser(request, String.valueOf(userForChange.getUserId()), map);

    }

    @RequestMapping(value = "/user/cancel/", method = RequestMethod.GET)
    public String cancelUserEdit(HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_USER_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewUser(request, userId, map);
        }

        userLockRequestService.closeRequest(Integer.valueOf(userId), user.getUserId());

        return this.viewUser(request, String.valueOf(userId), map);
    }

    @RequestMapping(value = "/role/", method = RequestMethod.POST)
    public String queryRole(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "roleName", required = false) String roleName,
            ModelMap map) {

        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_ROLE_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(roleId) != 0) {
            inputList[count] = "role_id";
            objectList[count] = Integer.valueOf(roleId);
            count++;
        }

        if (Validation.isStringSet(roleName, 50)) {
            inputList[count] = "role_name";
            objectList[count] = roleName;
            count++;
        }

        List<Role> roleList;

        try {
            roleList = roleService.getRoleListByCriteria(inputList, objectList, count);
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "query");
            roleList = new ArrayList<>();
        }

        map.addAttribute("roleList", roleList);

        this.setRoot("main/update/query/queryrole.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/role/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewRole(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleIdentifier,
            ModelMap map) {

        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_ROLE_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewRole(request, roleIdentifier, map);
        }

        int roleId = Validation.toInteger(roleIdentifier);

        if (roleId == 0) {
            Validation.inputIsInvalid(map, "query");
            this.setRoot("main/update/query/queryrole.jsp", map);
            return "mainview";
        }

        map.addAttribute("role", roleService.getRoleById(roleId));

        this.setRoot("main/update/updaterole.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/role/edit/", method = RequestMethod.POST)
    public String editRole(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_ROLE_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewRole(request, roleId, map);
        }

        if (roleLockRequestService.checkIfOpen(Integer.valueOf(roleId), user.getUserId())) {
            map.addAttribute("edit", true);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE.getActiveFlag());
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE.getActiveFlag());
        } else if (roleLockRequestService.checkIfOutstanding(Integer.valueOf(roleId), user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "Role");
        } else {
            roleLockRequestService.addLockRequest(
                    new RoleLockRequest(new Role(Integer.valueOf(roleId)), user));
            Validation.requestCreated(map, "Role");
        }

        map.addAttribute("role", roleService.getRoleById(Integer.valueOf(roleId)));

        this.setRoot("main/update/updaterole.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/role/save/", method = RequestMethod.POST)
    public String saveRole(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_ROLE_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewRole(request, roleId, map);
        }

        if (!roleLockRequestService.checkIfOpen(Integer.valueOf(roleId), user.getUserId())) {
            Validation.noOpenRequest(map, roleId);
            return this.viewRole(request, roleId, map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketId))) {
            Validation.inputIsInvalid(map, "Ticket");
            return this.viewRole(request, roleId, map);
        }

        try {
            Role roleToAmend = roleService.getRoleById(Integer.valueOf(roleId));

            if (roleToAmend.isProtectedFlag()) {
                Validation.changeProtected(map, "Role");
            } else {
                if (Validation.isStringSet(activeFlag, 1)) {
                    if (roleToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                        if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                            roleService.updateToOnline(Integer.valueOf(roleId), new Ticket(Integer.valueOf(ticketId)), user);
                        } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                            roleService.updateToOffline(Integer.valueOf(roleId), new Ticket(Integer.valueOf(ticketId)), user);
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            Validation.pageError(map);
        }

        roleLockRequestService.closeRequest(Integer.valueOf(roleId), user.getUserId());

        return this.viewRole(request, roleId, map);
    }

    @RequestMapping(value = "/role/cancel/", method = RequestMethod.GET)
    public String cancelRoleEdit(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_ROLE_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewRole(request, roleId, map);
        }

        roleLockRequestService.closeRequest(Integer.valueOf(roleId), user.getUserId());

        return this.viewRole(request, roleId, map);
    }

    @RequestMapping(value = "/queue/", method = RequestMethod.POST)
    public String queryQueue(HttpServletRequest request,
            @RequestParam(value = "queueId", required = false) String queueId,
            @RequestParam(value = "queueName", required = false) String queueName,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_QUEUE_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(queueId) != 0) {
            inputList[count] = "queue_id";
            objectList[count] = Integer.valueOf(queueId);
            count++;
        }

        if (Validation.isStringSet(queueName, 50)) {
            inputList[count] = "queue_name";
            objectList[count] = queueName;
            count++;
        }

        List<Queue> queueList = queueService.getQueueListByCriteria(inputList, objectList, count);

        map.addAttribute("queueList", queueList);
        map.addAttribute("page", "main/update/query/queryqueue.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/queue/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewQueue(HttpServletRequest request,
            @RequestParam(value = "queueId", required = false) String queueIdentifier,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_QUEUE_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        int queueId = Validation.toInteger(queueIdentifier);

        if (queueId == 0) {
            Validation.inputIsInvalid(map, "query");
            this.setRoot("main/update/query/queryqueue.jsp", map);
            return "mainview";
        }

        map.addAttribute("queue", queueService.getQueueById(queueId));
        this.setRoot("main/update/updatequeue.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/queue/edit/", method = RequestMethod.POST)
    public String editQueue(HttpServletRequest request,
            @RequestParam(value = "queueId", required = false) String queueId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_QUEUE_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewQueue(request, queueId, map);
        }

        if (queueLockRequestService.checkIfOpen(Integer.valueOf(queueId), user.getUserId())) {
            map.addAttribute("edit", true);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE);
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE);
        } else if (queueLockRequestService.checkIfOutstanding(Integer.valueOf(queueId), user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "Queue");
        } else {
            queueLockRequestService.addLockRequest(
                    new QueueLockRequest(new Queue(Integer.valueOf(queueId)), user));
            Validation.requestCreated(map, "Queue");
        }

        map.addAttribute("queue", queueService.getQueueById(Integer.valueOf(queueId)));
        map.addAttribute("page", "main/update/updatequeue.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/queue/save/", method = RequestMethod.POST)
    public String saveQueue(HttpServletRequest request,
            @ModelAttribute("queue") Queue newVersion,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_QUEUE_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewQueue(request, String.valueOf(newVersion.getQueueId()), map);
        }

        if (!queueLockRequestService.checkIfOpen(newVersion.getQueueId(), user.getUserId())) {
            Validation.noOpenRequest(map, "Queue");
            return this.viewQueue(request, String.valueOf(newVersion.getQueueId()), map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketId))) {
            Validation.inputIsInvalid(map, "Ticket");
            return this.viewQueue(request, String.valueOf(newVersion.getQueueId()), map);
        }

        boolean queueDeactivated = false;

        try {
            Queue oldVersion = queueService.getQueueById(newVersion.getQueueId());

            if (oldVersion.isProtectedFlag()) {
                Validation.changeProtected(map, "Queue");
            } else {
                if (oldVersion.getActiveFlag().getActiveFlag() != newVersion.getActiveFlag().getActiveFlag()) {
                    if (newVersion.getActiveFlag().getActiveFlag() == ActiveFlag.ONLINE.getActiveFlag()) {
                        queueService.updateToOnline(oldVersion.getQueueId(), new Ticket(Integer.valueOf(ticketId)), user);
                    } else if (newVersion.getActiveFlag().getActiveFlag() == ActiveFlag.OFFLINE.getActiveFlag()) {
                        queueService.updateToOffline(oldVersion.getQueueId(), new Ticket(Integer.valueOf(ticketId)), user);
                        queueDeactivated = true;
                    }
                }

                for (UserQueueCon userQueueCon : newVersion.getUserQueueConList()) {
                    boolean found = false;
                    boolean change = false;
                    int userQueueConId = 0;

                    for (int i = 0; i < oldVersion.getUserQueueConList().size(); i++) {
                        if (oldVersion.getUserQueueConList().get(i).getUser().getUserId() == userQueueCon.getUser().getUserId()) {
                            found = true;
                            userQueueConId = oldVersion.getUserQueueConList().get(i).getUserQueueConId();

                            if (oldVersion.getUserQueueConList().get(i).getActiveFlag().getActiveFlag()
                                    != userQueueCon.getActiveFlag().getActiveFlag()) {

                                if (!((queueDeactivated) && (userQueueCon.getActiveFlag().getActiveFlag() == ActiveFlag.OFFLINE.getActiveFlag()))) {
                                    change = true;
                                }
                            }
                        }
                    }

                    if ((found) && (change)) {
                        if (queueDeactivated) {
                            userQueueCon.setActiveFlag(ActiveFlag.OFFLINE);
                            change = true;
                        }

                        // If queue is being deactivated, the user queue con status flag is overriden
                        if (queueDeactivated) {
                            if (userQueueCon.getActiveFlag().getActiveFlag() != ActiveFlag.OFFLINE.getActiveFlag()) {
                                userQueueCon.setActiveFlag(ActiveFlag.OFFLINE);
                                change = true;
                            }
                        }

                        if (change) {
                            if (userQueueCon.getActiveFlag().getActiveFlag() == ActiveFlag.ONLINE.getActiveFlag()) {
                                userQueueConService.updateToOnline(userQueueConId,
                                        new Ticket(Integer.valueOf(ticketId)), user);
                            } else if (userQueueCon.getActiveFlag().getActiveFlag() == ActiveFlag.OFFLINE.getActiveFlag()) {
                                userQueueConService.updateToOffline(userQueueConId,
                                        new Ticket(Integer.valueOf(ticketId)), user);
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            Validation.pageError(map);
        }

        queueLockRequestService.closeRequest(newVersion.getQueueId(), user.getUserId());

        return this.viewQueue(request, String.valueOf(newVersion.getQueueId()), map);
    }

    @RequestMapping(value = "/queue/cancel/", method = RequestMethod.GET)
    public String cancelQueueEdit(HttpServletRequest request,
            @RequestParam(value = "queueId", required = false) String queueId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_QUEUE_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewQueue(request, String.valueOf(Integer.valueOf(queueId)), map);
        }

        queueLockRequestService.closeRequest(Integer.valueOf(queueId), user.getUserId());

        return this.viewQueue(request, queueId, map);
    }

    @RequestMapping(value = "/application/", method = RequestMethod.POST)
    public String queryApplication(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            @RequestParam(value = "applicationName", required = false) String applicationName,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_APPLICATION_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(applicationId) != 0) {
            inputList[count] = "application_id";
            objectList[count] = Integer.valueOf(applicationId);
            count++;
        }

        if (Validation.isStringSet(applicationName, 50)) {
            inputList[count] = "application_name";
            objectList[count] = applicationName;
            count++;
        }

        List<Application> applicationList = applicationService.getApplicationListByCriteria(inputList, objectList, count);

        map.addAttribute("applicationList", applicationList);
        this.setRoot("main/update/query/queryapplication.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/application/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewApplication(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationIdentifier,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_APPLICATION_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        int applicationId = Validation.toInteger(applicationIdentifier);

        if (applicationId == 0) {
            Validation.inputIsInvalid(map, "query");
            this.setRoot("main/update/query/queryapplication.jsp", map);
            return "mainview";
        }

        map.addAttribute("application", applicationService.getApplicationById(applicationId));
        this.setRoot("main/update/updateapplication.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/application/edit/", method = RequestMethod.POST)
    public String editApplication(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_APPLICATION_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewApplication(request, applicationId, map);
        }

        if (applicationLockRequestService.checkIfOpen(Integer.valueOf(applicationId), user.getUserId())) {
            map.addAttribute("edit", true);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE.getActiveFlag());
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE.getActiveFlag());
        } else if (applicationLockRequestService.checkIfOutstanding(Integer.valueOf(applicationId), user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "Application");
        } else {
            applicationLockRequestService.addLockRequest(
                    new ApplicationLockRequest(new Application(Integer.valueOf(applicationId)), user));
            Validation.requestCreated(map, "Application");
        }

        map.addAttribute("application", applicationService.getApplicationById(Integer.valueOf(applicationId)));
        this.setRoot("main/update/updateapplication.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/application/save/", method = RequestMethod.POST)
    public String saveApplication(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_APPLICATION_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewApplication(request, applicationId, map);
        }

        if (!applicationLockRequestService.checkIfOpen(Integer.valueOf(applicationId), user.getUserId())) {
            Validation.noOpenRequest(map, "Application");
            return this.viewApplication(request, applicationId, map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketId))) {
            Validation.inputIsInvalid(map, "Ticket");
            return this.viewApplication(request, applicationId, map);
        }

        try {
            Application applicationToAmend = applicationService.getApplicationById(Integer.valueOf(applicationId));

            if (applicationToAmend.isProtectedFlag()) {
                Validation.changeProtected(map, "Application");
            } else {
                if (Validation.isStringSet(activeFlag, 1)) {
                    if (applicationToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                        if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                            applicationService.updateToOnline(Integer.valueOf(applicationId), new Ticket(Integer.valueOf(ticketId)), user);
                        } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                            applicationService.updateToOffline(Integer.valueOf(applicationId), new Ticket(Integer.valueOf(ticketId)), user);
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            Validation.pageError(map);
        }

        applicationLockRequestService.closeRequest(Integer.valueOf(applicationId), user.getUserId());

        return this.viewApplication(request, applicationId, map);
    }

    @RequestMapping(value = "/application/cancel/", method = RequestMethod.GET)
    public String cancelApplicationEdit(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_APPLICATION_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewApplication(request, applicationId, map);
        }

        applicationLockRequestService.closeRequest(Integer.valueOf(applicationId), user.getUserId());

        return this.viewApplication(request, applicationId, map);
    }

    @RequestMapping(value = "/severity/", method = RequestMethod.POST)
    public String querySeverity(HttpServletRequest request,
            @RequestParam(value = "severityId", required = false) String severityId,
            @RequestParam(value = "severityLevel", required = false) String severityLevel,
            @RequestParam(value = "severityName", required = false) String severityName,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_QUEUE_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        String[] inputList = new String[3];
        Object[] objectList = new Object[3];
        int count = 0;

        if (Validation.toInteger(severityId) != 0) {
            inputList[count] = "severity_id";
            objectList[count] = Integer.valueOf(severityId);
            count++;
        }

        if (Validation.isStringSet(severityLevel, 2)) {
            inputList[count] = "severity_level";
            objectList[count] = severityLevel;
            count++;
        }

        if (Validation.isStringSet(severityName, 25)) {
            inputList[count] = "severity_name";
            objectList[count] = severityName;
            count++;
        }

        List<Severity> severityList = severityService.getListByCriteria(inputList, objectList, count);

        map.addAttribute("severityList", severityList);
        map.addAttribute("page", "main/update/query/queryseverity.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/severity/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewSeverity(HttpServletRequest request,
            @RequestParam(value = "severityId", required = false) String severityIdentifier,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_SEVERITY_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        int severityId = Validation.toInteger(severityIdentifier);

        if (severityId == 0) {
            Validation.inputIsInvalid(map, "query");
            map.addAttribute("page", "main/update/query/queryseverity.jsp");
            return "mainview";
        }

        map.addAttribute("severity", severityService.getSeverityById(severityId));
        this.setRoot("main/update/updateseverity.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/severity/edit/", method = RequestMethod.POST)
    public String editSeverity(HttpServletRequest request,
            @RequestParam(value = "severityId", required = false) String severityId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_SEVERITY_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewSeverity(request, severityId, map);
        }

        if (severityLockRequestService.checkIfOpen(Integer.valueOf(severityId), user.getUserId())) {
            map.addAttribute("edit", true);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE);
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE);
        } else if (severityLockRequestService.checkIfOutstanding(Integer.valueOf(severityId), user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "Severity");
        } else {
            severityLockRequestService.addLockRequest(
                    new SeverityLockRequest(new Severity(Integer.valueOf(severityId)), user));
            Validation.requestCreated(map, "Severity");
        }

        map.addAttribute("severity", severityService.getSeverityById(Integer.valueOf(severityId)));
        this.setRoot("main/update/updateseverity.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/severity/save/", method = RequestMethod.POST)
    public String saveSeverity(HttpServletRequest request,
            @ModelAttribute("severity") Severity newVersion,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_SEVERITY_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewSeverity(request, String.valueOf(newVersion.getSeverityId()), map);
        }

        if (!severityLockRequestService.checkIfOpen(newVersion.getSeverityId(), user.getUserId())) {
            Validation.noOpenRequest(map, ticketId);
            return this.viewSeverity(request, String.valueOf(newVersion.getSeverityId()), map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketId))) {
            Validation.inputIsInvalid(map, "Ticket");
            return this.viewSeverity(request, String.valueOf(newVersion.getSeverityId()), map);
        }

        try {
            Severity oldVersion = severityService.getSeverityById(newVersion.getSeverityId());

            if (newVersion.getActiveFlag().getActiveFlag() != oldVersion.getActiveFlag().getActiveFlag()) {
                if (newVersion.getActiveFlag().getActiveFlag() == ActiveFlag.ONLINE.getActiveFlag()) {
                    severityService.updateToOnline(oldVersion.getSeverityId(), new Ticket(Integer.valueOf(ticketId)), user);
                } else if (newVersion.getActiveFlag().getActiveFlag() == ActiveFlag.OFFLINE.getActiveFlag()) {
                    severityService.updateToOffline(oldVersion.getSeverityId(), new Ticket(Integer.valueOf(ticketId)), user);
                }
            }
        } catch (NumberFormatException e) {
            Validation.pageError(map);
        }

        severityLockRequestService.closeRequest(newVersion.getSeverityId(), user.getUserId());

        return this.viewSeverity(request, String.valueOf(newVersion.getSeverityId()), map);
    }

    @RequestMapping(value = "/severity/cancel/", method = RequestMethod.GET)
    public String cancelSeverityEdit(HttpServletRequest request,
            @RequestParam(value = "severityId", required = false) String severityId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_SEVERITY_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewSeverity(request, severityId, map);
        }

        severityLockRequestService.closeRequest(Integer.valueOf(severityId), user.getUserId());

        return this.viewSeverity(request, severityId, map);
    }

    @RequestMapping(value = "/configuration/", method = RequestMethod.POST)
    public String queryApplicationControl(HttpServletRequest request,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlId,
            @RequestParam(value = "ticketType", required = false) String ticketTypeId,
            @RequestParam(value = "application", required = false) String applicationId,
            @RequestParam(value = "severity", required = false) String severityId,
            @RequestParam(value = "workflow", required = false) String workflowId,
            @RequestParam(value = "role", required = false) String roleId,
            @RequestParam(value = "slaClock", required = false) String slaClock,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_CONFIGURATION_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        String[] inputList = new String[7];
        Object[] objectList = new Object[7];
        int count = 0;

        if (Validation.toInteger(applicationControlId) != 0) {
            inputList[count] = "application_control_id";
            objectList[count] = Integer.valueOf(applicationControlId);
            count++;
        }

        if (Validation.toInteger(ticketTypeId) != 0) {
            inputList[count] = "ticket_type_id";
            objectList[count] = ticketTypeId;
            count++;
        }

        if (Validation.toInteger(applicationId) != 0) {
            inputList[count] = "application_id";
            objectList[count] = applicationId;
            count++;
        }

        if (Validation.toInteger(severityId) != 0) {
            inputList[count] = "severity_id";
            objectList[count] = severityId;
            count++;
        }

        if (Validation.toInteger(workflowId) != 0) {
            inputList[count] = "workflow_id";
            objectList[count] = workflowId;
            count++;
        }

        if (Validation.toInteger(roleId) != 0) {
            inputList[count] = "role_id";
            objectList[count] = roleId;
            count++;
        }

        if (Validation.toInteger(slaClock) != 0) {
            inputList[count] = "sla_clock";
            objectList[count] = slaClock;
            count++;
        }

        List<ApplicationControl> applicationControlList = applicationControlService.getApplicationControlListByCriteria(inputList, objectList, count);

        map.addAttribute("applicationControlList", applicationControlList);
        return this.configuration(request, map);
    }

    @RequestMapping(value = "/configuration/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewApplicationControl(HttpServletRequest request,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlIdentifier,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_CONFIGURATION_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        int applicationControlId = Validation.toInteger(applicationControlIdentifier);

        if (applicationControlId == 0) {
            Validation.inputIsInvalid(map, "query");
            return this.configuration(request, map);
        }

        map.addAttribute("applicationControl", applicationControlService.getApplicationControlById(applicationControlId, false));
        this.setRoot("main/update/updateconfiguration.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/configuration/edit/", method = RequestMethod.POST)
    public String editApplicationControl(HttpServletRequest request,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_CONFIGURATION_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewApplicationControl(request, applicationControlId, map);
        }

        if (applicationControlLockRequestService.checkIfOpen(Integer.valueOf(applicationControlId), user.getUserId())) {
            map.addAttribute("edit", true);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE.getActiveFlag());
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE.getActiveFlag());
        } else if (applicationControlLockRequestService.checkIfOutstanding(Integer.valueOf(applicationControlId), user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "Configuration");
        } else {
            applicationControlLockRequestService.addLockRequest(
                    new ApplicationControlLockRequest(new ApplicationControl(Integer.valueOf(applicationControlId)), user));
            Validation.requestCreated(map, "Configuration");
        }

        map.addAttribute("applicationControl", applicationControlService.getApplicationControlById(Integer.valueOf(applicationControlId), false));
        this.setRoot("main/update/updateconfiguration.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/configuration/save/", method = RequestMethod.POST)
    public String saveApplicationControl(HttpServletRequest request,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_CONFIGURATION_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewApplicationControl(request, applicationControlId, map);
        }

        if (!applicationControlLockRequestService.checkIfOpen(Integer.valueOf(applicationControlId), user.getUserId())) {
            Validation.noOpenRequest(map, ticketId);
            return this.viewApplicationControl(request, applicationControlId, map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketId))) {
            Validation.inputIsInvalid(map, "Ticket");
            return this.viewApplicationControl(request, applicationControlId, map);
        }

        try {
            ApplicationControl applicationControlToAmend = applicationControlService.getApplicationControlById(Integer.valueOf(applicationControlId), false);

            if (Validation.isStringSet(activeFlag, 1)) {
                if (applicationControlToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                    if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                        applicationControlService.updateToOnline(Integer.valueOf(applicationControlId), new Ticket(Integer.valueOf(ticketId)), user);
                    } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                        applicationControlService.updateToOffline(Integer.valueOf(applicationControlId), new Ticket(Integer.valueOf(ticketId)), user);
                    }
                }
            }
        } catch (NumberFormatException e) {
            Validation.pageError(map);
        }

        applicationControlLockRequestService.closeRequest(Integer.valueOf(applicationControlId), user.getUserId());

        return this.viewApplicationControl(request, applicationControlId, map);
    }

    @RequestMapping(value = "/configuration/cancel/", method = RequestMethod.GET)
    public String cancelApplicationControlEdit(HttpServletRequest request,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_CONFIGURATION_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewApplicationControl(request, applicationControlId, map);
        }

        applicationControlLockRequestService.closeRequest(Integer.valueOf(applicationControlId), user.getUserId());

        return this.viewApplicationControl(request, applicationControlId, map);
    }

    @RequestMapping(value = "/workflow/", method = RequestMethod.POST)
    public String queryWorkflow(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            @RequestParam(value = "workflowName", required = false) String workflowName,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOW_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(workflowId) != 0) {
            inputList[count] = "workflow_id";
            objectList[count] = Integer.valueOf(workflowId);
            count++;
        }

        if (Validation.isStringSet(workflowName, 50)) {
            inputList[count] = "workflow_name";
            objectList[count] = workflowName;
            count++;
        }

        List<Workflow> workflowList = workflowService.getListByCriteria(inputList, objectList, count);

        map.addAttribute("workflowList", workflowList);
        this.setRoot("main/update/query/queryworkflow.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflow/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewWorkflow(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowIdentifier,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOW_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        int workflowId = Validation.toInteger(workflowIdentifier);

        if (workflowId == 0) {
            this.setRoot("main/update/query/queryworkflow.jsp", map);
            Validation.inputIsInvalid(map, "query");
            return "mainview";
        }

        Workflow workflow = workflowService.getWorkflow(Integer.valueOf(workflowId));
        workflow.setWorkflowMap(workflowMapService.getWorkflowMapById(workflow.getWorkflowId()));

        map.addAttribute("workflow", workflow);
        this.setRoot("main/update/updateworkflow.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflow/edit/", method = RequestMethod.POST)
    public String editWorkflow(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOW_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewWorkflow(request, workflowId, map);
        }

        if (workflowLockRequestService.checkIfOpen(Integer.valueOf(workflowId), user.getUserId())) {
            map.addAttribute("edit", true);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE.getActiveFlag());
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE.getActiveFlag());
        } else if (workflowLockRequestService.checkIfOutstanding(Integer.valueOf(workflowId), user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "Workflow");
        } else {
            workflowLockRequestService.addLockRequest(
                    new WorkflowLockRequest(new Workflow(Integer.valueOf(workflowId)), user));
            Validation.requestCreated(map, "Workflow");
        }

        Workflow workflow = workflowService.getWorkflow(Integer.valueOf(workflowId));
        workflow.setWorkflowMap(workflowMapService.getWorkflowMapById(workflow.getWorkflowId()));
        map.addAttribute("workflow", workflow);

        this.setRoot("main/update/updateworkflow.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflow/save/", method = RequestMethod.POST)
    public String saveWorkflow(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOW_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewWorkflow(request, workflowId, map);
        }

        if (!workflowLockRequestService.checkIfOpen(Integer.valueOf(workflowId), user.getUserId())) {
            Validation.noOpenRequest(map, ticketId);
            return this.viewWorkflow(request, workflowId, map);
        }

        if (!ticketService.doesTicketExist(Validation.toInteger(ticketId))) {
            Validation.inputIsInvalid(map, "Ticket");
            return this.viewWorkflow(request, workflowId, map);
        }

        try {
            Workflow workflowToAmend = workflowService.getWorkflow(Integer.valueOf(workflowId));

            if (Validation.isStringSet(activeFlag, 1)) {
                if (workflowToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                    if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                        workflowService.updateToOnline(Integer.valueOf(workflowId), new Ticket(Integer.valueOf(ticketId)), user);
                    } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                        workflowService.updateToOffline(Integer.valueOf(workflowId), new Ticket(Integer.valueOf(ticketId)), user);
                    }
                }
            }
        } catch (NumberFormatException e) {
            Validation.pageError(map);
        }

        workflowLockRequestService.closeRequest(Integer.valueOf(workflowId), user.getUserId());

        return this.viewWorkflow(request, workflowId, map);
    }

    @RequestMapping(value = "/workflow/cancel/", method = RequestMethod.GET)
    public String cancelWorkflowEdit(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOW_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewWorkflow(request, workflowId, map);
        }

        workflowLockRequestService.closeRequest(Integer.valueOf(workflowId), user.getUserId());

        return this.viewWorkflow(request, workflowId, map);
    }

    @RequestMapping(value = "/workflowstatus/", method = RequestMethod.POST)
    public String queryWorkflowStatus(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusId,
            @RequestParam(value = "workflowStatusName", required = false) String workflowStatusName,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOWSTATUS_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(workflowStatusId) != 0) {
            inputList[count] = "workflow_status_id";
            objectList[count] = Integer.valueOf(workflowStatusId);
            count++;
        }

        if (Validation.isStringSet(workflowStatusName, 25)) {
            inputList[count] = "workflow_status_name";
            objectList[count] = workflowStatusName;
            count++;
        }

        List<WorkflowStatus> workflowStatusList = workflowStatusService.getListByCriteria(inputList, objectList, count);

        map.addAttribute("workflowStatusList", workflowStatusList);
        this.setRoot("main/update/query/queryworkflowstatus.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflowstatus/update/", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewWorkflowStatus(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusIdentifier,
            ModelMap map) {
        this.setRoot("menu/update.jsp", map);
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOWSTATUS_VIEW", user, map)) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        int workflowStatusId = Validation.toInteger(workflowStatusIdentifier);

        if (workflowStatusId == 0) {
            Validation.inputIsInvalid(map, "query");
            this.setRoot("main/update/query/queryworkflowstatus.jsp", map);
            return "mainview";
        }

        map.addAttribute("workflowStatus", workflowStatusService.getWorkflowStatus(workflowStatusId));
        this.setRoot("main/update/updateworkflowstatus.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflowstatus/edit/", method = RequestMethod.POST)
    public String editWorkflowStatus(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOWSTATUS_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewWorkflowStatus(request, workflowStatusId, map);
        }

        if (workflowStatusLockRequestService.checkIfOpen(Integer.valueOf(workflowStatusId), user.getUserId())) {
            map.addAttribute("edit", true);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE.getActiveFlag());
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE.getActiveFlag());
        } else if (workflowStatusLockRequestService.checkIfOutstanding(Integer.valueOf(workflowStatusId), user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "WorkflowStatus");
        } else {
            workflowStatusLockRequestService.addLockRequest(
                    new WorkflowStatusLockRequest(new WorkflowStatus(Integer.valueOf(workflowStatusId)), user));
            Validation.requestCreated(map, "WorkflowStatus");
        }

        map.addAttribute("workflowStatus", workflowStatusService.getWorkflowStatus(Integer.valueOf(workflowStatusId)));
        this.setRoot("main/update/updateworkflowstatus.jsp", map);
        return "mainview";
    }

    @RequestMapping(value = "/workflowstatus/save/", method = RequestMethod.POST)
    public String saveWorkflowStatus(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOWSTATUS_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewWorkflowStatus(request, workflowStatusId, map);
        }

        if (!workflowStatusLockRequestService.checkIfOpen(Integer.valueOf(workflowStatusId), user.getUserId())) {
            return this.viewWorkflowStatus(request, workflowStatusId, map);
        }

        if (Validation.toInteger(ticketId) == 0) {
            return this.viewWorkflowStatus(request, workflowStatusId, map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            return this.viewWorkflowStatus(request, workflowStatusId, map);
        }

        try {
            WorkflowStatus workflowStatusToAmend = workflowStatusService.getWorkflowStatus(Integer.valueOf(workflowStatusId));

            if (Validation.isStringSet(activeFlag, 1)) {
                if (workflowStatusToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                    if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                        workflowStatusService.updateToOnline(Integer.valueOf(workflowStatusId), new Ticket(Integer.valueOf(ticketId)), user);
                    } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                        workflowStatusService.updateToOffline(Integer.valueOf(workflowStatusId), new Ticket(Integer.valueOf(ticketId)), user);
                    }
                }
            }
        } catch (NumberFormatException e) {
            Validation.pageError(map);
        }

        workflowStatusLockRequestService.closeRequest(Integer.valueOf(workflowStatusId), user.getUserId());

        return this.viewWorkflowStatus(request, workflowStatusId, map);
    }

    @RequestMapping(value = "/workflowstatus/cancel/", method = RequestMethod.GET)
    public String cancelWorkflowStatusEdit(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!checkService.doesRoleCheckPass("MAINT_WORKFLOWSTATUS_UPDATE", user, map)) {
            Validation.userDoesntHaveRole(map);
            return this.viewWorkflowStatus(request, workflowStatusId, map);
        }

        workflowStatusLockRequestService.closeRequest(Integer.valueOf(workflowStatusId), user.getUserId());

        return this.viewWorkflowStatus(request, workflowStatusId, map);
    }

    private void setRoot(String page, ModelMap map) {
        map.addAttribute("page", page);
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
