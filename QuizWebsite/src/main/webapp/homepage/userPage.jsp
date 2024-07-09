<!--
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
-->
<%@ page import="com.example.quizwebsite.relationManager.RelationManager" %>
<%@ page import="com.example.quizwebsite.userManager.User" %>
<%
    User user = (User) session.getAttribute("user");
    RelationManager relationManager = (RelationManager) application.getAttribute("relationManager");

    int friendsNum = 0;
    int requestsNum = 0;

    if (relationManager != null && user != null) {
        friendsNum = relationManager.getFriendsNum(user.getId());
        requestsNum = relationManager.getRequestsNum(user.getId());
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>user Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userStyles.css">
</head>
<body>
<div class="grid-container">
    <div class="grid-item header">
        <h1>Quiz Website</h1>
    </div>
    <div class="grid-item nav">
        <a href="HomepageServlet?action=home" title="Home"><img src="${pageContext.request.contextPath}/icons/home-icon.png" alt="homepage icon"></a>
        <a href="HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="${pageContext.request.contextPath}/icons/play-icon.png" alt="play quiz icon"></a>
        <a href="HomepageServlet?action=createQuiz" title="Create a quiz"><img src="${pageContext.request.contextPath}/icons/create-icon.png" alt="create a quiz icon"></a>
        <a href="HomepageServlet?action=profile" title="Your Profile"><img src="${pageContext.request.contextPath}/icons/profile-icon.png" alt="profile page icon"></a>
        <a href="HomepageServlet?action=logout" title="Logout"><img src="${pageContext.request.contextPath}/icons/logout.png" alt="logout icon"></a>
    </div>
    <hr class="after-header">
    <div class="grid-item content">
        <h2> ${user.getUsername()}'s Profile</h2>
    </div>
    <div class="grid-item search-friend">
        <h3>Search for a Friend</h3>
        <form action="${pageContext.request.contextPath}/users" method="get">
            <label for="searchUsername">Username:</label>
            <input type="text" id="searchUsername" name="friendUsername" required>
            <button type="submit">Search</button>
        </form>
    </div> <%-- search bar    --%>
    <div class="grid-item achievement-board">
        <h3>Achievement Board</h3>
        <h4>Total score:</h4> <!--overall score number-->
        <h4>Quizzes taken:</h4> <!--number of quizzes taken-->
        <h4>Quizzes created:</h4> <!--number of quizzes created-->
    </div>
    <div class="grid-item friends-board">
        <button class="icon-button" title="Friends"
                onclick="location.href='${pageContext.request.contextPath}/friends'">
            <img src="icons/friends-icon.png" alt="friends icon"> <%= friendsNum %>
        </button>
        <button class="icon-button" title="Requests"
                onclick="location.href='${pageContext.request.contextPath}/requests'">
            <img src="icons/friend-request-icon.png" alt="friend request icon"> <%= requestsNum %>
        </button>
    </div>
    <hr class="before-footer">
    <footer>@Elene&Ana&Ana</footer>
</div>
</body>
</html>
