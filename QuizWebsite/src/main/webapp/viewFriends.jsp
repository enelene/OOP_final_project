<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.quizwebsite.userManager.User" %>
<%@ page import="java.util.Set" %>
<!DOCTYPE html>
<html>
<head>
    <title>${user.getUsername()}'s Friends</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userStyles.css">
    <style>
        .grid-container .header h1 {
            font-weight: bold !important;
            font-family: "Roboto Light" !important;
            align-items: center !important;
        }

        .before-footer, .after-header {
            border: none !important;
            height: 1px !important;
            background-color: #000 !important;
        }

        .friends-container {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .list-group {
            width: 100%;
            max-width: 600px; /* Optional: set a max-width for the list */
            margin: 0 auto;
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
        <h2 class="friend-list-title">${user.getUsername()}'s Friends</h2>
        <div class="friends-container">
            <%
                Set<User> friendsList = (Set<User>) request.getAttribute("friends");
                if (friendsList != null && !friendsList.isEmpty()) {
            %>
            <ul class="list-group w-100">
                <% for (User friend : friendsList) { %>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <form action="${pageContext.request.contextPath}/users" method="get" style="display:inline;">
                        <input type="hidden" name="friendUsername" value="<%= friend.getUsername() %>">
                        <button type="submit"
                                style="background:none;border:none;color:#0000EE;cursor:pointer;padding:0;">
                            <%= friend.getUsername() %>
                        </button>
                    </form>
                    <div>
                        <form action="${pageContext.request.contextPath}/friends" method="post" style="margin: 0;">
                            <input type="hidden" name="method" value="unfriend">
                            <input type="hidden" name="from" value="friends">
                            <input type="hidden" name="friendUsername" value="<%= friend.getUsername() %>">
                            <button type="submit" class="btn btn-danger btn-sm">Unfriend</button>
                        </form>
                    </div>

                </li>
                <%
                    }
                } else {
                %>
                <div class="no-friends-message">No friends to display</div>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
    <hr class="before-footer">
    <footer>@Elene&Ana&Ana</footer>
</div>
</body>
</html>
