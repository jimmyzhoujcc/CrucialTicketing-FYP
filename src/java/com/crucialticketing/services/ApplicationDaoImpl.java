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
public class ApplicationDaoImpl implements ApplicationDao {
     @Autowired
    DataSource dataSource;
    
    @Override
    public void insertApplication(Application application) {
        
    }

    @Override
    public List<Application> getApplicationList() {
        List<Application> temp = new ArrayList();
        
        return temp;
    }
    
    @Override
    public void updateApplication(Application application) {
        
    }

    @Override
    public void deleteApplication(String id) {
        
    }

    @Override
    public Application getApplicationById(String id) {
        Application temp = new Application();
        
        return temp;
    }
}
