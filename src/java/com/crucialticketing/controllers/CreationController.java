/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daos.services.ApplicationControlService;
import com.crucialticketing.daos.services.ApplicationService;
import com.crucialticketing.daos.services.QueueService;
import com.crucialticketing.entities.User;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.daos.services.SeverityService;
import com.crucialticketing.daos.services.TicketService;
import com.crucialticketing.daos.services.TicketTypeService;
import com.crucialticketing.daos.services.UserQueueConService;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.daos.services.WorkflowMapService;
import com.crucialticketing.daos.services.WorkflowService;
import com.crucialticketing.daos.services.WorkflowStatusService;
import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.UserRoleCon;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStep;
import com.crucialticketing.util.Validation;
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

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public String createUserForm(ModelMap map) {
        map.addAttribute("roleList", roleService.getOnlineRoleList());
        map.addAttribute("user", new User());
        map.addAttribute("page", "main/create/createuser.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/create/", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request,
            @ModelAttribute("user") User userForCreation,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Gets user off of the session
        User user = (User) request.getSession().getAttribute("user");

        // Adds return values which will be used if anything fails
        // Otherwise they will be discarded on 
        map.addAttribute("user", userForCreation);
        map.addAttribute("page", "main/create/createuser.jsp");

        // Checks the role needed for this exists and is not deactivated
        if (!roleService.doesRoleExistInOnline("MAINT_USER_CREATION")) {
            Validation.databaseError(map);
            return "mainview";
        }

        // Checks if the user has the correct role and it is active
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_USER_CREATION").getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        // Checks if ticket is set
        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket");
            return "mainview";
        }

        // Checks if ticket is valid
        int convTicketId;
        try {
            convTicketId = Integer.valueOf(ticketId);

            if (!ticketService.doesTicketExist(convTicketId)) {
                Validation.fieldAlreadyExists(map, "Ticket");
                return "mainview";
            }
        } catch (Exception e) {
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
        int userId = userService.insertUser(userForCreation, new Ticket(convTicketId), user);

        for (UserRoleCon userRoleCon : userForCreation.getUserRoleConList()) {
            userRoleCon.getUser().setUserId(userId);
            userRoleCon.setActiveFlag(userForCreation.getActiveFlag());

            int userRoleConId = userRoleConService.insertUserRoleCon(userRoleCon, true, new Ticket(convTicketId), user);
            userRoleConService.updateToUnprocessed(userRoleConId, new Ticket(convTicketId), user);
        }

        userService.updateToUnprocessed(userForCreation.getUserId(), new Ticket(convTicketId), user);

        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

    @RequestMapping(value = "/role/", method = RequestMethod.GET)
    public String createRoleForm(ModelMap map) {
        map.addAttribute("role", new Role());
        map.addAttribute("page", "main/create/createrole.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/role/create/", method = RequestMethod.POST)
    public String createRole(HttpServletRequest request,
            @ModelAttribute("role") Role roleForCreation,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Check if user has correct role to do this
        User user = (User) request.getSession().getAttribute("user");

        // Sets defaults incase any errors occur - will be discarded/overriden on success
        map.addAttribute("role", roleForCreation);
        map.addAttribute("page", "main/create/createrole.jsp");

        // Checks if role is active
        if (!roleService.doesRoleExistInOnline("MAINT_ROLE_CREATION")) {
            Validation.databaseError(map);
            return "mainview";
        }

        // Checks if the user has this role
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_ROLE_CREATION").getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        // Checks if ticket is set
        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket");
            return "mainview";
        }

        // Checks if ticket is valid
        int convTicketId;
        try {
            convTicketId = Integer.valueOf(ticketId);

            if (!ticketService.doesTicketExist(convTicketId)) {
                Validation.fieldAlreadyExists(map, "Ticket");
                return "mainview";
            }
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Checks if role exists already
        if (roleService.doesRoleExist(roleForCreation.getRoleName())) {
            Validation.fieldAlreadyExists(map, "Role");
            return "mainview";
        }

        roleService.insertRole(roleForCreation, new Ticket(convTicketId), user);
        roleService.updateToUnprocessed(roleForCreation.getRoleId(), new Ticket(convTicketId), user);

        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

    @RequestMapping(value = "/queue/", method = RequestMethod.GET)
    public String createQueueForm(ModelMap map) {
        map.addAttribute("userList", userService.getOnlineUserList(false));
        map.addAttribute("queue", new Queue());
        map.addAttribute("page", "main/create/createqueue.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/queue/create/", method = RequestMethod.POST)
    public String createQueue(HttpServletRequest request,
            @ModelAttribute("queue") Queue queue,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Check if user has correct role to do this
        User user = (User) request.getSession().getAttribute("user");

        // Sets defaults incase of error - discarded on success
        map.addAttribute("queue", queue);
        map.addAttribute("page", "main/create/createqueue.jsp");

        // Checks if the role is active
        if (!roleService.doesRoleExistInOnline("MAINT_QUEUE_CREATION")) {
            Validation.databaseError(map);
            return "mainview";
        }

        // Checks if the user has this role
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_QUEUE_CREATION").getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        // Checks if ticket is set
        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket");
            return "mainview";
        }

        // Checks if ticket is valid
        int convTicketId;
        try {
            convTicketId = Integer.valueOf(ticketId);

            if (!ticketService.doesTicketExist(convTicketId)) {
                Validation.fieldAlreadyExists(map, "Ticket");
                return "mainview";
            }
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Check if queue name already exists
        if (queueService.doesQueueExist(queue.getQueueName())) {
            Validation.fieldAlreadyExists(map, "Queue");
            return "mainview";
        }

        // If users have been selected, checks if they are valid
        for (int i = 0, length = queue.getUserList().size(); i < length; i++) {
            UserQueueCon userQueueCon = queue.getUserList().get(i);

            if (!userService.doesUserExistInOnline(userQueueCon.getUser().getUserId())) {
                queue.getUserList().remove(userQueueCon);
                i--;
                length--;
            }
        }

        // Inserts queue into queue request
        queueService.insertQueue(queue, new Ticket(convTicketId), user);

        // Inserts any user connection
        for (UserQueueCon userQueueCon : queue.getUserList()) {
            userQueueCon.getQueue().setQueueId(queue.getQueueId());
            userQueueCon.getQueue().setActiveFlag(queue.getActiveFlag());

            int userQueueConId = userQueueConService.insertUserQueueCon(userQueueCon, true, new Ticket(convTicketId), user);
            userQueueConService.updateToUnprocessed(userQueueConId, new Ticket(convTicketId), user);
        }

        queueService.updateToUnprocessed(queue.getQueueId(), new Ticket(convTicketId), user);

        // Success
        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

    @RequestMapping(value = "/application/", method = RequestMethod.GET)
    public String createApplicationForm(ModelMap map) {
        map.addAttribute("application", new Application());
        map.addAttribute("page", "main/create/createapplication.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/application/create/", method = RequestMethod.POST)
    public String createApplication(HttpServletRequest request,
            @ModelAttribute("application") Application application,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Gets user off session
        User user = (User) request.getSession().getAttribute("user");

        // Sets defaults incase of error - discarded on success
        map.addAttribute("application", application);
        map.addAttribute("page", "main/create/createapplication.jsp");

        // Checks if the role is active
        if (!roleService.doesRoleExistInOnline("MAINT_APPLICATION_CREATION")) {
            Validation.databaseError(map);
            return "mainview";
        }

        // Checks if the user has this role
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_APPLICATION_CREATION").getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        // Checks if ticket is set
        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket");
            return "mainview";
        }

        // Checks if ticket is valid
        int convTicketId;
        try {
            convTicketId = Integer.valueOf(ticketId);

            if (!ticketService.doesTicketExist(convTicketId)) {
                Validation.fieldAlreadyExists(map, "Ticket");
                return "mainview";
            }
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Check if application name already exists
        if (applicationService.doesApplicationExist(application.getApplicationName())) {
            Validation.fieldAlreadyExists(map, "Application");
            return "mainview";
        }

        // Inserts application 
        applicationService.insertApplication(application, new Ticket(convTicketId), user);
        applicationService.updateToUnprocessed(application.getApplicationId(), new Ticket(convTicketId), user);

        // Success
        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

    @RequestMapping(value = "/severity/", method = RequestMethod.GET)
    public String createSeverityForm(ModelMap map) {
        map.addAttribute("severity", new Severity());
        map.addAttribute("page", "main/create/createseverity.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/severity/create/", method = RequestMethod.POST)
    public String createSeverity(HttpServletRequest request,
            @ModelAttribute("severity") Severity severity,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Gets user off session
        User user = (User) request.getSession().getAttribute("user");

        // Sets defaults incase of error - discarded on success
        map.addAttribute("severity", severity);
        map.addAttribute("page", "main/create/createseverity.jsp");

        // Checks if the role is active
        if (!roleService.doesRoleExistInOnline("MAINT_SEVERITY_CREATION")) {
            Validation.databaseError(map);
            return "mainview";
        }

        // Checks if the user has this role
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_SEVERITY_CREATION").getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        // Checks if ticket is set
        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket");
            return "mainview";
        }

        // Checks if ticket is valid
        int convTicketId;
        try {
            convTicketId = Integer.valueOf(ticketId);

            if (!ticketService.doesTicketExist(convTicketId)) {
                Validation.fieldAlreadyExists(map, "Ticket");
                return "mainview";
            }
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Check severity level is a number
        try {
            int test = Integer.valueOf(ticketId);
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "Severity Level");
            return "mainview";
        }

        // Check if severity level already exists
        if (severityService.doesSeverityExistByLevel(severity.getSeverityLevel())) {
            Validation.fieldAlreadyExists(map, "Severity");
            return "mainview";
        }

        // Inserts Severity 
        severityService.insertSeverity(severity, new Ticket(convTicketId), user);
        severityService.updateToUnprocessed(severity.getSeverityId(), new Ticket(convTicketId), user);

        // Success
        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

    @RequestMapping(value = "/configuration/", method = RequestMethod.GET)
    public String createConfigurationForm(ModelMap map) {
        map.addAttribute("ticketTypeList", ticketTypeService.getTicketTypeList());
        map.addAttribute("severityList", severityService.getOnlineSeverityList());
        map.addAttribute("applicationList", applicationService.getOnlineApplicationList());
        map.addAttribute("workflowList", workflowService.getOnlineWorkflowList());
        map.addAttribute("applicationControl", new ApplicationControl());
        map.addAttribute("roleList", roleService.getOnlineRoleList());
        map.addAttribute("page", "main/create/createconfiguration.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/configuration/create/", method = RequestMethod.POST)
    public String createConfiguration(HttpServletRequest request,
            @ModelAttribute("applicationControl") ApplicationControl applicationControl,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Gets user off session
        User user = (User) request.getSession().getAttribute("user");

        // Sets defaults incase of error - discarded on success
        createConfigurationForm(map);

        // Checks if the role is active
        if (!roleService.doesRoleExistInOnline("MAINT_CONFIGURATION_CREATION")) {
            Validation.databaseError(map);
            return "mainview";
        }

        // Checks if the user has this role
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_CONFIGURATION_CREATION").getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return "mainview";
        }

        // Checks if ticket is set
        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket");
            return "mainview";
        }

        // Checks if ticket is valid
        int convTicketId;
        try {
            convTicketId = Integer.valueOf(ticketId);

            if (!ticketService.doesTicketExist(convTicketId)) {
                Validation.fieldAlreadyExists(map, "Ticket");
                return "mainview";
            }
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "Ticket");
            return "mainview";
        }

        // Checks if each configuration is valid
        if (!ticketTypeService.doesTicketTypeExist(applicationControl.getTicketType().getTicketTypeId())) {

        }

        if (!severityService.doesSeverityExistInOnlineById(applicationControl.getSeverity().getSeverityId())) {

        }

        if (!applicationService.doesApplicationExistInOnline(applicationControl.getApplication().getApplicationId())) {

        }

        if (!workflowService.doesWorkflowExistInOnline(applicationControl.getWorkflow().getWorkflowId())) {

        }

        // Checks if configuration already exists
        if (applicationControlService.doesApplicationControlExistInOnline(
                applicationControl.getTicketType().getTicketTypeId(),
                applicationControl.getApplication().getApplicationId(),
                applicationControl.getSeverity().getSeverityId())) {
            // Configuration already exists   
        }

        applicationControlService.insertApplicationControl(applicationControl, new Ticket(convTicketId), user);

        // Success
        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflow/", method = RequestMethod.GET)
    public String createWorkflowFormPart1(ModelMap map) {
        map.addAttribute("workflowStatusList", workflowStatusService.getOnlineWorkflowStatusList());
        map.addAttribute("workflow", new Workflow());
        map.addAttribute("page", "main/create/workflow/createworkflowp1.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflow/p2/", method = RequestMethod.POST)
    public String createWorkflowPage2(HttpServletRequest request,
            @ModelAttribute("workflow") Workflow workflow,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        map.addAttribute("ticketId", ticketId);
        
        for (WorkflowStep workflowStep : workflow.getWorkflowMap().getWorkflow()) {
            if (!workflowStatusService.doesWorkflowStatusExistInOnline(workflowStep.getWorkflowStatus().getWorkflowStatusId())) {
                this.createWorkflowFormPart1(map);
                return "mainview";
            }
            workflowStep.getWorkflowStatus().setWorkflowStatusName(
                    workflowStatusService.getWorkflowStatus(
                            workflowStep.getWorkflowStatus().getWorkflowStatusId()).getWorkflowStatusName());
        }

        map.addAttribute("roleList", roleService.getOnlineRoleList());
        map.addAttribute("queueList", queueService.getOnlineQueueList());

        map.addAttribute("workflow", workflow);
        map.addAttribute("page", "main/create/workflow/createworkflowp2.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflow/create/", method = RequestMethod.POST)
    public String createWorkflow(HttpServletRequest request,
            @ModelAttribute("workflow") Workflow workflow, 
            @RequestParam(value = "ticketId", required = false) String ticketId, 
            ModelMap map) {
     
        this.createWorkflowFormPart1(map);
        
        // Gets user off of the session
        User user = (User) request.getSession().getAttribute("user");
        
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
                queueCheckList.put(nextWorkflowStep.getQueue().getQueueId(), nextWorkflowStep.getQueue());

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
        List<WorkflowStatus> workflowStatusList = workflowStatusService.getOnlineWorkflowStatusList();
        mapIterator = statusCheckList.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) mapIterator.next();
            found = false;
            for (WorkflowStatus workflowStatus : workflowStatusList) {
                if (workflowStatus.getWorkflowStatusId() == (int) pair.getKey()) {

                    // Checks if status is a base or closure node - validates against db
                    for (Integer baseNode : baseNodeCheckList) {
                        if (baseNode == workflowStatus.getWorkflowStatusId()) {
                            if (!workflowStatus.isBaseWorkflowStatus()) {
                                Validation.illegalWorkflow(map);
                                return "mainview";
                            }
                        }
                    }
                    for (Integer closureNode : closureNodeCheckList) {
                        if (closureNode == workflowStatus.getWorkflowStatusId()) {
                            if (!workflowStatus.isClosureWorkflowStatus()) {
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
      if(workflowService.doesWorkflowExistInOnline(workflow.getWorkflowName())) {
          Validation.fieldAlreadyExists(map, "Workflow");
          return "mainview";
      }
      
        // All status', queues and roles are valid
        // All paths are complete
        // No duplicate or abandoned paths
      
        // Adds workflow template
      workflowService.insertWorkflow(workflow, new Ticket(Integer.valueOf(ticketId)), user);
      
      workflowMapService.insertWorkflowStep(workflow);
      
        // Adds workflow steps
        // Sets workflow template to unprocessed
        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");
        return "mainview";
    }
}
