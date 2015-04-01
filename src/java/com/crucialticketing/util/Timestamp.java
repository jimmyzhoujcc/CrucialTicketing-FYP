/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author DanFoley
 */
public class Timestamp {

    public static int getTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static String convTimestamp(int timestamp) {
        try {
            Date date = new Date((long)timestamp * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateString = sdf.format(date);
            return dateString;
        } catch (Exception e) {
            return "00-00-0000";
        }
    }

    public static int convTimestamp(String timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return (int) (((Date) sdf.parse(timestamp)).getTime() / 1000);
        } catch (Exception e) {
            return 0;
        }
    }
}
