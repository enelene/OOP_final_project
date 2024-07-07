<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <h1>Welcome to Our Website</h1>

    <% if (request.getAttribute("message") != null) { %>
    <p class="success-message"><%= request.getAttribute("message") %></p>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
    <p class="error-message"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="LoginServlet" method="post">
        <input type="text" name="username" placeholder="Username"><br>
        <input type="password" name="password" placeholder="Password"><br>
        <input type="checkbox" name="rememberMe"> Remember Me
        <input type="submit" value="Login">
    </form>
    <a href="createAccount.jsp">Create a new account</a>
</div>
</body>
</html>