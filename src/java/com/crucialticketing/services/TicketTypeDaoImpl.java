/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.TicketType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Daniel Foley
 */
public class TicketTypeDaoImpl implements TicketTypeDao {
    
    @Autowired
    DataSource dataSource;
    
    @Override
    public void insertTicketType(TicketType ticketType) {
        
    }
    
    @Override
    public List<TicketType> getTicketTypeList() {
        List<TicketType> ticketTypeList = new ArrayList<>();
        return ticketTypeList;
    }

    @Override
    public void updateTicketType(TicketType ticketType) {
        
    }

    @Override
    public void deleteTicketType(String id) { 
        
    }

    @Override
    public TicketType getTicketTypeById(String id) { 
        TicketType ticketType = new TicketType();
        
        String sql = "SELECT * FROM ticket_type WHERE ticket_type_id= " + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> ticketTypeData = jdbcTemplate.queryForList(sql);
        
        ticketType.setTicketTypeId((int)ticketTypeData.get(0).get("ticket_type_id"));
        ticketType.setTicketTypeName((String)ticketTypeData.get(0).get("ticket_type_name"));
        return ticketType;
    }
}
