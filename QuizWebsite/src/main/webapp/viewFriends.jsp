<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.quizwebsite.userManager.User" %>
<%@ page import="java.util.Set" %>
<html>
<head>
    <title>${user.getUsername()}'s  Friends</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userStyles.css">
</head>
<body>
<div class="container">
    <h1>${user.getUsername()}'s Friends</h1>
    <ul class="list-group">
        <%
            Set<User> friendsList = (Set<User>) request.getAttribute("friends");
            if (friendsList != null) {
                for (User friend : friendsList) {
        %>
        <li class="list-group-item d-flex justify-content-between align-items-center">
            <span><%= friend.getUsername() %></span>
            <div>
                <form action="${pageContext.request.contextPath}/friends" method="post" style="margin: 0;">
                    <input type="hidden" name="method" value="unfriend">
                    <input type="hidden" name="from" value="friends">
                    <input type="hidden" name="friendId" value="<%= friend.getUsername() %>">
                    <button type="submit" class="btn btn-danger btn-sm">Unfriend</button>
                </form>
            </div>
        </li>
        <%
            }
        } else {
        %>
        <li class="list-group-item">No friends to display</li>
        <%
            }
        %>
    </ul>
</div>
</body>
</html>
