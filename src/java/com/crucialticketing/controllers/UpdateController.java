/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daos.services.ApplicationControlLockRequestService;
import com.crucialticketing.daos.services.ApplicationControlService;
import com.crucialticketing.daos.services.ApplicationLockRequestService;
import com.crucialticketing.daos.services.ApplicationService;
import com.crucialticketing.daos.services.QueueLockRequestService;
import com.crucialticketing.daos.services.QueueService;
import com.crucialticketing.daos.services.RoleLockRequestService;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.daos.services.SeverityService;
import com.crucialticketing.daos.services.TicketService;
import com.crucialticketing.daos.services.TicketTypeService;
import com.crucialticketing.daos.services.UserLockRequestService;
import com.crucialticketing.daos.services.UserQueueConService;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.daos.services.WorkflowLockRequestService;
import com.crucialticketing.daos.services.WorkflowMapService;
import com.crucialticketing.daos.services.WorkflowService;
import com.crucialticketing.daos.services.WorkflowStatusLockRequestService;
import com.crucialticketing.daos.services.WorkflowStatusService;
import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.ApplicationControlLockRequest;
import com.crucialticketing.entities.ApplicationLockRequest;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.QueueLockRequest;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleLockRequest;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserLockRequest;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowLockRequest;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStatusLockRequest;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.util.Validation;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @Autowired
    UserLockRequestService userLockRequestService;

    @Autowired
    RoleLockRequestService roleLockRequestService;

    @Autowired
    QueueLockRequestService queueLockRequestService;

    @Autowired
    ApplicationLockRequestService applicationLockRequestService;

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

    @RequestMapping(value = "/query/configuration/", method = RequestMethod.GET)
    public String configuration(ModelMap map) {
        map.addAttribute("page", "main/update/query/queryconfiguration.jsp");
        map.addAttribute("ticketTypeList", ticketTypeService.getTicketTypeList());
        map.addAttribute("applicationList", applicationService.getApplicationList());
        map.addAttribute("severityList", severityService.getList());
        map.addAttribute("workflowList", workflowService.getList());
        map.addAttribute("roleList", roleService.getList());
        return "mainview";
    }

    @RequestMapping(value = "/query/{pagename}/", method = RequestMethod.GET)
    public String index(@PathVariable(value = "pagename") String pageName, ModelMap map) {
        map.addAttribute("page", "main/update/query/query" + pageName + ".jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public String queryUser(HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            ModelMap map) {
        String[] inputList = new String[4];
        Object[] objectList = new Object[4];
        int count = 0;

        if (Validation.toInteger(userId) != 0) {
            inputList[count] = "user_id";
            objectList[count] = Integer.valueOf(userId);
            count++;
        }

        if (Validation.isStringSet(username)) {
            inputList[count] = "username";
            objectList[count] = username;
            count++;
        }

        if (Validation.isStringSet(firstName)) {
            inputList[count] = "first_name";
            objectList[count] = firstName;
            count++;
        }

        if (Validation.isStringSet(lastName)) {
            inputList[count] = "last_name";
            objectList[count] = lastName;
            count++;
        }

        List<User> userList = userService.getUserListByCriteria(inputList, objectList, count, false);

        map.addAttribute("userList", userList);
        map.addAttribute("page", "main/update/query/queryuser.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/update/", method = RequestMethod.POST)
    public String viewUser(HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userIdentifier,
            ModelMap map) {

        int userId = Validation.toInteger(userIdentifier);

        if (userId == 0) {
            map.addAttribute("page", "main/update/query/queryuser.jsp");
            return "mainview";
        }

        User retrievedUser = userService.getUserById(userId, false);
        retrievedUser.setUserRoleConList(userRoleConService.getRoleListByUserId(retrievedUser.getUserId()));

        map.addAttribute("user", retrievedUser);
        map.addAttribute("page", "main/update/updateuser.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/edit/", method = RequestMethod.POST)
    public String editUser(HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userId,
            ModelMap map) {
        boolean editMode = false;
        User user = (User) request.getSession().getAttribute("user");

        if (userLockRequestService.checkIfOpen(Integer.valueOf(userId), user.getUserId())) {
            editMode = true;
            map.addAttribute("edit", editMode);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE.getActiveFlag());
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE.getActiveFlag());
        } else if (userLockRequestService.checkIfOutstanding(Integer.valueOf(userId), user.getUserId())) {
            Validation.requestAlreadyOustanding(map, "User");
        } else {
            userLockRequestService.addUserLockRequest(
                    new UserLockRequest(new User(Integer.valueOf(userId)), user));
            Validation.requestCreated(map, "User");
        }

        User retrievedUser = userService.getUserById(Integer.valueOf(userId), editMode);
        retrievedUser.setUserRoleConList(userRoleConService.getRoleListByUserId(retrievedUser.getUserId()));

        map.addAttribute("user", retrievedUser);
        map.addAttribute("page", "main/update/updateuser.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/save/", method = RequestMethod.POST)
    public String saveUser(HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "contact", required = false) String contact,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!userLockRequestService.checkIfOpen(Integer.valueOf(userId), user.getUserId())) {
            return this.viewUser(request, userId, map);
        }

        if (Validation.toInteger(ticketId) == 0) {
            return this.viewUser(request, userId, map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            return this.viewUser(request, userId, map);
        }

        PasswordHash passwordHash = new PasswordHash();

        try {
            User userToAmend = userService.getUserById(Integer.valueOf(userId), true);

            if (Validation.isStringSet(password)) {
                String hash = passwordHash.createHash(password);

                if (!userToAmend.getSecure().getHash().equals(hash)) {
                    userService.updateHash(Integer.valueOf(userId), hash);
                }
            }

            if (Validation.isStringSet(firstName)) {
                if (!userToAmend.getFirstName().equals(firstName)) {
                    userService.updateFirstName(Integer.valueOf(userId), firstName);
                }
            }

            if (Validation.isStringSet(lastName)) {
                if (!userToAmend.getLastName().equals(lastName)) {
                    userService.updateLastName(Integer.valueOf(userId), lastName);
                }
            }

            if (Validation.isStringSet(email)) {
                if (!userToAmend.getEmailAddress().equals(email)) {
                    userService.updateEmail(Integer.valueOf(userId), email);
                }
            }

            if (Validation.isStringSet(contact)) {
                if (!userToAmend.getContact().equals(contact)) {
                    userService.updateContact(Integer.valueOf(userId), contact);
                }
            }

            if (Validation.isStringSet(activeFlag)) {
                if (userToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                    if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                        userService.updateToOnline(Integer.valueOf(userId), new Ticket(Integer.valueOf(ticketId)), user);
                    } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                        userService.updateToOffline(Integer.valueOf(userId), new Ticket(Integer.valueOf(ticketId)), user);
                    }
                }
            }
        } catch (NumberFormatException | NoSuchAlgorithmException | InvalidKeySpecException e) {
        }

        return this.viewUser(request, userId, map);

    }

    @RequestMapping(value = "/role/", method = RequestMethod.POST)
    public String queryRole(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "roleName", required = false) String roleName,
            ModelMap map) {
        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(roleId) != 0) {
            inputList[count] = "role_id";
            objectList[count] = Integer.valueOf(roleId);
            count++;
        }

        if (Validation.isStringSet(roleName)) {
            inputList[count] = "role_name";
            objectList[count] = roleName;
            count++;
        }

        List<Role> roleList = roleService.getRoleListByCriteria(inputList, objectList, count);

        map.addAttribute("roleList", roleList);
        map.addAttribute("page", "main/update/query/queryrole.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/role/update/", method = RequestMethod.POST)
    public String viewRole(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleIdentifier,
            ModelMap map) {

        int roleId = Validation.toInteger(roleIdentifier);

        if (roleId == 0) {
            map.addAttribute("page", "main/update/query/queryrole.jsp");
            return "mainview";
        }

        map.addAttribute("role", roleService.getRoleById(roleId));
        map.addAttribute("page", "main/update/updaterole.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/role/edit/", method = RequestMethod.POST)
    public String editRole(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

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
        map.addAttribute("page", "main/update/updaterole.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/role/save/", method = RequestMethod.POST)
    public String saveRole(HttpServletRequest request,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!roleLockRequestService.checkIfOpen(Integer.valueOf(roleId), user.getUserId())) {
            return this.viewUser(request, roleId, map);
        }

        if (Validation.toInteger(ticketId) == 0) {
            return this.viewUser(request, roleId, map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            return this.viewUser(request, roleId, map);
        }

        try {
            Role roleToAmend = roleService.getRoleById(Integer.valueOf(roleId));

            if (roleToAmend.isProtectedFlag()) {
                Validation.changeProtected(map, "Role");
            } else {
                if (Validation.isStringSet(activeFlag)) {
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
        }

        return this.viewRole(request, roleId, map);
    }

    @RequestMapping(value = "/queue/", method = RequestMethod.POST)
    public String queryQueue(HttpServletRequest request,
            @RequestParam(value = "queueId", required = false) String queueId,
            @RequestParam(value = "queueName", required = false) String queueName,
            ModelMap map) {
        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(queueId) != 0) {
            inputList[count] = "queue_id";
            objectList[count] = Integer.valueOf(queueId);
            count++;
        }

        if (Validation.isStringSet(queueName)) {
            inputList[count] = "queue_name";
            objectList[count] = queueName;
            count++;
        }

        List<Queue> queueList = queueService.getQueueListByCriteria(inputList, objectList, count);

        map.addAttribute("queueList", queueList);
        map.addAttribute("page", "main/update/query/queryqueue.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/queue/update/", method = RequestMethod.POST)
    public String viewQueue(HttpServletRequest request,
            @RequestParam(value = "queueId", required = false) String queueIdentifier,
            ModelMap map) {

        int queueId = Validation.toInteger(queueIdentifier);

        if (queueId == 0) {
            map.addAttribute("page", "main/update/query/queryqueue.jsp");
            return "mainview";
        }

        map.addAttribute("queue", queueService.getQueueById(queueId));
        map.addAttribute("page", "main/update/updatequeue.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/queue/edit/", method = RequestMethod.POST)
    public String editQueue(HttpServletRequest request,
            @RequestParam(value = "queueId", required = false) String queueId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

        if (queueLockRequestService.checkIfOpen(Integer.valueOf(queueId), user.getUserId())) {
            map.addAttribute("edit", true);
            map.addAttribute("activeFlagOnline", ActiveFlag.ONLINE.getActiveFlag());
            map.addAttribute("activeFlagOffline", ActiveFlag.OFFLINE.getActiveFlag());
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
            @RequestParam(value = "queueId", required = false) String queueId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!queueLockRequestService.checkIfOpen(Integer.valueOf(queueId), user.getUserId())) {
            return this.viewUser(request, queueId, map);
        }

        if (Validation.toInteger(ticketId) == 0) {
            return this.viewUser(request, queueId, map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            return this.viewUser(request, queueId, map);
        }

        try {
            Queue queueToAmend = queueService.getQueueById(Integer.valueOf(queueId));

            if (queueToAmend.isProtectedFlag()) {
                Validation.changeProtected(map, "Queue");
            } else {
                if (Validation.isStringSet(activeFlag)) {
                    if (queueToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                        if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                            queueService.updateToOnline(Integer.valueOf(queueId), new Ticket(Integer.valueOf(ticketId)), user);
                        } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                            queueService.updateToOffline(Integer.valueOf(queueId), new Ticket(Integer.valueOf(ticketId)), user);
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
        }

        return this.viewQueue(request, queueId, map);
    }

    @RequestMapping(value = "/application/", method = RequestMethod.POST)
    public String queryApplication(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            @RequestParam(value = "applicationName", required = false) String applicationName,
            ModelMap map) {
        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(applicationId) != 0) {
            inputList[count] = "application_id";
            objectList[count] = Integer.valueOf(applicationId);
            count++;
        }

        if (Validation.isStringSet(applicationName)) {
            inputList[count] = "application_name";
            objectList[count] = applicationName;
            count++;
        }

        List<Application> applicationList = applicationService.getApplicationListByCriteria(inputList, objectList, count);

        map.addAttribute("applicationList", applicationList);
        map.addAttribute("page", "main/update/query/queryapplication.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/application/update/", method = RequestMethod.POST)
    public String viewApplication(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationIdentifier,
            ModelMap map) {

        int applicationId = Validation.toInteger(applicationIdentifier);

        if (applicationId == 0) {
            map.addAttribute("page", "main/update/query/queryapplication.jsp");
            return "mainview";
        }

        map.addAttribute("application", applicationService.getApplicationById(applicationId));
        map.addAttribute("page", "main/update/updateapplication.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/application/edit/", method = RequestMethod.POST)
    public String editApplication(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

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
        map.addAttribute("page", "main/update/updateapplication.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/application/save/", method = RequestMethod.POST)
    public String saveApplication(HttpServletRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!applicationLockRequestService.checkIfOpen(Integer.valueOf(applicationId), user.getUserId())) {
            return this.viewUser(request, applicationId, map);
        }

        if (Validation.toInteger(ticketId) == 0) {
            return this.viewUser(request, applicationId, map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            return this.viewUser(request, applicationId, map);
        }

        try {
            Application applicationToAmend = applicationService.getApplicationById(Integer.valueOf(applicationId));

            if (applicationToAmend.isProtectedFlag()) {
                Validation.changeProtected(map, "Application");
            } else {
                if (Validation.isStringSet(activeFlag)) {
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
        }

        return this.viewApplication(request, applicationId, map);
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
        return this.configuration(map);
    }

    @RequestMapping(value = "/configuration/update/", method = RequestMethod.POST)
    public String viewApplicationControl(HttpServletRequest request,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlIdentifier,
            ModelMap map) {

        int applicationControlId = Validation.toInteger(applicationControlIdentifier);

        if (applicationControlId == 0) {
            return this.configuration(map);
        }

        map.addAttribute("applicationControl", applicationControlService.getApplicationControlById(applicationControlId, false));
        map.addAttribute("page", "main/update/updateconfiguration.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/configuration/edit/", method = RequestMethod.POST)
    public String editApplicationControl(HttpServletRequest request,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

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
        map.addAttribute("page", "main/update/updateconfiguration.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/configuration/save/", method = RequestMethod.POST)
    public String saveApplicationControl(HttpServletRequest request,
            @RequestParam(value = "applicationControlId", required = false) String applicationControlId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!applicationControlLockRequestService.checkIfOpen(Integer.valueOf(applicationControlId), user.getUserId())) {
            return this.viewUser(request, applicationControlId, map);
        }

        if (Validation.toInteger(ticketId) == 0) {
            return this.viewUser(request, applicationControlId, map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            return this.viewUser(request, applicationControlId, map);
        }

        try {
            ApplicationControl applicationControlToAmend = applicationControlService.getApplicationControlById(Integer.valueOf(applicationControlId), false);

            if (Validation.isStringSet(activeFlag)) {
                if (applicationControlToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                    if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                        applicationControlService.updateToOnline(Integer.valueOf(applicationControlId), new Ticket(Integer.valueOf(ticketId)), user);
                    } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                        applicationControlService.updateToOffline(Integer.valueOf(applicationControlId), new Ticket(Integer.valueOf(ticketId)), user);
                    }
                }
            }
        } catch (NumberFormatException e) {
        }

        return this.viewApplicationControl(request, applicationControlId, map);
    }

    @RequestMapping(value = "/workflow/", method = RequestMethod.POST)
    public String queryWorkflow(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            @RequestParam(value = "workflowName", required = false) String workflowName,
            ModelMap map) {
        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(workflowId) != 0) {
            inputList[count] = "workflow_id";
            objectList[count] = Integer.valueOf(workflowId);
            count++;
        }

        if (Validation.isStringSet(workflowName)) {
            inputList[count] = "workflow_name";
            objectList[count] = workflowName;
            count++;
        }

        List<Workflow> workflowList = workflowService.getListByCriteria(inputList, objectList, count);

        map.addAttribute("workflowList", workflowList);
        map.addAttribute("page", "main/update/query/queryworkflow.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflow/update/", method = RequestMethod.POST)
    public String viewWorkflow(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowIdentifier,
            ModelMap map) {

        int workflowId = Validation.toInteger(workflowIdentifier);

        if (workflowId == 0) {
            map.addAttribute("page", "main/update/query/queryworkflow.jsp");
            return "mainview";
        }

        Workflow workflow = workflowService.getWorkflow(Integer.valueOf(workflowId));
        workflow.setWorkflowMap(workflowMapService.getWorkflowMapById(workflow.getWorkflowId()));

        map.addAttribute("workflow", workflow);
        map.addAttribute("page", "main/update/updateworkflow.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflow/edit/", method = RequestMethod.POST)
    public String editWorkflow(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

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

        map.addAttribute("page", "main/update/updateworkflow.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflow/save/", method = RequestMethod.POST)
    public String saveWorkflow(HttpServletRequest request,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!workflowLockRequestService.checkIfOpen(Integer.valueOf(workflowId), user.getUserId())) {
            return this.viewUser(request, workflowId, map);
        }

        if (Validation.toInteger(ticketId) == 0) {
            return this.viewUser(request, workflowId, map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            return this.viewUser(request, workflowId, map);
        }

        try {
            Workflow workflowToAmend = workflowService.getWorkflow(Integer.valueOf(workflowId));

            if (Validation.isStringSet(activeFlag)) {
                if (workflowToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                    if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                        workflowService.updateToOnline(Integer.valueOf(workflowId), new Ticket(Integer.valueOf(ticketId)), user);
                    } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                        workflowService.updateToOffline(Integer.valueOf(workflowId), new Ticket(Integer.valueOf(ticketId)), user);
                    }
                }
            }
        } catch (NumberFormatException e) {
        }

        return this.viewWorkflow(request, workflowId, map);
    }

    @RequestMapping(value = "/workflowstatus/", method = RequestMethod.POST)
    public String queryWorkflowStatus(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusId,
            @RequestParam(value = "workflowStatusName", required = false) String workflowStatusName,
            ModelMap map) {
        String[] inputList = new String[2];
        Object[] objectList = new Object[2];
        int count = 0;

        if (Validation.toInteger(workflowStatusId) != 0) {
            inputList[count] = "workflow_status_id";
            objectList[count] = Integer.valueOf(workflowStatusId);
            count++;
        }

        if (Validation.isStringSet(workflowStatusName)) {
            inputList[count] = "workflow_status_name";
            objectList[count] = workflowStatusName;
            count++;
        }

        List<WorkflowStatus> workflowStatusList = workflowStatusService.getListByCriteria(inputList, objectList, count);

        map.addAttribute("workflowStatusList", workflowStatusList);
        map.addAttribute("page", "main/update/query/queryworkflowstatus.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflowstatus/update/", method = RequestMethod.POST)
    public String viewWorkflowStatus(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusIdentifier,
            ModelMap map) {

        int workflowStatusId = Validation.toInteger(workflowStatusIdentifier);

        if (workflowStatusId == 0) {
            map.addAttribute("page", "main/update/query/queryworkflowstatus.jsp");
            return "mainview";
        }

        map.addAttribute("workflowStatus", workflowStatusService.getWorkflowStatus(workflowStatusId));
        map.addAttribute("page", "main/update/updateworkflowstatus.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflowstatus/edit/", method = RequestMethod.POST)
    public String editWorkflowStatus(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusId,
            ModelMap map) {
        User user = (User) request.getSession().getAttribute("user");

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
        map.addAttribute("page", "main/update/updateworkflowstatus.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/workflowstatus/save/", method = RequestMethod.POST)
    public String saveWorkflowStatus(HttpServletRequest request,
            @RequestParam(value = "workflowStatusId", required = false) String workflowStatusId,
            @RequestParam(value = "activeFlag", required = false) String activeFlag,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (!workflowStatusLockRequestService.checkIfOpen(Integer.valueOf(workflowStatusId), user.getUserId())) {
            return this.viewUser(request, workflowStatusId, map);
        }

        if (Validation.toInteger(ticketId) == 0) {
            return this.viewUser(request, workflowStatusId, map);
        }

        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            return this.viewUser(request, workflowStatusId, map);
        }

        try {
            WorkflowStatus workflowStatusToAmend = workflowStatusService.getWorkflowStatus(Integer.valueOf(workflowStatusId));

            if (Validation.isStringSet(activeFlag)) {
                if (workflowStatusToAmend.getActiveFlag().getActiveFlag() != Integer.valueOf(activeFlag)) {
                    if (Integer.valueOf(activeFlag) == ActiveFlag.ONLINE.getActiveFlag()) {
                        workflowStatusService.updateToOnline(Integer.valueOf(workflowStatusId), new Ticket(Integer.valueOf(ticketId)), user);
                    } else if (Integer.valueOf(activeFlag) == ActiveFlag.OFFLINE.getActiveFlag()) {
                        workflowStatusService.updateToOffline(Integer.valueOf(workflowStatusId), new Ticket(Integer.valueOf(ticketId)), user);
                    }
                }
            }
        } catch (NumberFormatException e) {
        }

        return this.viewWorkflowStatus(request, workflowStatusId, map);
    }
}
