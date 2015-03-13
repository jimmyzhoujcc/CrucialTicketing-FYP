/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.util;

import org.springframework.ui.ModelMap;

/**
 *
 * @author DanFoley
 */
public class Validation {
    public static void inputNotProvided(ModelMap map, String field) {
        map.addAttribute("alert", field + " was not provided, please check and try again");
    }
    
    public static void inputIsInvalid(ModelMap map, String field) {
        map.addAttribute("alert", "The " + field + " provided is invalid, please check and try again");
    }
    
    public static void databaseError(ModelMap map) {
        map.addAttribute("alert", "This section of the application is currently inaccessible");
    }
    
    public static void pageError(ModelMap map) {
        map.addAttribute("alert", "This section of the application is currently inaccessible");
    }
    
    public static void userDoesntHaveRole(ModelMap map) {
        map.addAttribute("alert", "You do not have the required role privledges to complete this operation");
    }
    
    public static void fieldAlreadyExists(ModelMap map, String field) {
        map.addAttribute("alert", "A "+ field +" with this name already exists");
    }
    
    public static void illegalWorkflow(ModelMap map) {
        map.addAttribute("alert", "The workflow contained either a invalid base node or a invalid closure node");
    }

}
