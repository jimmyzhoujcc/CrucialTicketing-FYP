/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Person;
import java.util.List;

/**
 *
 * @author Owner
 */
public interface PersonService {

    public void insertData(Person person);

    public List<Person> getPersonList();

    public void deleteData(String id);

    public Person getPersonById(String id);

    public void updateData(Person person);
}
