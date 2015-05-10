/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.schedule;

import com.crucialticketing.daoimpl.ApplicationChangeLogService;
import com.crucialticketing.daoimpl.ApplicationControlChangeLogService;
import com.crucialticketing.daoimpl.ApplicationControlService;
import com.crucialticketing.daoimpl.ApplicationService;
import com.crucialticketing.daoimpl.QueueChangeLogService;
import com.crucialticketing.daoimpl.QueueService;
import com.crucialticketing.daoimpl.RoleChangeLogService;
import com.crucialticketing.daoimpl.RoleService;
import com.crucialticketing.daoimpl.SeverityChangeLogService;
import com.crucialticketing.daoimpl.SeverityService;
import com.crucialticketing.daoimpl.UserAlertService;
import com.crucialticketing.daoimpl.UserChangeLogService;
import com.crucialticketing.daoimpl.UserQueueConChangeLogService;
import com.crucialticketing.daoimpl.UserQueueConService;
import com.crucialticketing.daoimpl.UserRoleConChangeLogService;
import com.crucialticketing.daoimpl.UserRoleConService;
import com.crucialticketing.daoimpl.UserService;
import com.crucialticketing.daoimpl.WorkflowChangeLogService;
import com.crucialticketing.daoimpl.WorkflowService;
import com.crucialticketing.daoimpl.WorkflowStatusChangeLogService;
import com.crucialticketing.daoimpl.WorkflowStatusService;
import com.crucialticketing.entities.Application;
import com.crucialticketing.entities.ApplicationChangeLog;
import com.crucialticketing.entities.ApplicationControl;
import com.crucialticketing.entities.ApplicationControlChangeLog;
import com.crucialticketing.entities.PasswordHash;
import com.crucialticketing.entities.Queue;
import com.crucialticketing.entities.QueueChangeLog;
import com.crucialticketing.entities.Role;
import com.crucialticketing.entities.RoleChangeLog;
import com.crucialticketing.entities.Severity;
import com.crucialticketing.entities.SeverityChangeLog;
import com.crucialticketing.entities.User;
import com.crucialticketing.entities.UserChangeLog;
import com.crucialticketing.entities.UserQueueCon;
import com.crucialticketing.entities.UserQueueConChangeLog;
import com.crucialticketing.entities.UserRoleCon;
import com.crucialticketing.entities.UserRoleConChangeLog;
import com.crucialticketing.entities.Workflow;
import com.crucialticketing.entities.WorkflowChangeLog;
import com.crucialticketing.entities.WorkflowStatus;
import com.crucialticketing.entities.WorkflowStatusChangeLog;
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

    //
    @Autowired
    RoleService roleService;

    @Autowired
    RoleChangeLogService roleChangeLogService;

    //
    @Autowired
    QueueService queueService;

    @Autowired
    QueueChangeLogService queueChangeLogService;

    //
    @Autowired
    UserRoleConService userRoleConService;

    @Autowired
    UserRoleConChangeLogService userRoleConChangeLogService;

    //
    @Autowired
    UserQueueConService userQueueConService;

    @Autowired
    UserQueueConChangeLogService userQueueConChangeLogService;

    //
    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationChangeLogService applicationChangeLogService;

    //
    @Autowired
    SeverityService severityService;

    @Autowired
    SeverityChangeLogService severityChangeLogService;

    //
    @Autowired
    WorkflowService workflowService;

    @Autowired
    WorkflowChangeLogService workflowChangeLogService;

    //
    @Autowired
    WorkflowStatusService workflowStatusService;

    @Autowired
    WorkflowStatusChangeLogService workflowStatusChangeLogService;

    //
    @Autowired
    ApplicationControlService applicationControlService;

    @Autowired
    ApplicationControlChangeLogService applicationControlChangeLogService;

    //
    @Autowired
    UserAlertService userAlertService;

    //
    public void executeJobList() {
        this.createUser();
        this.createRole();
        this.createQueue();
        this.createApplication();
        this.createSeverity();
        this.createTicketConfiguration();
        this.createWorkflowStatus();
        this.createWorkflow();
        this.createUserRoleConnection();
        this.createUserQueueConnection();
    }

    private void createUser() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<User> newUserList = userService.getUnprocessedUserList();

        for (User user : newUserList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (user.getUsername().equals(processedList.get(i))) {
                    userService.removeUser(user.getUserId());
                    i = processedList.size();
                    found = true;
                }
            }

            // Gets change log entry - needed for requestor details
            List<UserChangeLog> userChangeLogList = userChangeLogService.getUserChangeLogListByUserId(user.getUserId());

            // If the user is not found in the already processed list
            if (!found) {
                // Checks if the username exists in the db
                if (userService.doesUserExistInOnlineOrOffline(user.getUsername())) {
                    found = true;
                } else {
                    // Generates password for new user
                    PasswordHash passwordHash = new PasswordHash();
                    String generatedPassword = passwordHash.generatePassword(8);

                    try {
                        // Generates new hash for user
                        String hash = passwordHash.createHash(generatedPassword);

                        // Adds hash to user
                        userService.updateHash(user.getUserId(), hash);
                        user.getSecure().setHash(hash);

                        // Gets the roles requested with this new user
                        List<UserRoleCon> newUserRoleConList = userRoleConService.getUnprocessedUserRoleConListByUserId(user.getUserId(), true);

                        // Loops each role and checks if the same entry exists already
                        for (UserRoleCon userRoleCon : newUserRoleConList) {
                            if (userRoleConService.doesUserRoleConExistInOnlineOrOffline(user.getUserId(), userRoleCon.getRole().getRoleId())) {
                                userRoleConService.removeUserRoleCon(userRoleCon.getUserRoleConId());
                            } else {

                                userRoleConService.updateToOnline(userRoleCon.getUserRoleConId(),
                                        userChangeLogList.get(0).getTicket(),
                                        userChangeLogList.get(0).getRequestor());
                            }
                        }

                        // Adds user to processed list to skip any further requests for this username
                        processedList.add(user.getUsername());

                        // Updates user activation flag
                        userService.updateToOnline(user.getUserId(),
                                userChangeLogList.get(0).getTicket(),
                                userChangeLogList.get(0).getRequestor());

                        // Adds alert to requestors notification list
                        userAlertService.insertUserAlert(userChangeLogList.get(0).getRequestor().getUserId(),
                                "A user account has been setup with the following information: Username ("
                                + user.getUsername() + ") Password (" + generatedPassword + ")");

                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    }
                }
            }

            if (found) {
                processedList.add(user.getUsername());
                userService.removeUser(user.getUserId());
                userAlertService.insertUserAlert(userChangeLogList.get(0).getRequestor().getUserId(),
                        "This request could not be completed at this time. To avoid any "
                        + "further issues the request has been deleted");
            }
        }
    }

    private void createRole() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<Role> newRoleList = roleService.getUnprocessedRoleList();

        for (Role newRole : newRoleList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (newRole.getRoleName().equals(processedList.get(i))) {
                    found = true;
                }
            }

            List<RoleChangeLog> roleChangeLogList = roleChangeLogService.getChangeLogByRoleId(newRole);

            if (!found) {
                if (roleService.doesRoleExistInOnlineOrOffline(newRole.getRoleName())) {
                    found = true;
                } else {
                    roleService.updateToOnline(newRole.getRoleId(),
                            roleChangeLogList.get(0).getTicket(),
                            roleChangeLogList.get(0).getRequestor());

                    processedList.add(newRole.getRoleName());

                    userAlertService.insertUserAlert(roleChangeLogList.get(0).getRequestor().getUserId(),
                            "A role has been setup with the following information: role Name ("
                            + newRole.getRoleName() + ") Role Description (" + newRole.getRoleDescription() + ")");
                }
            }

            if (found) {
                processedList.add(newRole.getRoleName());
                roleService.removeRole(newRole.getRoleId());
                userAlertService.insertUserAlert(roleChangeLogList.get(0).getRequestor().getUserId(),
                        "A role was setup with the same role name before your request could be completed");
            }
        }
    }

    private void createQueue() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<Queue> queueList = queueService.getUnprocessedQueueList();

        for (Queue queue : queueList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (queue.getQueueName().equals(processedList.get(i))) {
                    found = true;
                }
            }

            List<QueueChangeLog> queueChangeLog = queueChangeLogService.getQueueChangeLogListByQueueId(queue.getQueueId());

            if (!found) {
                if (queueService.doesQueueExistInOnlineOrOffline(queue.getQueueName())) {
                    found = true;
                } else {
                    List<UserQueueCon> userQueueConList = userQueueConService.getUserListByQueueId(queue.getQueueId());

                    for (UserQueueCon userQueueCon : userQueueConList) {
                        userQueueConService.updateToOnline(userQueueCon.getUserQueueConId(),
                                queueChangeLog.get(0).getTicket(),
                                queueChangeLog.get(0).getRequestor());
                    }

                    queueService.updateToOnline(queue.getQueueId(),
                            queueChangeLog.get(0).getTicket(),
                            queueChangeLog.get(0).getRequestor());

                    userAlertService.insertUserAlert(queueChangeLog.get(0).getRequestor().getUserId(),
                            "A queue has been setup with the following information: Queue Name ("
                            + queue.getQueueName() + ")");
                }
            }

            if (found) {
                processedList.add(queue.getQueueName());
                queueService.removeQueue(queue.getQueueId());
                userAlertService.insertUserAlert(queueChangeLog.get(0).getRequestor().getUserId(),
                        "A role was setup with the same role name before your request could be completed");
            }
        }
    }

    private void createApplication() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<Application> applicationList = applicationService.getUnprocessedApplicationList();

        for (Application application : applicationList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (application.getApplicationName().equals(processedList.get(i))) {
                    found = true;
                }
            }

            List<ApplicationChangeLog> changeLog = applicationChangeLogService
                    .getApplicationChangeLogByApplicationId(application.getApplicationId());

            if (!found) {
                if (applicationService.doesApplicationExistInOnlineOrOffline(application.getApplicationName())) {
                    found = true;
                } else {
                    applicationService.updateToOnline(application.getApplicationId(),
                            changeLog.get(0).getTicket(),
                            changeLog.get(0).getRequestor());

                    userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                            "A application has been setup with the following information: Application Name ("
                            + application.getApplicationName() + ")");
                }
            }

            if (found) {
                processedList.add(application.getApplicationName());
                applicationService.removeApplication(application.getApplicationId());
                userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                        "A application was setup with the same application name before your request could be completed");
            }
        }
    }

    private void createSeverity() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<Severity> severityList = severityService.getUnprocessedList();

        for (Severity severity : severityList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (severity.getSeverityName().equals(processedList.get(i))) {
                    found = true;
                }
            }

            List<SeverityChangeLog> changeLog = severityChangeLogService
                    .getChangeLogBySeverityId(severity.getSeverityId());

            if (!found) {
                if (severityService.doesSeverityExistInOnlineOrOfflineByLevel(severity.getSeverityLevel())) {
                    found = true;
                } else {
                    severityService.updateToOnline(severity.getSeverityId(),
                            changeLog.get(0).getTicket(),
                            changeLog.get(0).getRequestor());

                    userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                            "A severity has been setup with the following information: Severity Name ("
                            + severity.getSeverityName() + ")");
                }
            }

            if (found) {
                processedList.add(severity.getSeverityName());
                severityService.removeSeverity(severity.getSeverityId());
                userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                        "A severity was setup with the same severity name before your request could be completed");
            }
        }
    }

    private void createTicketConfiguration() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<ApplicationControl> applicationControlList = applicationControlService.getUnprocessedList(false);

        for (ApplicationControl applicationControl : applicationControlList) {
            found = false;

            String combinedString = String.valueOf(applicationControl.getApplication().getApplicationId()) + "-"
                    + String.valueOf(applicationControl.getSeverity().getSeverityId()) + "-"
                    + String.valueOf(applicationControl.getTicketType().getTicketTypeId());

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (combinedString.equals(processedList.get(i))) {
                    found = true;
                }
            }

            List<ApplicationControlChangeLog> changeLog = applicationControlChangeLogService
                    .getChangeLogListByApplicationControlId(applicationControl.getApplicationControlId());

            if (!found) {
                if (applicationControlService.doesApplicationControlExistInOnlineOrOffline(
                        applicationControl.getTicketType().getTicketTypeId(),
                        applicationControl.getApplication().getApplicationId(),
                        applicationControl.getSeverity().getSeverityId())) {
                    found = true;
                } else {

                    applicationControlService.updateToOnline(applicationControl.getApplicationControlId(),
                            changeLog.get(0).getTicket(),
                            changeLog.get(0).getRequestor());

                    userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                            "A configuration has been setup with the following information: "
                            + "Ticket Type (" + applicationControl.getTicketType().getTicketTypeName() + ") "
                            + "Application (" + applicationControl.getApplication().getApplicationName() + ") "
                            + "Severity (" + applicationControl.getSeverity().getSeverityLevel() + " : "
                            + applicationControl.getSeverity().getSeverityName() + ") "
                            + "Workflow (" + applicationControl.getWorkflow().getWorkflowName() + ") ");
                }
            }

            if (found) {
                processedList.add(combinedString);
                applicationControlService.removeApplicationControl(applicationControl.getApplicationControlId());
                userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                        "A configuration was setup with the same configuration set before your request could be completed");
            }
        }
    }

    private void createWorkflowStatus() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<WorkflowStatus> workflowStatusList = workflowStatusService.getUnprocessedList();

        for (WorkflowStatus workflowStatus : workflowStatusList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (workflowStatus.getWorkflowStatusName().equals(processedList.get(i))) {
                    found = true;
                }
            }

            List<WorkflowStatusChangeLog> changeLog = workflowStatusChangeLogService
                    .getChangeLogByWorkflowStatusId(workflowStatus.getWorkflowStatusId());

            if (!found) {
                if (workflowStatusService.doesWorkflowStatusExistInOnlineOrOffline(
                        workflowStatus.getWorkflowStatusName())) {
                    found = true;
                } else {
                    workflowStatusService.updateToOnline(workflowStatus.getWorkflowStatusId(),
                            changeLog.get(0).getTicket(),
                            changeLog.get(0).getRequestor());

                    userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                            "A workflow status has been setup with the following information: "
                            + "Workflow Status Name (" + workflowStatus.getWorkflowStatusName() + ")");
                }
            }

            if (found) {
                processedList.add(workflowStatus.getWorkflowStatusName());
                workflowStatusService.removeWorkflowStatus(workflowStatus.getWorkflowStatusId());
                userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                        "A workflow status was setup with the same name before your request could be completed");
            }
        }
    }

    private void createWorkflow() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<Workflow> workflowList = workflowService.getUnprocessedList();

        for (Workflow workflow : workflowList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {

                if (workflow.getWorkflowName().equals(processedList.get(i))) {
                    found = true;
                }
            }

            List<WorkflowChangeLog> changeLog = workflowChangeLogService
                    .getChangeLogListByWorkflowId(workflow.getWorkflowId());

            if (!found) {
                if (workflowService.doesWorkflowExistInOnlineOrOffline(
                        workflow.getWorkflowName())) {
                    found = true;
                } else {
                    workflowService.updateToOnline(workflow.getWorkflowId(),
                            changeLog.get(0).getTicket(),
                            changeLog.get(0).getRequestor());

                    userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                            "A workflow has been setup with the following information: "
                            + "Workflow Name (" + workflow.getWorkflowName() + ")");
                }
            }

            if (found) {
                processedList.add(workflow.getWorkflowName());
                workflowService.removeWorkflow(workflow.getWorkflowId());
                userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                        "A workflow was setup with the same name before your request could be completed");
            }
        }
    }

    private void createUserRoleConnection() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<UserRoleCon> userRoleConList = userRoleConService.getUnprocessedUserRoleConList(false);

        for (UserRoleCon userRoleCon : userRoleConList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (processedList.get(i).equals(userRoleCon.getUser().getUserId() + "-" + userRoleCon.getRole().getRoleId())) {
                    found = true;
                }
            }

            List<UserRoleConChangeLog> changeLog = userRoleConChangeLogService.getChangeLogByUserRoleConId(userRoleCon.getUserRoleConId());

            if (!found) {
                if (userRoleConService.doesUserRoleConExistInOnlineOrOffline(userRoleCon.getUser().getUserId(), userRoleCon.getRole().getRoleId())) {
                    found = true;
                } else {
                    userRoleConService.updateToOnline(userRoleCon.getUserRoleConId(),
                            changeLog.get(0).getTicket(),
                            changeLog.get(0).getRequestor());

                    userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                            "A user role connection has been setup with the following information: "
                            + "Username (" + userRoleCon.getUser().getUsername() + "), Role Name (" + userRoleCon.getRole().getRoleName() + ")");
                }
            }

            if (found) {
                processedList.add(userRoleCon.getUser().getUserId() + "-" + userRoleCon.getRole().getRoleId());
                userRoleConService.removeUserRoleCon(userRoleCon.getUserRoleConId());
                userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                        "A user role connection was setup with the same details before your request could be completed");
            }
        }
    }
    
    private void createUserQueueConnection() {
        ArrayList<String> processedList = new ArrayList<>();
        boolean found;

        List<UserQueueCon> userQueueConList = userQueueConService.getUnprocessedUserQueueConList(false);

        for (UserQueueCon userQueueCon : userQueueConList) {
            found = false;

            for (int i = 0; (i < processedList.size()) && (!found); i++) {
                if (processedList.get(i).equals(userQueueCon.getUser().getUserId() + "-" + userQueueCon.getQueue().getQueueName())) {
                    found = true;
                }
            }

            List<UserQueueConChangeLog> changeLog = userQueueConChangeLogService.getChangeLogByUserQueueConId(userQueueCon.getUserQueueConId());

            if (!found) {
                if (userQueueConService.doesUserQueueConExistOnlineOrOffline(userQueueCon.getUser().getUserId(), userQueueCon.getQueue().getQueueId())) {
                    found = true;
                } else {
                    userQueueConService.updateToOnline(userQueueCon.getUserQueueConId(),
                            changeLog.get(0).getTicket(),
                            changeLog.get(0).getRequestor());

                    userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                            "A user queue connection has been setup with the following information: "
                            + "Username (" + userQueueCon.getUser().getUsername() + "), Queue Name (" + userQueueCon.getQueue().getQueueName()+ ")");
                }
            }

            if (found) {
                processedList.add(userQueueCon.getUser().getUserId() + "-" + userQueueCon.getQueue().getQueueName());
                userQueueConService.removeUserQueueCon(userQueueCon.getUserQueueConId());
                userAlertService.insertUserAlert(changeLog.get(0).getRequestor().getUserId(),
                        "A user queue connection was setup with the same details before your request could be completed");
            }
        }
    }
}
