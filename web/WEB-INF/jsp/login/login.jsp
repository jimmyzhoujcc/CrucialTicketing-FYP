<%-- 
    Document   : login
    Created on : 08-Jan-2015, 22:31:31
    Author     : Daniel Foley
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <body>
        ${alert}
        <br /><br />
        
        <form:form method="POST" action="../attemptlogin/" commandName="login">
		<table>
			<tr>
				<td>Username :</td>
				<td><form:input path="username" />
				</td>
			</tr>
                        
                        <tr>
				<td>Password :</td>
				<td><form:input path="password" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" />
				</td>
			</tr>
		</table>
	</form:form>

    </body>
</html>
<% request.getSession().setAttribute("alert", ""); %>