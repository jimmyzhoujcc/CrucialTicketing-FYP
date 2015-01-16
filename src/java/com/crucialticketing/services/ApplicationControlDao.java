/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.ApplicationControl;
import java.util.List;

/**
 *
 * @author Daniel Foley
 */
public interface ApplicationControlDao {

    public void insertApplicationControl(ApplicationControl applicationControl);

    public List<ApplicationControl> getApplicationControl();

    public void updateApplicationControl(ApplicationControl applicationControl);

    public void deleteApplicationControl(String id);

    public ApplicationControl getApplicationControlById(String id);
}
