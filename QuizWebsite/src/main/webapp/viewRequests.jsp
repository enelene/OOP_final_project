<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.quizwebsite.userManager.User" %>
<%@ page import="java.util.Set" %>
<html>
<head>
    <title>Your Friend Request</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="quizCreationStyles.css">
</head>
<body>
<div class="container">
    <h1>Friend Request</h1>
    <ul class="list-group">
        <%
            Set<User> requests = (Set<User>) request.getAttribute("requests");
            if (requests != null) {
                for (User sender : requests) {
        %>
        <li class="list-group-item d-flex justify-content-between align-items-center">
            <span><%= sender.getUsername() %></span>
            <div>
                <form action="${pageContext.request.contextPath}/requests" method="post" style="display: inline;">
                    <input type="hidden" name="method" value="accept">
                    <input type="hidden" name="from" value="requests">
                    <input type="hidden" name="friendId" value="<%= sender.getUsername() %>">
                    <button type="submit" class="btn btn-success btn-sm">Accept</button>
                </form>
                <form action="${pageContext.request.contextPath}/requests" method="post" style="display: inline;">
                    <input type="hidden" name="method" value="decline">
                    <input type="hidden" name="from" value="requests">
                    <input type="hidden" name="friendId" value="<%= sender.getUsername() %>">
                    <button type="submit" class="btn btn-danger btn-sm">Decline</button>
                </form>
            </div>
        </li>
        <%
            }
        } else {
        %>
        <li class="list-group-item">No Requests to display</li>
        <%
            }
        %>
    </ul>
</div>
</body>
</html>
