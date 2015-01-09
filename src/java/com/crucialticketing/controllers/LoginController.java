/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Login;
import com.crucialticketing.entities.User;
import com.crucialticketing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Daniel Foley
 */
@RequestMapping(value = "/login/")
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login/", method = RequestMethod.GET)
    public String defaultLoginVisit(ModelMap map) {
        map.addAttribute("login", new Login());
        return "login/login";
    }

    @RequestMapping(value = "/attemptlogin/",
            method = RequestMethod.POST)
    public String attemptLogin(@ModelAttribute("login") Login login, ModelMap map) {

        User user = userService.getUserByUsername(login.getUsername());

        if (user.getLogin().getPassword().equals(login.getPassword())) {
            map.addAttribute("page", "menu/main.jsp");
            return "mainview";
        }

        map.addAttribute("alert", "Invalid username or password");
        return "/login/login";
    }

    @RequestMapping(value = "/logout/",
            method = RequestMethod.GET)
    public String logout(ModelMap map) {
        map.addAttribute("login", new Login());
        return "/login/login";
    }

}
