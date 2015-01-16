/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.ApplicationControl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Daniel Foley
 */
public class ApplicationControlServiceImpl implements ApplicationControlService {
    @Autowired
    ApplicationControlDao applicationControlDao;
    
    @Override
    public void insertApplicationControl(ApplicationControl applicationControl) {
        applicationControlDao.insertApplicationControl(applicationControl);
    }

    @Override
    public List<ApplicationControl> getApplicationControl() {
        return applicationControlDao.getApplicationControl();
    }

    @Override
    public void updateApplicationControl(ApplicationControl applicationControl) {
        applicationControlDao.updateApplicationControl(applicationControl);
    }

    @Override
    public void deleteApplicationControl(String id) {
        applicationControlDao.deleteApplicationControl(id);
    }

    @Override
    public ApplicationControl getApplicationControlById(String id) {
        return applicationControlDao.getApplicationControlById(id);
    }
}
