/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.entities.Attachment;
import com.crucialticketing.services.AttachmentService;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application file upload requests
 */
@RequestMapping(value = "/file/")
@Controller
public class FileController {

    @Autowired
    DataSource dataSource;

    @RequestMapping(value = "/{filename}/", method = RequestMethod.GET)
    @ResponseBody
    public void fileDispatcher(
            @PathVariable(value = "filename") String fileUploadId,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap map) {

        String fileName = request.getServletContext().getRealPath("/WEB-INF/upload/");

        AttachmentService attachmentService = new AttachmentService();
        attachmentService.setCon(new JdbcTemplate(dataSource));
        Attachment attachment = attachmentService.getAttachmentById(Integer.valueOf(fileUploadId));

        if (attachment.getFileUploadId() == 0) {

        }

        fileName += "\\" + attachment.getFileName();

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(new File(fileName)));

            String extension = "unknown";

            int i = fileName.lastIndexOf('.');

            if (i > 0) {
                extension = fileName.substring(i + 1);
                fileName = fileName.substring(0, i);
            }

            response.setContentType("application/" + extension);
            response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getFileName());

            ServletOutputStream out = response.getOutputStream();
            IOUtils.copy(in, out);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
