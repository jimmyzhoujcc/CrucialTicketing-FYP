/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import java.util.List;

/**
 *
 * @author DanFoley
 */
public interface DatabaseService {

    public void insert(Object o);

    public List<Object> select(String field, String value);

    public void update(Object o);

    public void delete(Object o);

    public List<Object> getTable();
}
