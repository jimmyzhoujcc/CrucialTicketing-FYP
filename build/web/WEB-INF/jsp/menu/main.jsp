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
 <form:form id="upload_file" method="post" action="../../../home/uploadfile"
            modelAttribute="uploadedfile" enctype="multipart/form-data">
 
            <p>Select files to upload. Press Add button to add more file
                inputs.</p>
 
            <table id="fileTable">
                <tr>
                    <td><input name="files[0]" type="file" /></td>
                </tr>
                <tr>
                    <td><input name="files[1]" type="file" /></td>
                </tr>
            </table>
            <br />
            <input type="submit" value="Upload" />
            <input id="addFile" type="button" value="Add File" />
        </form:form>
