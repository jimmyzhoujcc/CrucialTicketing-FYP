/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.schedule;

import com.crucialticketing.daoimpl.ApplicationControlLockRequestService;
import com.crucialticketing.daoimpl.ApplicationLockRequestService;
import com.crucialticketing.daoimpl.QueueLockRequestService;
import com.crucialticketing.daoimpl.RoleLockRequestService;
import com.crucialticketing.daoimpl.SeverityLockRequestService;
import com.crucialticketing.entities.TicketLockRequest;
import com.crucialticketing.daoimpl.TicketLockRequestService;
import com.crucialticketing.daoimpl.UserLockRequestService;
import com.crucialticketing.daoimpl.WorkflowLockRequestService;
import com.crucialticketing.daoimpl.WorkflowStatusLockRequestService;
import com.crucialticketing.entities.ApplicationControlLockRequest;
import com.crucialticketing.entities.ApplicationLockRequest;
import com.crucialticketing.entities.QueueLockRequest;
import com.crucialticketing.entities.RoleLockRequest;
import com.crucialticketing.entities.SeverityLockRequest;
import com.crucialticketing.entities.UserLockRequest;
import com.crucialticketing.entities.WorkflowLockRequest;
import com.crucialticketing.entities.WorkflowStatusLockRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author DanFoley
 */
@Component
public class AccessJob {

    @Autowired
    TicketLockRequestService ticketLockRequestService;

    @Autowired
    UserLockRequestService userLockRequestService;

    @Autowired
    RoleLockRequestService roleLockRequestService;

    @Autowired
    QueueLockRequestService queueLockRequestService;

    @Autowired
    ApplicationLockRequestService applicationLockRequestService;

    @Autowired
    SeverityLockRequestService severityLockRequestService;

    @Autowired
    ApplicationControlLockRequestService applicationControlLockRequestService;

    @Autowired
    WorkflowStatusLockRequestService workflowStatusLockRequestService;

    @Autowired
    WorkflowLockRequestService workflowLockRequestService;

    /**
     *
     */
    public void executeAccessList() {
        this.executeTicketAccessJob();
        this.executeUserAccessJob();
        this.executeRoleAccessJob();
        this.executeQueueAccessJob();
        this.executeSeverityAccessJob();
        this.executeApplicationAccessJob();
        this.executeApplicationControlAccessJob();
        this.executeWorkflowStatusAccessJob();
        this.executeWorkflowAccessJob();
    }

    private void executeTicketAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<TicketLockRequest> lockRequestList = ticketLockRequestService.getOpenRequestList();

