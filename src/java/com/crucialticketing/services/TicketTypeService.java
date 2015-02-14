/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.ApplicationControl;
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
public class TicketTypeService implements TicketTypeDao {
   
    String selectByTicketTypeId = "SELECT * FROM ticket_type WHERE ticket_type_id=?";
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Override
    public TicketType getTicketTypeById(int ticketTypeId) {
        String sql = selectByTicketTypeId;
        List<Map<String,Object>> rs = jdbcTemplate.queryForList(sql, new Object[] { ticketTypeId });
        if(rs.size() != 1) {
            return new TicketType();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<TicketType> getTicketTypeList() {
        String sql = "SELECT * FROM ticket_type";
        List<Map<String,Object>> rs = jdbcTemplate.queryForList(sql);
        if(rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public boolean doesTicketTypeExist(int ticketTypeId) {
        if(this.getTicketTypeById(ticketTypeId).getTicketTypeId() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<TicketType> rowMapper(List<Map<String, Object>> resultSet) {
         List<TicketType> ticketTypeList = new ArrayList<>();

        for (Map row : resultSet) {
            TicketType ticketType = new TicketType();

            ticketType.setTicketTypeId((int) row.get("ticket_type_id"));
            ticketType.setTicketTypeName((String) row.get("ticket_type_name"));

            ticketTypeList.add(ticketType);
        }
        return ticketTypeList;
    }

    @Override
    public void setCon(JdbcTemplate con) {
        jdbcTemplate = con;
    }

    
}
