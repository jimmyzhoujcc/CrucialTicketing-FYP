/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.util;

import com.crucialticketing.entities.Ticket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ReportView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<Ticket> ticketList = (List<Ticket>) model.get("ticketList");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));

        //create a wordsheet
        HSSFSheet sheet = workbook.createSheet("Ticket Report");

        HSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("Ticket ID");
        header.createCell(1).setCellValue("Ticket Type");
        header.createCell(2).setCellValue("Short Description");
        header.createCell(3).setCellValue("Application");
        header.createCell(4).setCellValue("Severity");
        header.createCell(5).setCellValue("Workflow");
        header.createCell(6).setCellValue("Role");
        header.createCell(7).setCellValue("Workflow Status");
        header.createCell(8).setCellValue("SLA Clock");
        header.createCell(9).setCellValue("SLA Elapsed");
        header.createCell(10).setCellValue("SLA Status");
        header.createCell(11).setCellValue("Created By");
        header.createCell(12).setCellValue("Created On");
        header.createCell(13).setCellValue("Reported By");
        header.createCell(14).setCellValue("Reported On");
        header.createCell(15).setCellValue("Last Updated By");
        header.createCell(16).setCellValue("Last Updated On");

        int rowNum = 1;
        for (Ticket ticket : ticketList) {
            //create the row data
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ticket.getTicketId());
            row.createCell(1).setCellValue(ticket.getApplicationControl().getTicketType().getTicketTypeName());
            row.createCell(2).setCellValue(ticket.getShortDescription());
            row.createCell(3).setCellValue(ticket.getApplicationControl().getApplication().getApplicationName());
            row.createCell(4).setCellValue(ticket.getApplicationControl().getSeverity().getSeverityLevel() + ":" + ticket.getApplicationControl().getSeverity().getSeverityName());
            row.createCell(5).setCellValue(ticket.getApplicationControl().getWorkflow().getWorkflowName());
            row.createCell(6).setCellValue(ticket.getApplicationControl().getRole().getRoleName());
            row.createCell(7).setCellValue(ticket.getCurrentWorkflowStep().getWorkflowStatus().getWorkflowStatusName());
            row.createCell(8).setCellValue(ticket.getApplicationControl().getSlaClock());
            row.createCell(9).setCellValue(ticket.getChangeLog().getTimeElapsed());

            if (ticket.getApplicationControl().getSlaClock() >= ticket.getChangeLog().getTimeElapsed()) {
                row.createCell(10).setCellValue("Not Violated");
            } else {
                row.createCell(10).setCellValue("Violated");
            }

            row.createCell(11).setCellValue(ticket.getChangeLog().getChangeLog().get(0).getUser().getUsername());

            Date date = new Date(ticket.getChangeLog().getChangeLog().get(0).getStamp() * 1000);
            String formattedDate = sdf.format(date);

            row.createCell(12).setCellValue(formattedDate);
            row.createCell(13).setCellValue(ticket.getChangeLog().getChangeLog().get(0).getUser().getUsername());
            row.createCell(14).setCellValue(formattedDate);
            row.createCell(15).setCellValue(ticket.getChangeLog().getChangeLog().get(ticket.getChangeLog().getChangeLog().size() - 1).getUser().getUsername());
            
            date = new Date(ticket.getChangeLog().getChangeLog().get(ticket.getChangeLog().getChangeLog().size() - 1).getStamp() * 1000);
            formattedDate = sdf.format(date);
            
            row.createCell(16).setCellValue(formattedDate);
        }
    }
}
