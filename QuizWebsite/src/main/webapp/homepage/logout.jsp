<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    session.invalidate();
    //todo
    response.sendRedirect("/QuizWebsite/");
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
