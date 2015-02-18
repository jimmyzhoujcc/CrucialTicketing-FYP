/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Secure;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRequest;
import com.crucialticketing.services.RoleService;
import com.crucialticketing.services.UserRequestService;
import com.crucialticketing.services.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    DataSource dataSource;

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public String createUserForm(ModelMap map) {
        JdbcTemplate con = new JdbcTemplate(dataSource);

        RoleService roleService = new RoleService();
        roleService.setCon(con);

        map.addAttribute("roleList", roleService.getRoleList());
        map.addAttribute("user", new User());
        map.addAttribute("page", "main/create/createuser.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/create/", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request,
            @RequestParam(value = "roleListSelected", required = true) String roleListSelected,
            @ModelAttribute("user") User userForCreation, ModelMap map) {

        // Check if user has correct role to do this
        User user = (User) request.getSession().getAttribute("user");

        if (!user.hasRole("MAINT_USER_CREATION")) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "You do not have the correct role to complete this operation");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        JdbcTemplate con = new JdbcTemplate(dataSource);

        // check if username is taken
        UserService userService = new UserService();
        userService.setCon(con);

        User verifyUser = userService.getUserByUsername(userForCreation.getUsername(), false);
        if (verifyUser.getUserId() != 0) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "A user already exists with that username");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        UserRequestService userRequestService = new UserRequestService();
        userRequestService.setCon(con);
        userRequestService.insertUserRequest(new UserRequest(userForCreation, user));
        
        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }
}
