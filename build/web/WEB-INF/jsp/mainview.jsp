<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crucial Ticketing</title>

        <link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="heading">
            <div class="title">Crucial Ticketing</div>
            <div class="title logging">Help | <a href="<%= request.getContextPath()%>/home/login/logout/">Logout</a></div>
        </div>

        <% String pageName = (String) request.getAttribute("page");
            if (pageName == null) {
                pageName = "menu/home.jsp";
            }
        %>

        <div class="menu3">
            <a href="<%=request.getContextPath()%>/home/main/" 
               <% if (pageName == "menu/main.jsp") {
                       out.println("class=\"current\"");
                   }%>
               >Home</a>

            <a href="<%=request.getContextPath()%>/home/create/" 
               <% if (pageName == "menu/create.jsp") {
                       out.println("class=\"current\"");
                   }%>
               >Create</a>

            <a href="<%=request.getContextPath()%>/home/update/" 
               <% if (pageName == "menu/update.jsp") {
                       out.println("class=\"current\"");
                   }%>
               >Edit/Update</a>

            <a href="<%=request.getContextPath()%>/home/report/" 
               <% if (pageName == "menu/report.jsp") {
                       out.println("class=\"current\"");
                   }%>
               >Reporting</a>

            <a href="<%=request.getContextPath()%>/home/settings/" 
               <% if (pageName == "menu/settings.jsp") {
                       out.println("class=\"current\"");
                   }%>
               >Settings</a>
        </div>
        <div class="menu3sub">
            <jsp:include page="<%=pageName%>" flush="true" /> 
        </div>

        <div class="heading footer">
            Created by Daniel Foley | Final Year Project for Computer Science at the University of the West of England
        </div>
    </body>
</html>
