/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DanFoley
 */
public class ChangeLog {

    private List<ChangeLogEntry> changeLog;
    private int timeElapsed;

    public ChangeLog() {
        changeLog = new ArrayList<>();
    }

    public List<ChangeLogEntry> getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(List<ChangeLogEntry> changeLog) {
        this.changeLog = changeLog;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public void setTimeElapsed() {
        ChangeLogEntry prevChangeLogEntry = null;
        this.timeElapsed = 0;
        boolean clockActive = false;
        int lastStampRecorded = 0;
        int currentStamp = (int) (System.currentTimeMillis() / 1000);

        for (ChangeLogEntry changeLogEntry : changeLog) {

            if (prevChangeLogEntry == null) {
                prevChangeLogEntry = changeLogEntry;
            } else {
                WorkflowStep prevWorkflowStep
                        = prevChangeLogEntry.getApplicationControl().
                        getWorkflow().getWorkflowMap().
                        getWorkflowStageByStatus(prevChangeLogEntry.getWorkflowStatus().getStatusId());

                clockActive = (prevWorkflowStep.getClockActive() != 0);

                if (clockActive) {
                    this.timeElapsed += changeLogEntry.getStamp() - prevChangeLogEntry.getStamp();
                }
                // If the SLA clock has changed 
                if (prevChangeLogEntry.getApplicationControl().getSlaClock() != changeLogEntry.getApplicationControl().getSlaClock()) {
                    float convTimeElapsed = (float) this.timeElapsed / prevChangeLogEntry.getApplicationControl().getSlaClock();
                    this.timeElapsed = (int) (convTimeElapsed * changeLogEntry.getApplicationControl().getSlaClock());
                }

                prevChangeLogEntry = changeLogEntry;
            }
        }

        if (prevChangeLogEntry != null) {
            WorkflowStep workflowStep = prevChangeLogEntry
                    .getApplicationControl()
                    .getWorkflow()
                    .getWorkflowMap()
                    .getWorkflowStageByStatus(prevChangeLogEntry.getWorkflowStatus().getStatusId());

            if (!workflowStep.getNextWorkflowStep().isEmpty()) {
                if (prevChangeLogEntry
                        .getApplicationControl()
                        .getWorkflow()
                        .getWorkflowMap()
                        .getWorkflowStageByStatus(prevChangeLogEntry.getWorkflowStatus().getStatusId()).getClockActive() != 0) {
                    this.timeElapsed += currentStamp - prevChangeLogEntry.getStamp();
                }
            }
        }

    }
}
