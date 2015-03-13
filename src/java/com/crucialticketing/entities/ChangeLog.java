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
        ChangeLogEntry prevChangeLogEntry;
        ChangeLogEntry currChangeLogEntry;
        this.timeElapsed = 0;
        boolean clockActive;
        int currentStamp = (int) (System.currentTimeMillis() / 1000);

        if (changeLog.isEmpty()) {
            return;
        }

        prevChangeLogEntry = changeLog.get(0);

        if (changeLog.size() == 1) {
            currChangeLogEntry = prevChangeLogEntry;
        }

        int i = 0;

        for (ChangeLogEntry tempChangeLogEntry : changeLog) {
            if (i == 0) {
                i++;
            } else {
                currChangeLogEntry = tempChangeLogEntry;

                clockActive = prevChangeLogEntry.getApplicationControl().
                        getWorkflow().getWorkflowMap().
                        getWorkflowStageByStatus(prevChangeLogEntry.getWorkflowStatus().getWorkflowStatusId()).getClockActive() != 0;

                if (clockActive) {
                    this.timeElapsed += currChangeLogEntry.getStamp() - prevChangeLogEntry.getStamp();
                }

                // If the SLA clock has changed 
                if (prevChangeLogEntry.getApplicationControl().getSlaClock() != currChangeLogEntry.getApplicationControl().getSlaClock()) {
                    float convTimeElapsed = (float) this.timeElapsed / prevChangeLogEntry.getApplicationControl().getSlaClock();
                    this.timeElapsed = (int) (convTimeElapsed * currChangeLogEntry.getApplicationControl().getSlaClock());
                }

                prevChangeLogEntry = currChangeLogEntry;
            }
        }

        if (prevChangeLogEntry.getApplicationControl().
                getWorkflow().getWorkflowMap().
                getWorkflowStageByStatus(prevChangeLogEntry.getWorkflowStatus().getWorkflowStatusId()).getClockActive() != 0) {
            this.timeElapsed += currentStamp - prevChangeLogEntry.getStamp();
        }

    }
}
