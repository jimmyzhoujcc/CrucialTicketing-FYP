/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

/**
 *
 * @author Daniel Foley
 */
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.crucialticketing.entities.Person;
import java.util.Map;

public class PersonDaoImpl implements PersonDao {

    @Autowired
    DataSource dataSource;

    public void insertData(Person person) {

        String sql = "INSERT INTO user "
                + "(name,age) VALUES (?, ?)";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
                sql,
                new Object[]{person.getName(), person.getAge()});
    }

    public List<Person> getPersonList() {
        List userList = new ArrayList();

        String sql = "SELECT * FROM person";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> row = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> dbItem : row) {
            userList.add(new Person(
                    Integer.valueOf((String)dbItem.get("person_id")), 
                    (String)dbItem.get("name"), 
                    Integer.valueOf((String)dbItem.get("age"))));
        }
        
        return userList;
    }

    @Override
    public void deleteData(String id) {
        String sql = "DELETE FROM user WHERE person_id=" + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql);

    }

    @Override
    public void updateData(Person person) {

        String sql = "UPDATE user SET name = ?,age = ? WHERE person_id = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
                sql,
                new Object[]{person.getName(), person.getAge()});

    }

    @Override
    public Person getPersonById(String id) {
        
        String sql = "SELECT * FROM person WHERE person_id= " + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> personList = jdbcTemplate.queryForList(sql);
        return new Person();
    }

}
