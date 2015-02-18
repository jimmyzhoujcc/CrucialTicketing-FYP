/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.schedule;

import com.crucialticketing.entities.UserRequest;
import com.crucialticketing.services.UserAlertService;
import com.crucialticketing.services.UserRequestService;
import com.crucialticketing.services.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author DanFoley
 */
@Component
public class UserCreationJob {

    @Autowired
    DataSource dataSource;

    public void executeUserCreationJob() {
        JdbcTemplate con = new JdbcTemplate(dataSource);
        ArrayList<String> processedList = new ArrayList<>();
        boolean found = false;

        UserRequestService userRequestService = new UserRequestService();
        userRequestService.setCon(con);

        UserService userService = new UserService();
        userService.setCon(con);

        UserAlertService userAlertService = new UserAlertService();
        userAlertService.setCon(con);

        List<UserRequest> userRequestList = userRequestService.getUserRequestList();

        for (UserRequest userRequest : userRequestList) {
            for (int i = 0; i < processedList.size(); i++) {
                if (userRequest.getUser().getUsername().equals(processedList.get(i))) {
                    userRequestService.removeRequest(userRequest.getUserRequestId());
                    i = processedList.size();
                    found = true;
                }
            }

            if (!found) {

                if (userService.getUserByUsername(userRequest.getUser().getUsername(), false).getUserId() == 0) {
                    String password = userService.insertUser(userRequest.getUser());
                    processedList.add(userRequest.getUser().getUsername());
                    userRequestService.removeRequest(userRequest.getUserRequestId());
                    userAlertService.insertUserAlert(userRequest.getRequestor().getUserId(),
                            "A user account has been setup with the following information: Username ("
                            + userRequest.getUser().getUsername() + ") Password (" + password + ")");
                } else {
                    processedList.add(userRequest.getUser().getUsername());
                    userRequestService.removeRequest(userRequest.getUserRequestId());
                    userAlertService.insertUserAlert(userRequest.getRequestor().getUserId(),
                            "A user account was setup with the same username before your request could be complete");
                }
            }
        }

    }

}
