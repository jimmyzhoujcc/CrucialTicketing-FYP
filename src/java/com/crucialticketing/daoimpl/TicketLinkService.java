/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daoimpl;

import com.crucialticketing.daos.TicketLinkDao;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.entities.TicketLink;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author DanFoley
 */
public class TicketLinkService extends JdbcDaoSupport implements TicketLinkDao {

    @Override
    public void insertTicketLink(TicketLink ticketLink) {
        String sql = "INSERT INTO ticket_link (from_ticket_id, to_ticket_id) VALUES (?, ?)";

        this.getJdbcTemplate().update(sql, new Object[]{
            ticketLink.getFromTicket().getTicketId(),
            ticketLink.getToTicket().getTicketId()
        });
    }

    @Override
    public List<TicketLink> getTicketLink(int ticketId) {
        String sql = "SELECT * FROM ticket_link WHERE from_ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{ticketId});
        if (rs.isEmpty()) {
            return new ArrayList<>();
        }
        return this.rowMapper(rs);
    }

    @Override
    public boolean doesTicketLinkExist(int fromTicketId, int toTicketId) {
        String sql = "SELECT COUNT(ticket_link_id) AS result FROM ticket_link "
                + "WHERE from_ticket_id=? AND to_ticket_id=?";
        List<Map<String, Object>> rs = this.getJdbcTemplate().queryForList(sql, new Object[]{fromTicketId, toTicketId});
        int result = Integer.valueOf(rs.get(0).get("result").toString());
        return result != 0;
    }

    private List<TicketLink> rowMapper(List<Map<String, Object>> resultSet) {
        List<TicketLink> ticketLinkList = new ArrayList<>();

        for (Map row : resultSet) {
            TicketLink ticketLink = new TicketLink();

            ticketLink.setTicketLinkId((int) row.get("ticket_link_id"));
            ticketLink.setFromTicket(new Ticket((int) row.get("from_ticket_id")));
            ticketLink.setToTicket(new Ticket((int) row.get("to_ticket_id")));

            ticketLinkList.add(ticketLink);
        }
        return ticketLinkList;
    }
}
