/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.UploadedFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class UploadController {

    /**
     * Upload single file using Spring Controller
     *
     * @param name
     * @param uploadForm
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadfile", method = RequestMethod.POST)
    public String handleFormUpload(
            HttpServletRequest request,
            @ModelAttribute("uploadedfile") UploadedFile uploadForm,
            ModelMap map) {

        try {
            for (MultipartFile file : uploadForm.getFiles()) {
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();

                    String fileName = file.getOriginalFilename();

                    String extension = ".unknown";

                    int i = fileName.lastIndexOf('.');

                    if (i > 0) {
                        extension = fileName.substring(i + 1);
                        fileName = fileName.substring(0, i);
                    }

                    String saveFile = request.getServletContext().getRealPath("/WEB-INF/upload/" + fileName + "." + extension);

                    FileOutputStream output = new FileOutputStream(new File(saveFile));
                    output.write(bytes);
                    output.close();

                }
            }
        } catch (Exception e) {

        }

        map.addAttribute("page", "menu/main.jsp");
        return "mainview";
    }

}
