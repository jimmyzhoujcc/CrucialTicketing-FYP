/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.entities.Secure;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.User;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.entities.Role;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Daniel Foley
 */
@RequestMapping(value = "/login/")
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleConService userRoleConService;

    @RequestMapping(value = "/login/", method = RequestMethod.GET)
    public String defaultLoginVisit(HttpServletRequest request, ModelMap map) {
        // Gets user off session
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            boolean active = (boolean) request.getSession().getAttribute("active");

            if (active) {
                map.addAttribute("page", "menu/main.jsp");
                return "mainview";
            }
        }
        return "login/login";
    }

    @RequestMapping(value = "/attemptlogin/",
            method = RequestMethod.POST)
    public String attemptLogin(HttpServletRequest request,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password, ModelMap map) {

        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            boolean active = (boolean) request.getSession().getAttribute("active");

            if (active) {
                map.addAttribute("page", "menu/main.jsp");
                return "mainview";
            }
        }

        try {
            request.getSession().removeAttribute("alert");

            if (!userService.doesUserExistInOnline(username)) {
                // Doesnt exist as a active user
                map.addAttribute("alert", "User is not an activated user or does not exist");
                return "/login/login";
            }

            user = userService.getUserByUsername(username, true);

            PasswordHash passwordHash = new PasswordHash();

            if (!passwordHash.validatePassword(password, user.getSecure().getHash())) {
                map.addAttribute("alert", "Invalid username or password");
                return "/login/login";
            }

            if (!roleService.doesRoleExistInOnline("END_USER")) {
                map.addAttribute("alert", "Unable to complete your login request");
                return "/login/login";
            }

            Role role = roleService.getRoleByRoleName("END_USER");

            if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(), role.getRoleId())) {
                map.addAttribute("alert", "User does not have the correct privledges to login");
                return "/login/login";
            }

            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("active", true);

            map.addAttribute("page", "menu/main.jsp");
            return "mainview";

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            map.addAttribute("alert", "Unable to complete your login request");
            return "/login/login";
        }
    }

    @RequestMapping(value = "/logout/",
            method = RequestMethod.GET)
    public String logout(HttpServletRequest request, ModelMap map) {
        request.getSession().removeAttribute("user");
        map.addAttribute("login", new Secure());
        return "/login/login";
    }
}
