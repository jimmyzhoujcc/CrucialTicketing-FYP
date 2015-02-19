/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserAlert;
import com.crucialticketing.entities.UserAlertLog;
import com.crucialticketing.daos.services.UserAlertService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author DanFoley
 */
@RequestMapping(value = "/alert/")
@Controller
public class AlertController {

    @Autowired
    UserAlertService userAlertService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    String getNotificationList(HttpServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession sessionInfo = httpRequest.getSession();

        User user = (User) sessionInfo.getAttribute("user");

        UserAlertLog userAlertLog = new UserAlertLog(
                userAlertService.getUserAlertListByUserId(user.getUserId()),
                (int) (System.currentTimeMillis() / 1000));

        ObjectMapper mapper = new ObjectMapper();

        String jsonConversion = "";

        try {
            jsonConversion = mapper.writeValueAsString(userAlertLog);
        } catch (Exception e) {
        }

        return jsonConversion;
    }

    @RequestMapping(value = "/clear/", method = RequestMethod.POST)
    public @ResponseBody
    String clearNotificationCount(HttpServletRequest request, @RequestParam(value = "marker", required = true) String marker) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession sessionInfo = httpRequest.getSession();

        User user = (User) sessionInfo.getAttribute("user");

        userAlertService.clearNotificationCount(user.getUserId(), Integer.valueOf(marker));

        return "complete";
    }

    @RequestMapping(value = "/singlealert/", method = RequestMethod.GET)
    public String getSingleAlert(@RequestParam(value = "useralertid", required = true) String userAlertId, ModelMap map) {
        UserAlert userAlert = userAlertService.getUserAlertById(Integer.valueOf(userAlertId));
        map.addAttribute("userAlert", userAlert);
        map.addAttribute("page", "/main/alert/alertsingle.jsp");
        return "mainview";
    }
}
