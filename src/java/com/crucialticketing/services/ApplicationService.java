/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Application;
import java.util.List;

/**
 *
 * @author Daniel Foley
 */
public interface ApplicationService {
    public void insertApplication(Application application);

    public List<Application> getApplicationList();

    public void updateApplication(Application application);

    public void deleteApplication(String id);

    public Application getApplicationById(String id);
}
