<%-- 
    Document   : queryticket
    Created on : 07-Jan-2015, 01:02:05
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<c:if test="${fn:length(alert)>0}">
    <div class="alert alert-danger" role="alert">${alert}</div>
</c:if>
<br />

<form method="POST" action="<%=request.getContextPath()%>/home/update/viewticket/">
    Select by
    <br />
    Ticket ID: <input type="text" name="ticketid" />
    <input type="submit" />
</form>
<br />
Created from: <input type="text" name="ticketid" /> to <input type="text" name="ticketid" />

<br />
Last updated from: <input type="text" name="ticketid" /> to <input type="text" name="ticketid" />

<table><tr><td>
            <select multiple size="10" name="FromLB" style="width:150">
                <option value="one">one</option>
                <option value="two">two</option>
                <option value="three">three</option>
                <option value="four">four</option>
                <option value="five">five</option>
                <option value="six">six</option>
                <option value="seven">seven</option>
                <option value="eight">eight</option>
                <option value="nine">nine</option>
                <option value="ten">ten</option>
            </select>
        </td>
        <td align="center" valign="middle">
            <input type="button" onClick="move(this.form.FromLB, this.form.ToLB)" 
                   value="->"><br />
            <input type="button" onClick="move(this.form.ToLB, this.form.FromLB)" 
                   value="<-">
        </td>
        <td>
            <select multiple size="10" name="ToLB" style="width:150">
            </select>
        </td></tr></table>

System
Team

Status

<br />
