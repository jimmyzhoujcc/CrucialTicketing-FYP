/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos.services;

import com.crucialticketing.entities.Attachment;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.User;
import com.crucialticketing.daos.AttachmentDao;
import static com.crucialticketing.util.Timestamp.getTimestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class AttachmentService extends JdbcDaoSupport implements AttachmentDao {

    @Override
    public void insertAttachment(int ticketId, int userId, String fileName, String name, String description) {
        String sql = "INSERT INTO file_upload "
                + "(ticket_id, user_id, file_name, name, description, stamp) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        this.getJdbcTemplate().update(sql, new Object[]{ticketId, userId, fileName, name, description, getTimestamp()});
    }

    @Override
    public Attachment getAttachmentById(int fileUploadId) {
        String sql = "SELECT * FROM file_upload WHERE file_upload_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{fileUploadId});

        if (rs.isEmpty()) {
            return new Attachment();
        }
        return this.rowMapper(rs).get(0);
    }

    @Override
    public List<Attachment> getAttachmentListByTicketId(int ticketId) {
        String sql = "SELECT * FROM file_upload WHERE ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});

        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public boolean doesFileUploadExist(String fileName, int ticketId) {
        String sql = "SELECT COUNT(file_upload_id) AS result FROM file_upload "
                + "WHERE file_name=? AND ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{fileName, ticketId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
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
            attachment.setName((String) row.get("name"));
            attachment.setDescription((String) row.get("description"));
            attachment.setStamp((int) row.get("stamp"));
            attachmentList.add(attachment);
        }

        return attachmentList;
    }
}
