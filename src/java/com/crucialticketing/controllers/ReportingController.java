/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucialticketing.controllers;

import com.crucialticketing.daos.services.TicketService;
import com.crucialticketing.entities.Ticket;
import com.crucialticketing.util.Validation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author DanFoley
 */
@RequestMapping(value = "/reporting/")
@Controller
public class ReportingController {

    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/execute/", method = RequestMethod.POST)
    public String executeQuery(HttpServletRequest request,
            @RequestParam(value = "ticketList", required = false) String[] ticketList,
            @RequestParam(value = "applicationList", required = false) String[] applicationList,
            @RequestParam(value = "severityList", required = false) String[] severityList,
            @RequestParam(value = "workflowList", required = false) String[] workflowList,
            @RequestParam(value = "workflowStatusList", required = false) String[] workflowStatusList,
            @RequestParam(value = "createdByUserList", required = false) String[] createdByUserList,
            @RequestParam(value = "reportedByUserList", required = false) String[] reportedByUserList,
            @RequestParam(value = "lastUpdatedByUserList", required = false) String[] lastUpdatedByUserList,
            @RequestParam(value = "dateCreatedFrom", required = false) String dateCreatedFrom,
            @RequestParam(value = "dateCreatedTo", required = false) String dateCreatedTo,
            @RequestParam(value = "dateLastUpdatedTo", required = false) String dateLastUpdatedTo,
            @RequestParam(value = "dateLastUpdatedFrom", required = false) String dateLastUpdatedFrom,
            ModelMap map) {

        ArrayList<String> ticketArrayList = Validation.convertList(ticketList);
        ArrayList<String> applicationArrayList = Validation.convertList(applicationList);
        ArrayList<String> severityArrayList = Validation.convertList(severityList);
        ArrayList<String> workflowArrayList = Validation.convertList(workflowList);
        ArrayList<String> workflowStatusArrayList = Validation.convertList(workflowStatusList);

        ArrayList<String> reportedByUserArrayList = Validation.convertList(reportedByUserList);
        ArrayList<String> createdByUserArrayList = Validation.convertList(createdByUserList);
        ArrayList<String> lastUpdatedByUserArrayList = Validation.convertList(lastUpdatedByUserList);

        List<Ticket> returnTicketList = ticketService.getListByCriteria(ticketArrayList,
                applicationArrayList,
                severityArrayList,
                workflowArrayList,
                workflowStatusArrayList,
                reportedByUserArrayList,
                createdByUserArrayList,
                lastUpdatedByUserArrayList,
                dateCreatedFrom, dateCreatedTo,
                dateLastUpdatedFrom, dateLastUpdatedTo);

        map.addAttribute("ticketList", returnTicketList);
        map.addAttribute("page", "main/reporting/reportingresult.jsp");
        return "mainview";
    }

    @RequestMapping(value = "/crucialreport/", method = RequestMethod.POST)
    public ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response, @RequestParam(value = "ticketList", required = false) String[] ticketList) throws Exception {

        ArrayList<String> ticketArrayList = new ArrayList<>();

        ticketArrayList.addAll(Arrays.asList(ticketList));

        List<Ticket> returnTicketList = ticketService.getListByCriteria(ticketArrayList,
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                "", "",
                "", "");

        return new ModelAndView("ExcelRevenueSummary","ticketList", returnTicketList);

    }

}
