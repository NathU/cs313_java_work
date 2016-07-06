<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Forum - Invalid Login</title>
    </head>
    <body>
        <h2>Invalid Login</h2>
        <hr>
        <c:if test="${!empty error}">
           <p style="color:red">Error: ${error}</p>
        </c:if>
        <p> Please correct your username or password and <a href="./index.html">try again</a>. </p>
        
    </body>
</html>
