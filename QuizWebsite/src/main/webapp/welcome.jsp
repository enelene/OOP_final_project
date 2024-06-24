<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  language="java"%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h1 class="welcome-message"><%= request.getParameter("username") %></h1>
</div>
</body>
</html>
