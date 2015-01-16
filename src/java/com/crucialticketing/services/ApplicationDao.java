/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import java.util.List;
import com.crucialticketing.entities.Application;
/**
 *
 * @author Daniel Foley
 */
public interface ApplicationDao {
    public void insertApplication(Application application);

    public List<Application> getApplicationList();

    public void updateApplication(Application application);

    public void deleteApplication(String id);

    public Application getApplicationById(String id);
}
