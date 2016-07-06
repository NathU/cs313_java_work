<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>new post</title>
    </head>
    <body>
        <h2>Create a New Post:</h2>
        <form action="newpost" method="POST" >
            <textarea name="text" value=""></textarea>
            <input type="submit" value="OK" /> 
        </form>
        <br/>
        <h2> Or,View the Thread
        <form action="loadposts" method="POST" >
            <input type="submit" value="OK" /> 
        </form> </h2>
    </body>
</html>
