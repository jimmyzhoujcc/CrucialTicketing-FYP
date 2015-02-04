<%@include file="../header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<br />
<div style="text-align:Center"><h2>Crucial Ticketing</h2></div>
<br />
        <div style="margin-left:530px;text-align: center">
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
                        <td><form:input path="password" type="password" />
                        </td>
                    </tr>
                   
                    <tr>
                        <td colspan="2"><br /><input type="submit" />
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>

            <br /><br />
            <%@include file="../footer.jsp" %>
