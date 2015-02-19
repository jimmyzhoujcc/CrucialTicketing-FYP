/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.schedule;

import com.crucialticketing.daos.services.RoleRequestService;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.entities.UserRequest;
import com.crucialticketing.daos.services.UserAlertService;
import com.crucialticketing.daos.services.UserRequestService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.entities.RoleRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author DanFoley
 */
@Component
public class CreationJob {

    @Autowired
    UserRequestService userRequestService;
    
    @Autowired
    RoleRequestService roleRequestService;

    @Autowired
    UserService userService;
    
    @Autowired
    RoleService roleService;

    @Autowired
    UserAlertService userAlertService;

    public void executeUserCreationJob() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found = false;

        List<UserRequest> userRequestList = userRequestService.getUserRequestList();

        for (UserRequest userRequest : userRequestList) {
            for (int i = 0; i < processedList.size(); i++) {
                if (userRequest.getUser().getUsername().equals(processedList.get(i))) {
                    userRequestService.removeRequest(userRequest.getUserRequestId());
                    i = processedList.size();
                    found = true;
                }
            }

            if (!found) {

                if (userService.getUserByUsername(userRequest.getUser().getUsername(), false).getUserId() == 0) {
                    String password = userService.insertUser(userRequest.getUser());
                    processedList.add(userRequest.getUser().getUsername());
                    userRequestService.removeRequest(userRequest.getUserRequestId());
                    userAlertService.insertUserAlert(userRequest.getRequestor().getUserId(),
                            "A user account has been setup with the following information: Username ("
                            + userRequest.getUser().getUsername() + ") Password (" + password + ")");
                } else {
                    processedList.add(userRequest.getUser().getUsername());
                    userRequestService.removeRequest(userRequest.getUserRequestId());
                    userAlertService.insertUserAlert(userRequest.getRequestor().getUserId(),
                            "A user account was setup with the same username before your request could be completed");
                }
            }
        }

    }
    
    public void executeRoleCreationJob() {
         ArrayList<String> processedList = new ArrayList<>();
        boolean found = false;

        List<RoleRequest> roleRequestList = roleRequestService.getRoleRequestList();

        for (RoleRequest roleRequest : roleRequestList) {
            for (int i = 0; i < processedList.size(); i++) {
                if (roleRequest.getRole().getRoleName().equals(processedList.get(i))) {
                    roleRequestService.removeRequest(roleRequest.getRoleRequestId());
                    i = processedList.size();
                    found = true;
                }
            }

            if (!found) {

                if (roleService.getRoleByRoleName(roleRequest.getRole().getRoleName()).getRoleId() == 0) {
                    roleService.insertRole(roleRequest.getRole());
                    processedList.add(roleRequest.getRole().getRoleName());
                    roleRequestService.removeRequest(roleRequest.getRoleRequestId());
                    userAlertService.insertUserAlert(roleRequest.getRequestor().getUserId(),
                            "A role has been setup with the following information: role Name ("
                            + roleRequest.getRole().getRoleName()+ ") Role Description (" + roleRequest.getRole().getRoleDescription() + ")");
                } else {
                    processedList.add(roleRequest.getRole().getRoleName());
                    userRequestService.removeRequest(roleRequest.getRoleRequestId());
                    userAlertService.insertUserAlert(roleRequest.getRequestor().getUserId(),
                            "A role was setup with the same role name before your request could be completed");
                }
            }
        }
    }

}
