/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;
import com.crucialticketing.entities.Application;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Daniel Foley
 */
public class ApplicationServiceImpl implements ApplicationService {
    
    @Autowired
    ApplicationDao applicationDao;

    @Override
    public void insertApplication(Application application) {
        applicationDao.insertApplication(application);
    }

    @Override
    public List<Application> getApplicationList() {
        return applicationDao.getApplicationList();
    }

    @Override
    public void deleteApplication(String id) {
        applicationDao.deleteApplication(id);

    }

    @Override
    public Application getApplicationById(String id) {
        return applicationDao.getApplicationById(id);
    }

    @Override
    public void updateApplication(Application application) {
        applicationDao.updateApplication(application);
    }
}
