/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.util;

import java.util.ArrayList;
import org.springframework.ui.ModelMap;

/**
 *
 * @author DanFoley
 */
public class Validation {

    public static ArrayList<String> convertList(String[] list) {
        ArrayList<String> stringArray = new ArrayList<>();
        if (list != null) {
            for (String string : list) {
                stringArray.add(string);
            }
        }
        return stringArray;
    }

    public static int toInteger(String input) {
        try {
            return Integer.valueOf(input);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isStringSet(String input) {
        try {
            return input.length() != 0;
        } catch (Exception e) {
            return false;
        }
    }

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
        map.addAttribute("alert", "A " + field + " with this name already exists");
    }

    public static void illegalWorkflow(ModelMap map) {
        map.addAttribute("alert", "The workflow contained either a invalid base node or a invalid closure node");
    }

    public static void requestAlreadyOustanding(ModelMap map, String field) {
        map.addAttribute("alert", "A request is already outstanding and is being processed by the system for this " + field);
    }

    public static void requestCreated(ModelMap map, String field) {
        map.addAttribute("success", "A request has been created for exclusive access to this " + field);
    }

    public static void changeProtected(ModelMap map, String field) {
        map.addAttribute("alert", "The " + field + " you are trying to modify is protected and therefore cannot be modified");
    }

}