        for (TicketLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getTicket().getTicketId()) {
                    found = true;
                }
            }

            if (found) {
                ticketLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getTicket(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (ticketLockRequestService.checkIfOpen(lockRequest.getTicket().getTicketId())) {
                    ticketLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getTicket(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getTicket().getTicketId());
                } else {
                    processedList.add(lockRequest.getTicket().getTicketId());
                    ticketLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

    private void executeUserAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<UserLockRequest> lockRequestList = userLockRequestService.getOpenRequestList();

        for (UserLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getUser().getUserId()) {
                    found = true;
                }
            }

            if (found) {
                userLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getUser(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (userLockRequestService.checkIfOpen(lockRequest.getUser().getUserId())) {
                    userLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getUser(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getUser().getUserId());
                } else {
                    processedList.add(lockRequest.getUser().getUserId());
                    userLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

    private void executeRoleAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<RoleLockRequest> lockRequestList = roleLockRequestService.getOpenRequestList();

        for (RoleLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getRole().getRoleId()) {
                    found = true;
                }
            }

            if (found) {
                roleLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getRole(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (roleLockRequestService.checkIfOpen(lockRequest.getRole().getRoleId())) {
                    roleLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getRole(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getRole().getRoleId());
                } else {
                    processedList.add(lockRequest.getRole().getRoleId());
                    roleLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

    private void executeQueueAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<QueueLockRequest> lockRequestList = queueLockRequestService.getOpenRequestList();

        for (QueueLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getQueue().getQueueId()) {
                    found = true;
                }
            }

            if (found) {
                queueLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getQueue(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (queueLockRequestService.checkIfOpen(lockRequest.getQueue().getQueueId())) {
                    queueLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getQueue(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getQueue().getQueueId());
                } else {
                    processedList.add(lockRequest.getQueue().getQueueId());
                    queueLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

    private void executeApplicationAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<ApplicationLockRequest> lockRequestList = applicationLockRequestService.getOpenRequestList();

        for (ApplicationLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getApplication().getApplicationId()) {
                    found = true;
                }
            }

            if (found) {
                applicationLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getApplication(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (applicationLockRequestService.checkIfOpen(lockRequest.getApplication().getApplicationId())) {
                    applicationLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getApplication(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getApplication().getApplicationId());
                } else {
                    processedList.add(lockRequest.getApplication().getApplicationId());
                    applicationLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

    private void executeSeverityAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<SeverityLockRequest> lockRequestList = severityLockRequestService.getOpenRequestList();

        for (SeverityLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getSeverity().getSeverityId()) {
                    found = true;
                }
            }

            if (found) {
                severityLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getSeverity(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (severityLockRequestService.checkIfOpen(lockRequest.getSeverity().getSeverityId())) {
                    severityLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getSeverity(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getSeverity().getSeverityId());
                } else {
                    processedList.add(lockRequest.getSeverity().getSeverityId());
                    severityLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

    private void executeApplicationControlAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<ApplicationControlLockRequest> lockRequestList = applicationControlLockRequestService.getOpenRequestList();

        for (ApplicationControlLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getApplicationControl().getApplicationControlId()) {
                    found = true;
                }
            }

            if (found) {
                applicationControlLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getApplicationControl(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (applicationControlLockRequestService.checkIfOpen(lockRequest.getApplicationControl().getApplicationControlId())) {
                    applicationControlLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getApplicationControl(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getApplicationControl().getApplicationControlId());
                } else {
                    processedList.add(lockRequest.getApplicationControl().getApplicationControlId());
                    applicationControlLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

    private void executeWorkflowStatusAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<WorkflowStatusLockRequest> lockRequestList = workflowStatusLockRequestService.getOpenRequestList();

        for (WorkflowStatusLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getWorkflowStatus().getWorkflowStatusId()) {
                    found = true;
                }
            }

            if (found) {
                workflowStatusLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getWorkflowStatus(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (workflowStatusLockRequestService.checkIfOpen(lockRequest.getWorkflowStatus().getWorkflowStatusId())) {
                    workflowStatusLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getWorkflowStatus(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getWorkflowStatus().getWorkflowStatusId());
                } else {
                    processedList.add(lockRequest.getWorkflowStatus().getWorkflowStatusId());
                    workflowStatusLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

    private void executeWorkflowAccessJob() {
        List<Integer> processedList = new ArrayList<>();
        boolean found;

        List<WorkflowLockRequest> lockRequestList = workflowLockRequestService.getOpenRequestList();

        for (WorkflowLockRequest lockRequest : lockRequestList) {
            found = false;
            for (int i = 0; (i < processedList.size()) && (found == false); i++) {
                if (processedList.get(i) == lockRequest.getWorkflow().getWorkflowId()) {
                    found = true;
                }
            }

            if (found) {
                workflowLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                        lockRequest.getWorkflow(),
                        lockRequest.getRequestor().getUserId());
            } else {
                if (workflowLockRequestService.checkIfOpen(lockRequest.getWorkflow().getWorkflowId())) {
                    workflowLockRequestService.denyAccess(lockRequest.getLockRequestId(),
                            lockRequest.getWorkflow(),
                            lockRequest.getRequestor().getUserId());

                    processedList.add(lockRequest.getWorkflow().getWorkflowId());
                } else {
                    processedList.add(lockRequest.getWorkflow().getWorkflowId());
                    workflowLockRequestService.grantAccess(lockRequest.getLockRequestId());
                }
            }
        }
    }

}
