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
                boolean found = false;
                boolean statusChange = false;
                boolean timeChange = false;
                int userRoleConId = 0;

                for (int i = 0; (i < oldVersion.getUserRoleConList().size()) && (!found); i++) {

                    if (oldVersion.getUserRoleConList().get(i).getRole().getRoleId() == userRoleCon.getRole().getRoleId()) {
                        found = true;
                        userRoleConId = oldVersion.getUserRoleConList().get(i).getUserRoleConId();

                        if (oldVersion.getUserRoleConList().get(i).getActiveFlag().getActiveFlag()
                                != userRoleCon.getActiveFlag().getActiveFlag()) {

                            if (!((userDeactivated) && (userRoleCon.getActiveFlag().getActiveFlag() == ActiveFlag.OFFLINE.getActiveFlag()))) {
                                statusChange = true;
                            }
                        } else if (!convTimestamp(oldVersion.getUserRoleConList().get(i).getValidTo()).equals(userRoleCon.getValidToStr())) {
                            timeChange = true;
                        }
                    }
                }

                if (((statusChange) || (timeChange) || (userDeactivated)) && (found)) {
                    int timestamp = getTimestamp();

                    String validToStr = userRoleCon.getValidToStr();

                    int dateFrom = timestamp;
                    int dateTo = convTimestamp(validToStr);

                    // If user is being deactivated, the user role con status flag is overriden
                    if (userDeactivated) {
                        if (userRoleCon.getActiveFlag().getActiveFlag() != ActiveFlag.OFFLINE.getActiveFlag()) {
                            userRoleCon.setActiveFlag(ActiveFlag.OFFLINE);
                            statusChange = true;
                        }
                    }

                    // Overrides requested To date if active flag is set to offline for role or user
                    if (userRoleCon.getActiveFlag().getActiveFlag() == ActiveFlag.OFFLINE.getActiveFlag()) {
                        dateTo = timestamp;
                        timeChange = true;
                    }

                    if (timeChange) {
                        userRoleConService.updateValidity(userRoleConId, dateFrom, dateTo);
                    }

                    if (statusChange) {
                        if (userRoleCon.getActiveFlag().getActiveFlag() == ActiveFlag.ONLINE.getActiveFlag()) {
                            userRoleConService.updateToOnline(userRoleConId,
                                    new Ticket(Integer.valueOf(ticketId)), requestor);
                        } else if (userRoleCon.getActiveFlag().getActiveFlag() == ActiveFlag.OFFLINE.getActiveFlag()) {
                            userRoleConService.updateToOffline(userRoleConId,
                                    new Ticket(Integer.valueOf(ticketId)), requestor);
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
