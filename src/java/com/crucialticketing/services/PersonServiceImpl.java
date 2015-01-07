/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Owner
 */
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonDao personDao;

    @Override
    public void insertData(Person person) {
        personDao.insertData(person);
    }

    @Override
    public List<Person> getPersonList() {
        return personDao.getPersonList();
    }

    @Override
    public void deleteData(String id) {
        personDao.deleteData(id);

    }

    @Override
    public Person getPersonById(String id) {
        return personDao.getPersonById(id);
    }

    @Override
    public void updateData(Person person) {
        personDao.updateData(person);

    }

}
