<%@ page import="com.example.quizwebsite.userManager.User" %>
<%@ page import="com.example.quizwebsite.userManager.UserManager" %>
<%@ page import="javax.management.relation.Relation" %>
<%@ page import="com.example.quizwebsite.relationManager.RelationManager" %>
<%@ page import="com.example.quizwebsite.relationManager.RelationType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    User user = (User) request.getSession().getAttribute("user");
    Integer friendId = Integer.parseInt(request.getParameter("friendId"));
    UserManager um = (UserManager) application.getAttribute("userManager");
    User friend = um.getUserById(friendId);
    RelationManager rm = (RelationManager) application.getAttribute("relationManager");
    RelationType r = rm.getRelation(friend.getId(), user.getId());

    String buttonText = null;
    String statusText = null;
    String methodName = null;
    String formAction = "";

    if(r == RelationType.FRIENDS) {
        statusText = "You are already friends with " + friend.getUsername();
        buttonText = "Unfriend";
        formAction = "/friends";
        methodName = "unfriend";
    } else if(r == RelationType.NOT_FRIENDS) {
        statusText = "You and " + friend.getUsername() + " are not friends";
        buttonText = "Send Friend Request";
        formAction += "/friends";
        methodName = "sendRequest";
    } else if(r == RelationType.REQUEST_FROM_USER_1) {
        statusText = friend.getUsername() + " has sent you a friend request";
        buttonText = "Accept Friend Request";
        formAction += "/requests";
        methodName = "accept";
    } else if(r == RelationType.REQUEST_FROM_USER_2) {
        statusText = "You have already sent a friend request to " + friend.getUsername();
        buttonText = "Request Sent";
        formAction = "deleteRequest";
    }
%>
<head>
    <title><%=user.getUsername()%></title>
</head>
<body>
<h1><%=user.getUsername()%></h1>
<p><%= statusText %></p>
<form action="${pageContext.request.contextPath}<%= formAction %>" method="post">
    <input type="hidden" name="method" value="<%=methodName%>">
    <input type="hidden" name="friendId" value="<%= friend.getUsername() %>">
    <input type="hidden" name="from" value="users">
    <button type="submit" <%= (r == RelationType.REQUEST_FROM_USER_2) ? "disabled" : "" %>>
        <%= buttonText %>
    </button>
</form>
</body>
</html>
