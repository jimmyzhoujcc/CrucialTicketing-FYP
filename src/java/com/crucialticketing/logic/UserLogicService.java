/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.logic;

import com.crucialticketing.daoimpl.UserRoleConService;
import com.crucialticketing.daoimpl.UserService;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserRoleCon;
import com.crucialticketing.util.ActiveFlag;
import com.crucialticketing.util.Timestamp;
import static com.crucialticketing.util.Timestamp.convTimestamp;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.util.Validation;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

/**
 *
 * @author DanFoley
 */
public class UserLogicService {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleConService userRoleConService;

    public boolean updateUser(User newVersion, String ticketId, User requestor, ModelMap map) {

        PasswordHash passwordHash = new PasswordHash();

        try {
            User oldVersion = userService.getUserById(newVersion.getUserId(), true);

            // Initial check
            if (oldVersion.getUserId() == 0) {
                Validation.inputIsInvalid(map, "User");
                return false;
            }

            oldVersion.setUserRoleConList(userRoleConService.getRoleListByUserId(oldVersion.getUserId()));

            // Updates sections of the user record if a change has occured
            if (Validation.isStringSet(newVersion.getSecure().getTextPassword(), 25)) {
                String hash = passwordHash.createHash(newVersion.getSecure().getTextPassword());

                if (!oldVersion.getSecure().getHash().equals(hash)) {
                    userService.updateHash(oldVersion.getUserId(), hash);
                }
            }

            if (Validation.isStringSet(newVersion.getFirstName(), 25)) {
                if (!oldVersion.getFirstName().equals(newVersion.getFirstName())) {
                    userService.updateFirstName(oldVersion.getUserId(), newVersion.getFirstName());
                }
            }

            if (Validation.isStringSet(newVersion.getLastName(), 25)) {
                if (!oldVersion.getLastName().equals(newVersion.getLastName())) {
                    userService.updateLastName(oldVersion.getUserId(), newVersion.getLastName());
                }
            }

            if (Validation.isStringSet(newVersion.getEmailAddress(), 50)) {
                if (!oldVersion.getEmailAddress().equals(newVersion.getEmailAddress())) {
                    userService.updateEmail(oldVersion.getUserId(), newVersion.getEmailAddress());
                }
            }

            if (Validation.isStringSet(newVersion.getContact(), 15)) {
                if (!oldVersion.getContact().equals(newVersion.getContact())) {
                    userService.updateContact(oldVersion.getUserId(), newVersion.getContact());
                }
            }

            boolean userDeactivated = false;

            // Updates status flag of the user record
            if (oldVersion.getActiveFlag().getActiveFlag() != newVersion.getActiveFlag().getActiveFlag()) {
                if (newVersion.getActiveFlag().getActiveFlag() == ActiveFlag.ONLINE.getActiveFlag()) {
                    userService.updateToOnline(oldVersion.getUserId(), new Ticket(Integer.valueOf(ticketId)), requestor);
                } else if (newVersion.getActiveFlag().getActiveFlag() == ActiveFlag.OFFLINE.getActiveFlag()) {
                    userService.updateToOffline(oldVersion.getUserId(), new Ticket(Integer.valueOf(ticketId)), requestor);
                    userDeactivated = true;
                }
            }

            // Updates any role connections
            for (UserRoleCon userRoleCon : newVersion.getUserRoleConList()) {
                int userRoleConId = 0;

                for (int i = 0; i < oldVersion.getUserRoleConList().size(); i++) {

                    if (oldVersion.getUserRoleConList().get(i).getRole().getRoleId() == userRoleCon.getRole().getRoleId()) {

                        userRoleConId = oldVersion.getUserRoleConList().get(i).getUserRoleConId();

                        // User deactivated
                        if (userDeactivated) {
                            userRoleConService.updateValidity(userRoleConId, oldVersion.getUserRoleConList().get(i).getValidFrom(), Timestamp.getTimestamp());
                            userRoleConService.updateToOffline(userRoleConId, new Ticket(Integer.valueOf(ticketId)), requestor);
                        } else {
                            if (userRoleCon.getActiveFlag().getActiveFlag()
                                    != oldVersion.getUserRoleConList().get(i).getActiveFlag().getActiveFlag()) {
                                if (userRoleCon.getActiveFlag().getActiveFlag() == ActiveFlag.ONLINE.getActiveFlag()) {
                                    userRoleConService.updateToOnline(userRoleConId, new Ticket(Integer.valueOf(ticketId)), requestor);
                                    userRoleCon.setValidTo(Timestamp.convTimestamp(userRoleCon.getValidToStr()));
                                } else {
                                    userRoleConService.updateToOffline(userRoleConId, new Ticket(Integer.valueOf(ticketId)), requestor);
                                    userRoleCon.setValidTo(Timestamp.getTimestamp());
                                }
                            } else {
                                userRoleCon.setValidTo(Timestamp.convTimestamp(userRoleCon.getValidToStr()));
                            }

                            if (oldVersion.getUserRoleConList().get(i).getValidTo() != userRoleCon.getValidTo()) {
                                userRoleConService.updateValidity(userRoleConId, oldVersion.getUserRoleConList().get(i).getValidFrom(), userRoleCon.getValidTo());
                            }
                        }
                    }
                }
            }

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NumberFormatException e) {
            Validation.pageError(map);
            return false;
        }

        return true;
    }
}
