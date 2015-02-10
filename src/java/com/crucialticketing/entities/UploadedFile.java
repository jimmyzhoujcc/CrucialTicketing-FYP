/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author DanFoley
 */
public class UploadedFile {

    private List<MultipartFile> files;

    public UploadedFile() {
        files = new ArrayList<>();
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFile(List<MultipartFile> files) {
        this.files = files;
    }  
}
