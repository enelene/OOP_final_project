<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    session.invalidate();
    response.sendRedirect("home.jsp");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<h1>You have been logged out.</h1>
</body>
</html>
