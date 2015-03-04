/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.schedule;

import com.crucialticketing.daos.services.QueueChangeLogService;
import com.crucialticketing.daos.services.QueueService;
import com.crucialticketing.daos.services.RoleChangeLogService;
import com.crucialticketing.daos.services.RoleService;
import com.crucialticketing.daos.services.UserAlertService;
import com.crucialticketing.daos.services.UserChangeLogService;
import com.crucialticketing.daos.services.UserQueueConService;
import com.crucialticketing.daos.services.UserRoleConService;
import com.crucialticketing.daos.services.UserService;
import com.crucialticketing.entities.ActiveFlag;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.QueueChangeLog;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleChangeLog;
import com.crucialticketing.entities.Ticket;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserChangeLog;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.UserRoleCon;
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
    UserService userService;

    @Autowired
    UserChangeLogService userChangeLogService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleChangeLogService roleChangeLogService;

    @Autowired
    QueueService queueService;

    @Autowired
    UserRoleConService userRoleConService;

    @Autowired
    UserAlertService userAlertService;

    @Autowired
    QueueChangeLogService queueChangeLogService;

    @Autowired
    UserQueueConService userQueueConService;

    public void executeUserCreationJob() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found = false;

        List<User> newUserList = userService.getUnprocessedUserList();

        for (User user : newUserList) {
            for (int i = 0; i < processedList.size(); i++) {
                if (user.getUsername().equals(processedList.get(i))) {
                    userService.removeUserEntry(user);
                    // Change log entry
                    i = processedList.size();
                    found = true;
                }
            }

            // If the user is not found in the already processed list
            if (!found) {

                // Gets change log entry 
                List<UserChangeLog> userChangeLogList = userChangeLogService.getUserChangeLogList(user);

                // Checks if the username exists in the db
                if (!userService.doesUserExistInOnlineOrOffline(user.getUsername())) {

                    // Generates password for new user
                    PasswordHash passwordHash = new PasswordHash();
                    String generatedPassword = passwordHash.generatePassword(8);

                    try {
                        // Generates new hash for user
                        String hash = passwordHash.createHash(generatedPassword);

                        // Adds hash to user
                        userService.updateHash(user, hash);
                        user.getSecure().setHash(hash);

                        // Gets the roles requested with this new user
                        List<UserRoleCon> newUserRoleConList = userRoleConService.getUnprocessedUserRoleConListByUserId(user, true);

                        // Loops each role and checks if the same entry exists already
                        for (UserRoleCon userRoleCon : newUserRoleConList) {
                            if (userRoleConService.doesUserRoleConExistInOnline(user, userRoleCon.getRole())) {
                                userRoleConService.removeUserRoleConEntry(userRoleCon);
                            } else {
                                userRoleConService.updateToOnline(userRoleCon);
                            }
                        }

                        // Adds user to processed list to skip any further requests for this username
                        processedList.add(user.getUsername());

                        // Updates user activation flag
                        userService.updateToOnline(user);

                        userChangeLogService.insertQueueChangeLog(
                                new UserChangeLog(
                                        user,
                                        user.getSecure().getHash(),
                                        user.getEmailAddress(),
                                        user.getContact(),
                                        userChangeLogList.get(0).getTicket(),
                                        ActiveFlag.ONLINE.getActiveFlag(),
                                        userChangeLogList.get(0).getRequestor(),
                                        getTimestamp()
                                ));

                        // Adds alert to requestors notification list
                        userAlertService.insertUserAlert(userChangeLogList.get(0).getRequestor().getUserId(),
                                "A user account has been setup with the following information: Username ("
                                + user.getUsername() + ") Password (" + generatedPassword + ")");

                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                        processedList.add(user.getUsername());

                        userService.removeUserEntry(user);
                        userAlertService.insertUserAlert(userChangeLogList.get(0).getRequestor().getUserId(),
                                "This request could not be completed at this time. To avoid any "
                                + "further issues the request has been deleted");
                    }
                } else {
                    processedList.add(user.getUsername());
                    userService.removeUserEntry(user);
                    userAlertService.insertUserAlert(userChangeLogList.get(0).getRequestor().getUserId(),
                            "A user account was setup with the same username before your request could be completed");
                }
            }
        }

    }

    public void executeRoleCreationJob() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found = false;

        List<Role> newRoleList = roleService.getUnprocessedRoleList();

        for (Role newRole : newRoleList) {
            for (int i = 0; i < processedList.size(); i++) {
                if (newRole.getRoleName().equals(processedList.get(i))) {
                    roleService.removeRoleEntry(newRole);
                    i = processedList.size();
                    found = true;
                }
            }

            if (!found) {
                List<RoleChangeLog> roleChangeLogList = roleChangeLogService.getChangeLogByRoleId(newRole);

                if (!roleService.doesRoleExistInOnlineOrOffline(newRole.getRoleName())) {
                    roleService.updateToOnline(newRole);
                    processedList.add(newRole.getRoleName());

                    userAlertService.insertUserAlert(roleChangeLogList.get(0).getRequestor().getUserId(),
                            "A role has been setup with the following information: role Name ("
                            + newRole.getRoleName() + ") Role Description (" + newRole.getRoleDescription() + ")");

                    roleChangeLogService.insertRoleChange(
                            new RoleChangeLog(
                                    newRole,
                                    ActiveFlag.ONLINE.getActiveFlag(),
                                    roleChangeLogList.get(0).getTicket(),
                                    roleChangeLogList.get(0).getRequestor(),
                                    getTimestamp()
                            )
                    );
                } else {
                    processedList.add(newRole.getRoleName());
                    roleService.removeRoleEntry(newRole);
                    userAlertService.insertUserAlert(roleChangeLogList.get(0).getRequestor().getUserId(),
                            "A role was setup with the same role name before your request could be completed");
                }
            }
        }
    }

    public void executeQueueCreationJob() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found = false;

        List<Queue> queueList = queueService.getUnprocessedQueueList();

        for (Queue queue : queueList) {
            for (int i = 0; i < processedList.size(); i++) {
                if (queue.getQueueName().equals(processedList.get(i))) {
                    queueService.removeQueueEntry(queue);
                    i = processedList.size();
                    found = true;
                }
            }

            if (!found) {
                List<QueueChangeLog> queueChangeLog = queueChangeLogService.getQueueChangeLogList(queue);

                if (!queueService.doesQueueExistPostUnprocessed(queue.getQueueName())) {
                    updateUserQueueConToOnline(queue);
                    queueService.updateToOnline(queue);
                    userAlertService.insertUserAlert(queueChangeLog.get(0).getRequestor().getUserId(),
                            "A queue has been setup with the following information: Queue Name ("
                            + queue.getQueueName() + ")");
                } else {
                    processedList.add(queue.getQueueName());
                    queueService.removeQueueEntry(queue);
                    userAlertService.insertUserAlert(queueChangeLog.get(0).getRequestor().getUserId(),
                            "A role was setup with the same role name before your request could be completed");
                }
            }
        }
    }

    private void updateUserQueueConToOnline(Queue queue) {
        List<UserQueueCon> userQueueConList = userQueueConService.getUnprocessedUserQueueConListByQueueId(queue, true);

        for (UserQueueCon userQueueCon : userQueueConList) {
            if (!userQueueConService.doesUserQueueConExistPostUnprocessed(userQueueCon.getUser().getUserId(), queue.getQueueId())) {
                userQueueConService.updateToOnline(userQueueCon);
                // Change log entry
            } else {
                userQueueConService.removeQueueEntry(userQueueCon);
                // Change log entry
            }
        }

    }

}
