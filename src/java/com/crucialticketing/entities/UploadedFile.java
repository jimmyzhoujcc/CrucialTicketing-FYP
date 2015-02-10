/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author DanFoley
 */
public class UploadedFile {

    private MultipartFile file;

    public UploadedFile() {}
    
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
