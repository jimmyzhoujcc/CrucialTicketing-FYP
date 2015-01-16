/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.services;

import com.crucialticketing.entities.Severity;
import java.util.List;

/**
 *
 * @author Daniel Foley
 */
public interface SeverityService {
    public void insertSeverity(Severity severity);

    public List<Severity> getSeverityList();

    public void updateSeverity(Severity severity);

    public void deleteSeverity(String id);

    public Severity getSeverityById(String id);
}
