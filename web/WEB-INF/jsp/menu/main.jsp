<%-- 
    Document   : home
    Created on : 06-Jan-2015, 14:15:39
    Author     : Owner
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

Welcome to Crucial Ticketing
<br />
Please use the tabs provided to navigate through the program

<br />

<form method="post" action="../../../home/uploadfile" enctype="multipart/form-data">
		<input type="file" name="fileUploaded" /> <input type="submit" />
	</form>

