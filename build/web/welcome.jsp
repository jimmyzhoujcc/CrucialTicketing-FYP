<%-- 
    Document   : welcome
    Created on : 09-Jan-2015, 00:17:25
    Author     : Daniel Foley
--%>
<%
    String redirectURL = request.getContextPath() + "/home/login/login/";
    response.sendRedirect(redirectURL);
%>
