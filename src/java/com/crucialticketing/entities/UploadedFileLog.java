/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.List;

/**
 *
 * @author DanFoley
 */
public class UploadedFileLog {
    private List<UploadedFile> files;
    
    public UploadedFileLog() {}

    public List<UploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }
    
    
    
}
