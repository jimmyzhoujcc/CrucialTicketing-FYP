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

        <script language="javascript">
            function move(tbFrom, tbTo)
            {
                var arrFrom = new Array();
                var arrTo = new Array();
                var arrLU = new Array();
                var i;
                for (i = 0; i < tbTo.options.length; i++)
                {
                    arrLU[tbTo.options[i].text] = tbTo.options[i].value;
                    arrTo[i] = tbTo.options[i].text;
                }
                var fLength = 0;
                var tLength = arrTo.length;
                for (i = 0; i < tbFrom.options.length; i++)
                {
                    arrLU[tbFrom.options[i].text] = tbFrom.options[i].value;
                    if (tbFrom.options[i].selected && tbFrom.options[i].value != "")
                    {
                        arrTo[tLength] = tbFrom.options[i].text;
                        tLength++;
                    }
                    else
                    {
                        arrFrom[fLength] = tbFrom.options[i].text;
                        fLength++;
                    }
                }

                tbFrom.length = 0;
                tbTo.length = 0;
                var ii;

                for (ii = 0; ii < arrFrom.length; ii++)
                {
                    var no = new Option();
                    no.value = arrLU[arrFrom[ii]];
                    no.text = arrFrom[ii];
                    tbFrom[ii] = no;
                }

                for (ii = 0; ii < arrTo.length; ii++)
                {
                    var no = new Option();
                    no.value = arrLU[arrTo[ii]];
                    no.text = arrTo[ii];
                    tbTo[ii] = no;
                }
            }
        </script>
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
