/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.util;

import java.util.ArrayList;

/**
 *
 * @author DanFoley
 */
public class QueryGenerator {

    public static String amendQuery(String sql, ArrayList<String> inputList, String field, boolean toAddAnd) {
   
        for (int i = 0; i < inputList.size(); i++) {
            if((toAddAnd) && (i == 0)) {
                sql += "AND ";
            }
            sql += field + "=" + inputList.get(i) + " ";
            if ((i + 1) != inputList.size()) {
                sql += "OR ";
            }
        }
        return sql;
    }
}
