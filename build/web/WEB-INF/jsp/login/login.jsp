<%@include file="../header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<br />

<div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <h2 class="main-title">Crucial Ticketing</h2>
        
        <c:if test="${fn:length(alert)>0}">
            <div class="alert alert-danger" role="alert">${alert}</div>
            <% request.removeAttribute("alert");%>
        </c:if>



        <form method="POST"  class="form-horizontal" action="<%=request.getContextPath()%>/home/login/attemptlogin/" >

            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">Username</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="username" name="username" placeholder="Username" value="dan" required />
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="password" value="tGc3Qaic" required />
                </div>
            </div>
         
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Sign in</button>
                </div>
            </div>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<br /><br />
<%@include file="../footer.jsp" %>
