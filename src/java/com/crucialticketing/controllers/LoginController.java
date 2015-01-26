/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Login;
import com.crucialticketing.entities.User;
import com.crucialticketing.services.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    DataSource dataSource;

    @RequestMapping(value = "/login/", method = RequestMethod.GET)
    public String defaultLoginVisit(ModelMap map) {
        map.addAttribute("login", new Login());
        return "login/login";
    }

    @RequestMapping(value = "/attemptlogin/",
            method = RequestMethod.POST)
    public String attemptLogin(HttpServletRequest request, @ModelAttribute("login") Login login, ModelMap map) {

        JdbcTemplate con = new JdbcTemplate(dataSource);
        UserService userService = new UserService();
        userService.setCon(con);

        User user = userService.getUserByUsername(login.getUsername(), true);

        if (user.getLogin().getPassword().equals(login.getPassword())) {
            request.getSession().setAttribute("user", user);
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
