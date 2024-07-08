<%--
  Created by IntelliJ IDEA.
  User: Enele
  Date: 7/7/2024
  Time: 1:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.quizwebsite.quizManager.Quiz" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>List Quizzes</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userStyles.css">
    <style>
        .grid-container {
            grid-template-areas:
                'header'
                'content'
                'footer';
        }
        .header {
            max-height: 80px;
            display: flex;
            justify-content: space-between;
            padding-left: 12px;
            padding-right: 12px;
            grid-area: header;
        }
        .content {
            grid-area: content;
            padding: 20px;
        }
        .quiz-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }
        .quiz-item {
            background-color: #fff;
            border-radius: 8px;
            padding: 15px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="grid-container">
    <div class="grid-item header">
        <h1>Quiz Website</h1>
        <div class="nav">
            <a href="HomepageServlet?action=home" title="Home"><img src="icons/home-icon.png" alt="homepage icon"></a>
            <a href="HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="icons/play-icon.png" alt="play quiz icon"></a>
            <a href="HomepageServlet?action=createQuiz" title="Create a quiz"><img src="icons/create-icon.png" alt="create a quiz icon"></a>
            <a href="HomepageServlet?action=profile" title="Your Profile"><img src="icons/profile-icon.png" alt="profile page icon"></a>
            <a href="HomepageServlet?action=logout" title="Logout"><img src="icons/logout.png" alt="logout icon"></a>
        </div>
    </div>
    <div class="grid-item content">
        <h2>Available Quizzes</h2>
        <div class="quiz-list">
            <%
                List<Quiz> quizzes = (List<Quiz>) request.getAttribute("quizzes");
                if (quizzes != null && !quizzes.isEmpty()) {
                    for (Quiz quiz : quizzes) {
            %>
            <div class="quiz-item">
                <h3><%= quiz.getName() %></h3>
                <p><%= quiz.getDescription() %></p>
                <p>Category: <%= quiz.getCategory() %></p>
                <a href="PlayQuizServlet?quizId=<%= quiz.getId() %>">Take Quiz</a>
            </div>
            <%
                }
            } else {
            %>
            <p>No quizzes available at the moment.</p>
            <%
                }
            %>
        </div>
    </div>
    <footer>@Elene&Ana&Ana</footer>
</div>
</body>
</html>