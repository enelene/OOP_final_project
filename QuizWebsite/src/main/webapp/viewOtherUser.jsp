<%@ page import="com.example.quizwebsite.userManager.User" %>
<%@ page import="com.example.quizwebsite.userManager.UserManager" %>
<%@ page import="javax.management.relation.Relation" %>
<%@ page import="com.example.quizwebsite.relationManager.RelationManager" %>
<%@ page import="com.example.quizwebsite.relationManager.RelationType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    User user = (User) request.getSession().getAttribute("user");
    String friendUsername = request.getParameter("friendUsername");
    UserManager um = (UserManager) application.getAttribute("userManager");
    User friend = um.getUserByUsername(friendUsername);
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/friendSearchStyles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userStyles.css">
    <style>
        .grid-container .header h1 {
            font-weight: bold !important;
            /*font-size: 24px !important;*/
            font-family: "Roboto Light" !important;
            align-items: center !important;
        }
        .before-footer, .after-header {
            border: none !important;
            height: 1px !important;
            background-color: #000 !important;
        }
        /* New styles for centering */
        .content {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
        }
        .friend-list-title {
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        button {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="grid-container">
    <div class="grid-item header">
        <h1>Quiz Website</h1>
    </div>
    <div class="grid-item nav">
        <a href="HomepageServlet?action=home" title="Home"><img src="icons/home-icon.png" alt="homepage icon"></a>
        <a href="HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="icons/play-icon.png" alt="play quiz icon"></a>
        <a href="HomepageServlet?action=createQuiz" title="Create a quiz"><img src="icons/create-icon.png" alt="create a quiz icon"></a>
        <a href="HomepageServlet?action=profile" title="Your Profile"><img src="icons/profile-icon.png" alt="profile page icon"></a>
        <a href="HomepageServlet?action=logout" title="Logout"><img src="icons/logout.png" alt="logout icon"></a>
    </div>
    <hr class="after-header">
    <div class="grid-item content">
        <p class="friend-list-title"><%= "Dear " + user.getUsername() + "," + statusText %></p>
        <form action="${pageContext.request.contextPath}<%= formAction %>" method="post">
            <input type="hidden" name="method" value="<%=methodName%>">
            <input type="hidden" name="friendUsername" value="<%= friend.getUsername() %>">
            <input type="hidden" name="from" value="users">
            <button type="submit" <%= (r == RelationType.REQUEST_FROM_USER_2) ? "disabled" : "" %>>
                <%= buttonText %>
            </button>
        </form>
    </div>
    <hr class="before-footer">
    <footer>@Elene&Ana&Ana</footer>
</div>
</body>
</html>