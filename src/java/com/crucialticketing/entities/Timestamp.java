/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.entities;

/**
 *
 * @author DanFoley
 */
public class Timestamp {
    public static int getTimestamp() {
        return (int)(System.currentTimeMillis() / 1000);
    }
}
