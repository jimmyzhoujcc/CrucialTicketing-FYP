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



        <script type="text/javascript" >
            $(document).ready(function ()
            {
                $("#notificationLink").click(function ()
                {
                    clearInterval(getNotificationInterval);

                    $.post("<%=request.getContextPath()%>/home/alert/clear/", {marker: marker}, function () {
                    });

                    $("#alertcount").text("0");
                    $("#notificationContainer").fadeToggle(300);

                    getNotificationInterval = setInterval(crunchifyAjax, 3000);
                    return false;
                });

                //Document Click
                $(document).click(function ()
                {
                    $("#notificationContainer").hide();
                });
                //Popup Click
                $("#notificationContainer").click(function ()
                {
                    return false
                });

            });
        </script>

        <style>

            #nav{list-style:none;margin: 0px;
                 padding: 0px;}
            #nav li {
                float: left;
                margin-right: 20px;
                font-size: 14px;
                font-weight:bold;
            }
            #nav li a{color:#333333;text-decoration:none}
            #nav li a:hover{color:#006699;text-decoration:none}
            #notification_li{position:relative}
            #notificationContainer {
                background-color: #fff;
                border: 1px solid rgba(100, 100, 100, .4);
                -webkit-box-shadow: 0 3px 8px rgba(0, 0, 0, .25);
                
                overflow-x: hidden;
                overflow-y: scroll;
                max-height: 300px;
                position: absolute;
                width: 200px;
                z-index: 2;
                display: none;
            }
            #notificationContainer:before {
                content: '';
                display: block;
                position: absolute;
                width: 0;
                height: 0;
                color: transparent;
                border: 10px solid black;
                border-color: transparent transparent white;
                margin-top: -20px;
                margin-left: 188px;
            }
            #notificationTitle {
                z-index: 1000;
                font-weight: bold;
                padding: 8px;
                font-size: 13px;
                background-color: #ffffff;
                width: 184px;
                border-bottom: 1px solid #dddddd;
            }
            #notificationsBody {
                padding: 10px 0px 10px 0px !important;
                min-height:150px;
            }
            #notificationFooter {
                background-color: #e9eaed;
                text-align: center;
                font-weight: bold;
                padding: 8px;
                font-size: 12px;
                border-top: 1px solid #dddddd;
            }

            .notification {
                font-size:15px;
                border-style: dotted;
                border-width: 1px;
                border-color:#000;
                margin-top:2px;

            }
            .notification_stamp {
                font-size:10px;
                font-weight:bold;
            }
        </style>

    </head>
    <body>