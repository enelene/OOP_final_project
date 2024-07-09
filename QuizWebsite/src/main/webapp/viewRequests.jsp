<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.quizwebsite.userManager.User" %>
<%@ page import="java.util.Set" %>

<%
    User user = (User) session.getAttribute("user");
%>
<html>
<head>
    <title>Your Friend Request</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
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
    </style>
</head>
<body>
<div class="grid-container">
    <div class="grid-item header">
        <h1>Quiz Website</h1>
    </div>
    <div class="grid-item nav">
        <a href="HomepageServlet?action=home" title="Home"><img src="icons/home-icon.png" alt="homepage icon"></a>
        <a href="HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="icons/play-icon.png"
                                                                           alt="play quiz icon"></a>
        <a href="HomepageServlet?action=createQuiz" title="Create a quiz"><img src="icons/create-icon.png"
                                                                               alt="create a quiz icon"></a>
        <a href="HomepageServlet?action=profile" title="Your Profile"><img src="icons/profile-icon.png"
                                                                           alt="profile page icon"></a>
        <a href="HomepageServlet?action=logout" title="Logout"><img src="icons/logout.png" alt="logout icon"></a>
    </div>

    <hr class="after-header">
    <div class="grid-item content">
        <h2 class="friend-requests-title">${user.getUsername()}'s Friend Requests</h2>
        <div class="requests-container">
            <%
                Set<User> requests = (Set<User>) request.getAttribute("requests");
                if (requests != null && !requests.isEmpty()) {
            %>
            <ul class="list-group w-100">
                <% for (User sender : requests) { %>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <form action="${pageContext.request.contextPath}/users" method="get" style="display:inline;">
                        <input type="hidden" name="friendUsername" value="<%= sender.getUsername() %>">
                        <button type="submit"
                                style="background:none;border:none;color:#0000EE;cursor:pointer;padding:0;">
                            <%= sender.getUsername() %>
                        </button>
                    </form>
                    <div>
                        <form action="${pageContext.request.contextPath}/requests" method="post"
                              style="display: inline;">
                            <input type="hidden" name="method" value="accept">
                            <input type="hidden" name="from" value="requests">
                            <input type="hidden" name="friendUsername" value="<%= sender.getUsername() %>">
                            <button type="submit" class="btn btn-success btn-sm">Accept</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/requests" method="post"
                              style="display: inline;">
                            <input type="hidden" name="method" value="decline">
                            <input type="hidden" name="from" value="requests">
                            <input type="hidden" name="friendUsername" value="<%= sender.getUsername() %>">
                            <button type="submit" class="btn btn-danger btn-sm">Decline</button>
                        </form>
                    </div>
                </li>
                <% } %>
            </ul>
            <% } else { %>
            <div class="no-requests-message">No Requests to display</div>
            <% } %>
        </div>
    </div>
    <hr class="before-footer">
    <footer>@Elene&Ana&Ana</footer>
</div>
</body>
</html>