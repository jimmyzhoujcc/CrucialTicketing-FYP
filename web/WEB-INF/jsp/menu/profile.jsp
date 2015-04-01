<%-- 
    Document   : profile
    Created on : 30-Mar-2015, 02:47:49
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form method="POST" action="${pageContext.servletContext.contextPath}/home/main/profile/save/" modelAttribute="uploadedfilelog" enctype="multipart/form-data">  
    Welcome: ${user.username}

    <br class="clearfix" />
    <br class="clearfix" />
    
    Current Profile Picture:

    <img class="media-picture media-object" src="${profileImageLocation}" />

    <br class="clearfix" />
    <br class="clearfix" />
    
    <input name="files[0].file" type="file" accept=".jpg,.jpeg,.png" />
    
    <br class="clearfix" />
    <br class="clearfix" />
    
    <input type="submit" />

</form:form>