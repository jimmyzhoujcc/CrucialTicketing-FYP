/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daos.services.QueueChangeLogService;
import com.crucialticketing.daos.services.QueueService;
import com.crucialticketing.daos.services.RoleChangeLogService;
import com.crucialticketing.entities.User;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.daos.services.TicketService;
import com.crucialticketing.daos.services.UserChangeLogService;
import com.crucialticketing.daos.services.UserQueueConService;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.QueueChangeLog;
import com.crucialticketing.entities.QueueRequest;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleChangeLog;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.entities.UserChangeLog;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.UserRoleCon;
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
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    UserChangeLogService userChangeLogService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleChangeLogService roleChangeLogService;

    @Autowired
    UserRoleConService userRoleConService;

    //
    @Autowired
    QueueService queueService;

    @Autowired
    UserQueueConService userQueueConService;

    @Autowired
    QueueChangeLogService queueChangeLogService;

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public String createUserForm(ModelMap map) {
        map.addAttribute("roleList", roleService.getOnlineRoleList());
        map.addAttribute("user", new User());
        map.addAttribute("page", "main/create/createuser.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/user/create/", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request,
            @ModelAttribute("user") User userForCreation,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Gets user off of the session
        User user = (User) request.getSession().getAttribute("user");

        // Checks the role needed for this exists and is not deactivated
        if (!roleService.doesRoleExistInOnline("MAINT_USER_CREATION")) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "Unable to complete your service request");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        // Checks if the user has the correct role and it is active
        if (!userRoleConService.doesUserRoleConExistInOnline(user,
                roleService.getRoleByRoleName("MAINT_USER_CREATION"))) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "You do not have the required role privledges to complete this operation");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        // Checks if ticket is valid
        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "This ticket does not exist");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        // check if username is taken
        if (userService.doesUserExist(userForCreation.getUsername())) {
            map.addAttribute("user", userForCreation);
            map.addAttribute("alert", "A user already exists with that username");
            map.addAttribute("page", "main/create/createuser.jsp");
            return "mainview";
        }

        // If roles have been selected, checks if they are valid
        for (int i = 0, length = userForCreation.getUserRoleConList().size(); i < length; i++) {
            UserRoleCon userRoleCon = userForCreation.getUserRoleConList().get(i);

            if (!roleService.doesRoleExistInOnline(userRoleCon.getRole().getRoleId())) {
                userForCreation.getUserRoleConList().remove(userRoleCon);
                i--;
                length--;
            }
        }

        // Inserts user and gets ID to store roles against
        int userId = userService.insertUser(userForCreation);
        userForCreation.setUserId(userId);

        for (UserRoleCon userRoleCon : userForCreation.getUserRoleConList()) {
            userRoleCon.getUser().setUserId(userId);
            int userRoleConId = userRoleConService.insertUserRoleCon(userRoleCon, true);
            userRoleCon.setUserRoleConId(userRoleConId);
            userRoleConService.updateToUnprocessed(userRoleCon);
        }

        userChangeLogService.insertQueueChangeLog(
                new UserChangeLog(
                        userForCreation,
                        user.getSecure().getHash(),
                        user.getEmailAddress(),
                        user.getContact(),
                        new Ticket(Integer.valueOf(ticketId)),
                        ActiveFlag.UNPROCESSED.getActiveFlag(),
                        user,
                        getTimestamp()
                )
        );

        userService.updateToUnprocessed(userForCreation);

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

        // Checks if role is active
        if (!roleService.doesRoleExistInOnline("MAINT_ROLE_CREATION")) {
            map.addAttribute("role", roleForCreation);
            map.addAttribute("alert", "Unable to complete your service request");
            map.addAttribute("page", "main/create/createrole.jsp");
            return "mainview";
        }

        // Checks if the user has this role
        if (!userRoleConService.doesUserRoleConExistInOnline(user,
                roleService.getRoleByRoleName("MAINT_ROLE_CREATION"))) {
            map.addAttribute("role", roleForCreation);
            map.addAttribute("alert", "You do not have the correct role to complete this operation");
            map.addAttribute("page", "main/create/createrole.jsp");
            return "mainview";
        }

        // Checks if ticket is valid
        if (!ticketService.doesTicketExist(Integer.valueOf(ticketId))) {
            map.addAttribute("role", roleForCreation);
            map.addAttribute("alert", "This ticket does not exist");
            map.addAttribute("page", "main/create/createrole.jsp");
            return "mainview";
        }

        // Checks if role exists already
        if (roleService.doesRoleExistInOnlineOrOffline(roleForCreation.getRoleName())) {
            map.addAttribute("role", roleForCreation);
            map.addAttribute("alert", "A role with this name already exists");
            map.addAttribute("page", "main/create/createrole.jsp");
            return "mainview";
        }

        int roleId = roleService.insertRole(roleForCreation);
        roleForCreation.setRoleId(roleId);

        roleChangeLogService.insertRoleChange(
                new RoleChangeLog(
                        roleForCreation,
                        ActiveFlag.UNPROCESSED.getActiveFlag(),
                        new Ticket(Integer.valueOf(ticketId)),
                        user,
                        getTimestamp()
                )
        );

        roleService.updateToUnprocessed(roleForCreation);

        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

    @RequestMapping(value = "/queue/", method = RequestMethod.GET)
    public String createQueueForm(ModelMap map) {
        map.addAttribute("userList", userService.getOnlineUserList(false));
        map.addAttribute("queue", new Queue());
        map.addAttribute("page", "main/create/createqueue.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/queue/create/", method = RequestMethod.POST)
    public String createQueue(HttpServletRequest request,
            @ModelAttribute("queue") Queue queue,
            @RequestParam(value = "ticketId", required = false) String ticketId,
            ModelMap map) {

        // Check if user has correct role to do this
        User user = (User) request.getSession().getAttribute("user");

        if (!userRoleConService.doesUserRoleConExistInOnline(user,
                roleService.getRoleByRoleName("MAINT_QUEUE_CREATION"))) {
            map.addAttribute("queueRequest", queue);
            map.addAttribute("alert", "You do not have the correct role to complete this operation");
            map.addAttribute("page", "main/create/createqueue.jsp");
            return "mainview";
        }

        // Check if queue name already exists
        if (queueService.doesQueueExist(queue.getQueueName())) {
            map.addAttribute("queueRequest", queue);
            map.addAttribute("alert", "There is an entry already matching this queue name");
            map.addAttribute("page", "main/create/createqueue.jsp");
            return "mainview";
        }

        // If users have been selected, checks if they are valid
        for (int i = 0, length = queue.getUserList().size(); i < length; i++) {
            UserQueueCon userQueueCon = queue.getUserList().get(i);

            if (!userService.doesUserExistInOnline(userQueueCon.getUser())) {
                queue.getUserList().remove(userQueueCon);
                i--;
                length--;
            }
        }

        // Inserts queue into queue request
        int queueId = queueService.insertQueue(queue);
        queue.setQueueId(queueId);

        // Inserts any user connection
        for (UserQueueCon userQueueCon : queue.getUserList()) {
            userQueueCon.getQueue().setQueueId(queueId);
            int userQueueConId = userQueueConService.insertUserQueueCon(userQueueCon, true);
            userQueueCon.setUserQueueConId(userQueueConId);
            userQueueConService.updateToUnprocessed(userQueueCon);
        }

        queueChangeLogService.insertQueueChangeLog(new QueueChangeLog(queue,
                new Ticket(Integer.valueOf(ticketId)),
                user)
        );

        queueService.updateToUnprocessed(queue);

        // Success
        map.addAttribute("success", "Request submission received");
        map.addAttribute("page", "menu/create.jsp");

        return "mainview";
    }

}
