/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daos.services.RoleRequestService;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRequest;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.daos.services.UserRequestService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleRequest;
import com.crucialticketing.entities.Ticket;
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
    RoleRequestService roleRequestService;

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public String createUserForm(ModelMap map) {
        map.addAttribute("roleList", roleService.getRoleList());
        map.addAttribute("user", new User());
        map.addAttribute("page", "main/create/createuser.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/create/", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request,
            @RequestParam(value = "roleListSelected", required = false) String roleListSelected,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            @ModelAttribute("user") User userForCreation, ModelMap map) {

        // Check if user has correct role to do this
        User user = (User) request.getSession().getAttribute("user");

        if (!user.hasRole("MAINT_USER_CREATION")) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "You do not have the correct role to complete this operation");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        // check if username is taken
        User verifyUser = userService.getUserByUsername(userForCreation.getUsername(), false);
        if (verifyUser.getUserId() != 0) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "A user already exists with that username");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        userRequestService.insertUserRequest(new UserRequest(userForCreation, user, new Ticket(Integer.valueOf(ticketId))));

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

        if (!user.hasRole("MAINT_ROLE_CREATION")) {
            map.addAttribute("role", roleForCreation);
            map.addAttribute("alert", "You do not have the correct role to complete this operation");
            map.addAttribute("page", "main/create/createrole.jsp");
            return "mainview";
        }

        roleRequestService.insertRoleRequest(new RoleRequest(roleForCreation, user, new Ticket(Integer.valueOf(ticketId))));

        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

}
