/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.entities.TicketType;
import com.crucialticketing.daos.TicketTypeDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Daniel Foley
 */
public class TicketTypeService extends JdbcDaoSupport implements TicketTypeDao {
   
    @Override
    public TicketType getTicketTypeById(int ticketTypeId) {
        String sql = "SELECT * FROM ticket_type WHERE ticket_type_id=?";
        List<Map<String,Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[] { ticketTypeId });
        if(rs.size() != 1) {
            return new TicketType();
        }
        return (this.rowMapper(rs)).get(0);
    }

    @Override
    public List<TicketType> getTicketTypeList() {
        String sql = "SELECT * FROM ticket_type";
        List<Map<String,Object>> rs = this.getJdbcTemplate().queryForList(sql);
        if(rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }
    
    @Override
    public boolean doesTicketTypeExist(int ticketTypeId) {
        return this.getTicketTypeById(ticketTypeId).getTicketTypeId() != 0;
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
}
