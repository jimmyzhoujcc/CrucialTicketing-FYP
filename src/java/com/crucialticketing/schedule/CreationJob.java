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
import com.crucialticketing.daos.services.UserRoleConRequestService;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.RoleRequest;
import com.crucialticketing.entities.UserRoleConRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    UserRoleConRequestService userRoleConRequestService;
    
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleConService userRoleConService;
    
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

            // If the user is not found in the already processed list
            if (!found) {

                // Checks if the username exists in the db
                if (userService.getUserByUsername(userRequest.getUser().getUsername(), false).getUserId() == 0) {
                    // Generates password for new user
                    PasswordHash passwordHash = new PasswordHash();
                    String generatedPassword = passwordHash.generatePassword(8);

                    try {
                        // Generates new hash for user
                        String hash = passwordHash.createHash(generatedPassword);
                        userRequest.getUser().getSecure().setHash(hash);

                        // Inserts user and gets the new unique user ID for the user
                        int userId = userService.insertUser(userRequest.getUser());
                        
                        // Gets the roles requested by using the unique temp user ID from user request
                        List<UserRoleConRequest> userRoleConRequestList = userRoleConRequestService
                                .getUserRoleConnectionRequestList(userRequest.getUser().getUserId());
                        
                        // Loops each roles and inserts into live role list while deleting request record
                        for (UserRoleConRequest userRoleConRequest : userRoleConRequestList) {
                            userRoleConService.insertUserRoleCon(
                                    userId, 
                                    userRoleConRequest.getRole().getRoleId(), 
                                    userRoleConRequest.getValidFrom(), 
                                    userRoleConRequest.getValidTo());
                            userRoleConRequestService.removeUserRoleConRequest(userRoleConRequest.getUserRoleConRequestId());
                        }
                        
                        // Adds user to processed list to skip any further requests for this username
                        processedList.add(userRequest.getUser().getUsername());
                        
                        // Removes user request
                        userRequestService.removeRequest(userRequest.getUserRequestId());
                        
                        // Adds alert to requestors notification list
                        userAlertService.insertUserAlert(userRequest.getRequestor().getUserId(),
                                "A user account has been setup with the following information: Username ("
                                + userRequest.getUser().getUsername() + ") Password (" + generatedPassword + ")");

                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                        processedList.add(userRequest.getUser().getUsername());
                        userRequestService.removeRequest(userRequest.getUserRequestId());
                        userAlertService.insertUserAlert(userRequest.getRequestor().getUserId(),
                                "This request could not be completed at this time. To avoid any "
                                + "further issues the request has been deleted");
                    }
                } else {
                    processedList.add(userRequest.getUser().getUsername());
                    //userRequestService.removeRequest(userRequest.getUserRequestId());
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

                if (!roleService.doesRoleExist(roleRequest.getRole().getRoleName())) {
                    roleService.insertRole(roleRequest.getRole());
                    processedList.add(roleRequest.getRole().getRoleName());
                    roleRequestService.removeRequest(roleRequest.getRoleRequestId());
                    userAlertService.insertUserAlert(roleRequest.getRequestor().getUserId(),
                            "A role has been setup with the following information: role Name ("
                            + roleRequest.getRole().getRoleName() + ") Role Description (" + roleRequest.getRole().getRoleDescription() + ")");
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
