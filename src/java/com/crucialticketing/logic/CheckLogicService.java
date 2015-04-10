/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.logic;

import com.crucialticketing.daoimpl.RoleService;
import com.crucialticketing.daoimpl.TicketService;
import com.crucialticketing.daoimpl.UserRoleConService;
import com.crucialticketing.entities.User;
import com.crucialticketing.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

/**
 *
 * @author DanFoley
 */
public class CheckLogicService {
    @Autowired
    TicketService ticketService;
    
    @Autowired
    RoleService roleService;
    
    @Autowired
    UserRoleConService userRoleConService;
    
    
    // Sub-controller -  check role
    public boolean doesRoleCheckPass(String role, User user, ModelMap map) {
        // Checks if the role is active
        if (!roleService.doesRoleExistInOnline(role)) {
            Validation.databaseError(map);
            return false;
        }

        // Checks if the user has this role
        if (!userRoleConService.doesUserRoleConExistInOnline(user.getUserId(),
                roleService.getRoleByRoleName(role).getRoleId())) {
            Validation.userDoesntHaveRole(map);
            return false;
        }
        return true;
    }

    // Sub-controller - check ticket Id
    public boolean doesTicketCheckPass(String ticketId, ModelMap map) {
        // Checks if ticket is set
        if (ticketId == null) {
            Validation.inputNotProvided(map, "Ticket");
            return false;
        }

        // Checks if ticket is valid
        int convTicketId;
        try {
            convTicketId = Integer.valueOf(ticketId);

            if (!ticketService.doesTicketExist(convTicketId)) {
                Validation.fieldAlreadyExists(map, "Ticket");
                return false;
            }
        } catch (Exception e) {
            Validation.inputIsInvalid(map, "Ticket");
            return false;
        }
        return true;
    }
}
