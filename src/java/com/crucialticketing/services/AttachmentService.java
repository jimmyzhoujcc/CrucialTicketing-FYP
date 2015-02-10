/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Attachment;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public class AttachmentService implements AttachmentDao {

    private JdbcTemplate con;

    @Override
    public void insertAttachment(int ticketId, int userId, String fileName, int stamp) {
        String sql = "INSERT INTO file_upload (ticket_id, user_id, file_name, stamps) VALUES (?, ?, ?, ?)";
        con.update(sql, new Object[]{ticketId, userId, fileName, stamp});
    }

    @Override
    public List<Attachment> getAttachmentListByTicketId(int ticketId) {
        String sql = "SELECT * FROM file_upload WHERE ticket_id=?";
        List<Map<String, Object>> rs = con.queryForList(sql, new Object[]{ticketId});
        
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public List<Attachment> rowMapper(List<Map<String, Object>> resultSet) {
        List<Attachment> attachmentList = new ArrayList<>();

        for (Map row : resultSet) {
            Attachment attachment = new Attachment();
            attachment.setFileUploadId((int) row.get("file_upload_id"));
            attachment.setTicket(new Ticket((int) row.get("ticket_id")));
            attachment.setUser(new User((int) row.get("user_id")));
            attachment.setFileName((String) row.get("file_name"));
            attachment.setStamp((int) row.get("stamp"));
            attachmentList.add(attachment);
        }

        return attachmentList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        this.con = con;
    }
}
