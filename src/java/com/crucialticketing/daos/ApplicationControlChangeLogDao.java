/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.daos;

import com.crucialticketing.entities.ApplicationControlChangeLog;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanFoley
 */
public interface ApplicationControlChangeLogDao {
    public void insertChangeLog(ApplicationControlChangeLog applicationControlChangeLog);

    public List<ApplicationControlChangeLog> getChangeLogListByApplicationControlId(int applicationControlId);

    public List<ApplicationControlChangeLog> getChangeLogListByTicketId(int ticketId);

    public List<ApplicationControlChangeLog> rowMapper(List<Map<String, Object>> resultSet);
}
