/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.crucialticketing.entities.Application;

/**
 *
 * @author Daniel Foley
 */
public class ApplicationService implements DatabaseService {

    @Autowired
    DataSource dataSource;

    @Override
    public void insert(Object o) {

    }

    @Override
    public List<Object> select(String field, String value) {
        return new ArrayList<Object>();
    }

    @Override
    public void update(String filterField, String filterValue, String updateField, String updateValue) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public List<Object> getTable() {
        return new ArrayList<Object>();
    }
}
