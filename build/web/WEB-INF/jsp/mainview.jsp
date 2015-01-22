<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crucial Ticketing</title>

        <script src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
        
        <!-- Bootstrap -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

        

        
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


            <div class="title"><h3>Crucial Ticketing</h3></div>
            <div class="titletabbox">
                <ul class="nav nav-pills" role="tablist">
                    <li role="presentation"><a href="#">Alerts <span class="badge">42</span></a></li>
                    <li role="presentation"><a href="#">Help</a></li>
                    <li role="presentation"><a href="#">Logout</a></li>
                </ul>
            </div>

        </div>

        <br class="clearfix" />
        <% String pageName = (String) request.getAttribute("page");
            if (pageName == null) {
                pageName = "menu/home.jsp";
            }
        %>

        <ul class="nav nav-tabs">
            <li role="presentation" <% if (pageName.equals("menu/main.jsp")) {
                    out.print("class=\"active\"");
                }%>>
                <a href="<%=request.getContextPath()%>/home/main/main/">Home</a>
            </li>
            <li role="presentation" <% if (pageName.equals("menu/create.jsp")) {
                    out.print("class=\"active\"");
                }%>>
                <a href="<%=request.getContextPath()%>/home/main/create/">Create</a>
            </li>
            <li role="presentation" <% if (pageName.equals("menu/update.jsp")) {
                    out.print("class=\"active\"");
                }%>>
                <a href="<%=request.getContextPath()%>/home/main/update/">View</a>
            </li>
            <li role="presentation"><a href="menu/reporting.jsp">Reporting</a></li>
            <li role="presentation"><a href="menu/settings.jsp">Settings</a></li>

        </ul>
        <div class="main">
            <jsp:include page="<%=pageName%>"/>
        </div>
        <div class="footer">
            <ol class="breadcrumb">
                <li>Project by Daniel Foley | Computer Science | University of the West of England</li>
            </ol>
        </div>
    </body>
</html>
