/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daoimpl.ApplicationControlLockRequestService;
import com.crucialticketing.daoimpl.ApplicationLockRequestService;
import com.crucialticketing.daoimpl.QueueLockRequestService;
import com.crucialticketing.daoimpl.RoleLockRequestService;
import com.crucialticketing.daoimpl.SeverityLockRequestService;
import com.crucialticketing.daoimpl.TicketLockRequestService;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserAlert;
import com.crucialticketing.entities.UserAlertLog;
import com.crucialticketing.daoimpl.UserAlertService;
import com.crucialticketing.daoimpl.UserLockRequestService;
import com.crucialticketing.daoimpl.WorkflowLockRequestService;
import com.crucialticketing.daoimpl.WorkflowStatusLockRequestService;
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

    @Autowired
    TicketLockRequestService ticketLockRequestService;

    @Autowired
    UserLockRequestService userLockRequestService;

    @Autowired
    RoleLockRequestService roleLockRequestService;

    @Autowired
    QueueLockRequestService queueLockRequestService;

    @Autowired
    SeverityLockRequestService severityLockRequestService;

    @Autowired
    ApplicationLockRequestService applicationLockRequestService;

    @Autowired
    ApplicationControlLockRequestService applicationControlLockRequestService;

    @Autowired
    WorkflowStatusLockRequestService workflowStatusLockRequestService;

    @Autowired
    WorkflowLockRequestService workflowLockRequestService;

    private UserAlertLog getAlertList(HttpServletRequest request) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession sessionInfo = httpRequest.getSession();

        User user = (User) sessionInfo.getAttribute("user");

        UserAlertLog userAlertLog = new UserAlertLog(
                userAlertService.getUserAlertListByUserId(user.getUserId()),
                (int) (System.currentTimeMillis() / 1000));
        return userAlertLog;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    String getNotificationListJson(HttpServletRequest request) {
        UserAlertLog userAlertLog = this.getAlertList(request);

        ObjectMapper mapper = new ObjectMapper();

        String jsonConversion = "";

        try {
            jsonConversion = mapper.writeValueAsString(userAlertLog);
        } catch (Exception e) {
        }

        return jsonConversion;
    }

    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    public String getNotificationList(HttpServletRequest request, ModelMap map) {
        UserAlertLog userAlertLog = this.getAlertList(request);

        map.addAttribute("userAlertLog", userAlertLog);
        map.addAttribute("page", "main/alert/alertall.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public String getSingleAlert(HttpServletRequest request,
            @RequestParam(value = "alert", required = true) String userAlertId,
            ModelMap map) {
        // Basic checks
        User user = (User) request.getSession().getAttribute("user");

        UserAlert userAlert = userAlertService.getUserAlertById(Integer.valueOf(userAlertId), user.getUserId());

        UserAlertLog userAlertLog = new UserAlertLog();
        userAlertLog.getUserAlertLog().add(userAlert);

        map.addAttribute("userAlertLog", userAlertLog);
        map.addAttribute("page", "main/alert/alertall.jsp");
        return "mainview";
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

    @RequestMapping(value = "/checkticket/", method = RequestMethod.POST)
    public @ResponseBody
    String checkTicketIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String ticketId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = ticketLockRequestService.checkIfOpen(Integer.valueOf(ticketId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }

    @RequestMapping(value = "/checkuser/", method = RequestMethod.POST)
    public @ResponseBody
    String checkUserIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String userId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = userLockRequestService.checkIfOpen(Integer.valueOf(userId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }

    @RequestMapping(value = "/checkrole/", method = RequestMethod.POST)
    public @ResponseBody
    String checkRoleIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String roleId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = roleLockRequestService.checkIfOpen(Integer.valueOf(roleId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }

    @RequestMapping(value = "/checkqueue/", method = RequestMethod.POST)
    public @ResponseBody
    String checkQueueIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String queueId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = queueLockRequestService.checkIfOpen(Integer.valueOf(queueId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }

    @RequestMapping(value = "/checkseverity/", method = RequestMethod.POST)
    public @ResponseBody
    String checkSeverityIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String severityId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = severityLockRequestService.checkIfOpen(Integer.valueOf(severityId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }

    @RequestMapping(value = "/checkconfiguration/", method = RequestMethod.POST)
    public @ResponseBody
    String checkApplicationControlIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String applicationControlId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = applicationControlLockRequestService.checkIfOpen(Integer.valueOf(applicationControlId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }

    @RequestMapping(value = "/checkapplication/", method = RequestMethod.POST)
    public @ResponseBody
    String checkApplicationIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String applicationId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = applicationLockRequestService.checkIfOpen(Integer.valueOf(applicationId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }

    @RequestMapping(value = "/checkworkflowstatus/", method = RequestMethod.POST)
    public @ResponseBody
    String checkWorkflowStatusIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String workflowStatusId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = workflowStatusLockRequestService.checkIfOpen(Integer.valueOf(workflowStatusId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }

    @RequestMapping(value = "/checkworkflow/", method = RequestMethod.POST)
    public @ResponseBody
    String checkWorkflowIsOpen(HttpServletRequest request, @RequestParam(value = "id", required = true) String workflowId) {
        User user = (User) request.getSession().getAttribute("user");
        boolean open = workflowLockRequestService.checkIfOpen(Integer.valueOf(workflowId), user.getUserId());
        return String.valueOf((open) ? 1 : 0);
    }
}
