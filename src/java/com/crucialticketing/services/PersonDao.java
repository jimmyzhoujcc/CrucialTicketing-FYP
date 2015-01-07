/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import java.util.List;

import com.crucialticketing.entities.Person;

public interface PersonDao {

    public void insertData(Person user);

    public List<Person> getPersonList();

    public void updateData(Person user);

    public void deleteData(String id);

    public Person getPersonById(String id);
}
