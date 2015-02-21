/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daos.services.QueueRequestService;
import com.crucialticketing.daos.services.RoleRequestService;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRequest;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.daos.services.UserQueueConService;
import com.crucialticketing.daos.services.UserRequestService;
import com.crucialticketing.daos.services.UserRoleConRequestService;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.entities.QueueRequest;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleRequest;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.UserRoleConRequest;
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
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRequestService userRequestService;

    @Autowired
    UserRoleConService userRoleConService;
    
    //
    
    @Autowired
    RoleRequestService roleRequestService;

    //
    
    @Autowired
    QueueRequestService queueRequestService;
    
    @Autowired
    UserQueueConService userQueueConService;
    

    @Autowired
    UserRoleConRequestService userRoleConRequestService;

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public String createUserForm(ModelMap map) {
        map.addAttribute("roleList", roleService.getRoleList());
        map.addAttribute("userRequest", new UserRequest());
        map.addAttribute("page", "main/create/createuser.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/create/", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request,
            @ModelAttribute("userRequest") UserRequest userForCreation,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Check if user has correct role to do this
        User user = (User) request.getSession().getAttribute("user");

        if (!userRoleConService.doesConExist(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_USER_CREATION").getRoleId())) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "You do not have the correct role to complete this operation");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        // check if username is taken
        User verifyUser = userService.getUserByUsername(userForCreation.getUser().getUsername(), false);
        if (verifyUser.getUserId() != 0) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "A user already exists with that username");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        // If roles have been selected, checks if they are valid
        for (int i = 0, length = userForCreation.getUserRoleConRequestList().size(); i < length; i++) {
            UserRoleConRequest userRoleConRequest = userForCreation.getUserRoleConRequestList().get(i);

            if (!roleService.doesRoleExist(userRoleConRequest.getRole().getRoleId())) {
                userForCreation.getUserRoleConRequestList().remove(userRoleConRequest);
                i--;
                length--;
            }
        }

        // Inserts user and gets temp ID to store roles against
        int tempUserId = userRequestService.insertUserRequest(new UserRequest(userForCreation.getUser(), userForCreation.getTicket(), user));

        for (UserRoleConRequest userRoleConRequest : userForCreation.getUserRoleConRequestList()) {
            userRoleConRequest.getUser().setUserId(tempUserId);
            userRoleConRequestService.insertUserRoleConRequest(
                    userRoleConRequest.getRole().getRoleId(),
                    userRoleConRequest.getUser().getUserId(),
                    userRoleConRequest.getValidFrom(),
                    userRoleConRequest.getValidTo(),
                    userRoleConRequest.getTicket().getTicketId(),
                    userRoleConRequest.getRequestor().getUserId());
        }

        userRequestService.setReadyToProcess(tempUserId);

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

        if (!userRoleConService.doesConExist(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_ROLE_CREATION").getRoleId())) {
            map.addAttribute("role", roleForCreation);
            map.addAttribute("alert", "You do not have the correct role to complete this operation");
            map.addAttribute("page", "main/create/createrole.jsp");
            return "mainview";
        }

        if (roleService.doesRoleExist(roleForCreation.getRoleName())) {
            map.addAttribute("role", roleForCreation);
            map.addAttribute("alert", "A role with this name already exists");
            map.addAttribute("page", "main/create/createrole.jsp");
            return "mainview";
        }

        roleRequestService.insertRoleRequest(new RoleRequest(roleForCreation, user, new Ticket(Integer.valueOf(ticketId))));

        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

    @RequestMapping(value = "/queue/", method = RequestMethod.GET)
    public String createQueueForm(ModelMap map) {
        map.addAttribute("userList", userService.getUserList(false));
        map.addAttribute("queueRequest", new QueueRequest());
        map.addAttribute("page", "main/create/createqueue.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/queue/create/", method = RequestMethod.POST)
    public String createQueue(HttpServletRequest request,
            @ModelAttribute("queueRequest") QueueRequest queueRequest,
            ModelMap map) {

        // Check if user has correct role to do this
        User user = (User) request.getSession().getAttribute("user");

        if (!userRoleConService.doesConExist(user.getUserId(),
                roleService.getRoleByRoleName("MAINT_QUEUE_CREATION").getRoleId())) {
            map.addAttribute("queueRequest", queueRequest);
            map.addAttribute("alert", "You do not have the correct role to complete this operation");
            map.addAttribute("page", "main/create/createqueue.jsp");
            return "mainview";
        }
        
        // If users have been selected, checks if they are valid
        for (int i = 0, length = queueRequest.getUserQueueConList().size(); i < length; i++) {
            UserQueueCon userQueueCon = queueRequest.getUserQueueConList().get(i);

            if (!userService.doesUserExist(userQueueCon.getUser().getUserId())) {
                queueRequest.getUserQueueConList().remove(userQueueCon);
                i--;
                length--;
            }
        }
        
        // Inserts queue into queue request
        int queueRequestId = queueRequestService.insertQueueRequest(queueRequest);
        
        // Inserts any user connection
        for (UserQueueCon userQueueCon : queueRequest.getUserQueueConList()) {
            userQueueCon.getQueue().setQueueId(queueRequestId);
            userQueueConService.insertUserQueueCon(userQueueCon);
        }
        
        // Success
        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview"; 
    }

}
