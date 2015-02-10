/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Attachment;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface AttachmentDao extends DatabaseService {
    
    public void insertAttachment(int ticketId, int userId, String fileName, int stamp);
    
    public List<Attachment> getAttachmentListByTicketId(int ticketId);

    public List<Attachment> rowMapper(List<Map<String, Object>> resultSet);
}
