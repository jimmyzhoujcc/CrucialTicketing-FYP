/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author DanFoley
 */
public interface DatabaseService {
    public void setCon(JdbcTemplate con);
}
