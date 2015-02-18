/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Secure;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.UploadedFile;
import com.crucialticketing.entities.User;
import com.crucialticketing.services.UserRoleConnectionService;
import com.crucialticketing.services.UserService;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Daniel Foley
 */
@RequestMapping(value = "/login/")
@Controller
public class LoginController {

    @Autowired
    DataSource dataSource;

    @RequestMapping(value = "/login/", method = RequestMethod.GET)
    public String defaultLoginVisit(ModelMap map) {
        return "login/login";
    }

    @RequestMapping(value = "/attemptlogin/",
            method = RequestMethod.POST)
    public String attemptLogin(HttpServletRequest request,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password, ModelMap map) {

        try {
            request.getSession().removeAttribute("alert");

            JdbcTemplate con = new JdbcTemplate(dataSource);
            UserService userService = new UserService();
            userService.setCon(con);

            User user = userService.getUserByUsername(username, true);

            PasswordHash passwordHash = new PasswordHash();

            if (passwordHash.validatePassword(password, user.getSecure().getHash())) {
                UserRoleConnectionService userRoleConnectionService = new UserRoleConnectionService();
                userRoleConnectionService.setCon(con);

                user.setRoleList(userRoleConnectionService.getUserRoleConListByUserId(user.getUserId()));

                request.getSession().setAttribute("user", user);

                if (!user.hasRole("END_USER")) {
                    map.addAttribute("alert", "User does not have the correct privledges to login");
                    return "/login/login";
                }

                map.addAttribute("alert", "");
                map.addAttribute("page", "menu/main.jsp");
                return "mainview";
            }

            map.addAttribute("alert", "Invalid username or password");
            return "/login/login";
        } catch (Exception e) {}
        
        map.addAttribute("alert", "System is current experiencing technical difficulties");
        return "/login/login";
    }

    @RequestMapping(value = "/logout/",
            method = RequestMethod.GET)
    public String logout(HttpServletRequest request, ModelMap map) {
        request.getSession().removeAttribute("user");
        map.addAttribute("login", new Secure());
        return "/login/login";
    }

}
