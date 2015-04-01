<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crucial Ticketing</title>

        <script type="text/javascript">
            var homeURI = "<%=request.getContextPath()%>";
        </script>

        <link rel="icon" 
              type="image/png" 
              href="<%=request.getContextPath()%>/img/ticket.png">

        <!-- jQuery -->
        <script src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">

        <!-- Bootstrap CSS -->
        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

        <!-- Self: main styling -->
        <link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css"/>

        <!-- Self: notification JS -->
        <link href="<%=request.getContextPath()%>/css/notification.css" rel="stylesheet" type="text/css"/>

        <!-- Date picker code -->
        <!-- http://jqueryui.com/datepicker/ -->
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

       
    </head>
    <body>