/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author DanFoley
 */
public class Attachment {
    private int fileUploadId;
    private Ticket ticket;
    private User user;
    private String fileName;
    private String name;
    private String description;
    private int stamp;

    public Attachment() {}
    
    public Attachment(int fileUploadId, Ticket ticket, User user, String fileName, int stamp) {
        this.fileUploadId = fileUploadId;
        this.ticket = ticket;
        this.user = user;
        this.fileName = fileName;
        this.stamp = stamp;
    }

    public int getFileUploadId() {
        return fileUploadId;
    }

    public void setFileUploadId(int fileUploadId) {
        this.fileUploadId = fileUploadId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

    
    
}
