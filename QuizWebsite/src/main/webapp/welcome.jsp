<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ page import="com.example.quizwebsite.userManager.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <%
        User user = (User) session.getAttribute("user");
        String username = (user != null) ? user.getUsername() : "Guest";
    %>
    <h1 class="welcome-message">Welcome, <%= username %>!</h1>
</div>
</body>
</html>


