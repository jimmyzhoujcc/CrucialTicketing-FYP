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
<form name="input_form">
<input id="form_file1" name="files[0]" type="file" onchange="updateUpload('form_file1');" />
<br />
<input id="form_file2" name="files[0]" type="file" onchange="updateUpload('form_file1');" />
</form>
<br /><br />
<hr />


<form:form id="upload_file" name="submit_form" method="post" action="../../../home/uploadfile"
           modelAttribute="uploadedfile" enctype="multipart/form-data">

        <table id="fileTable">
            <tr>
                <td><input id="submit_file1" name="files[0]" type="file" /></td>
            </tr>
            <tr>
                <td><input id="submit_file2" name="files[1]" type="file" /></td>
            </tr>
        </table>

    <br />
    <input type="submit" value="Upload" />
    <input id="addFile" type="button" value="Add File" />
</form:form>
