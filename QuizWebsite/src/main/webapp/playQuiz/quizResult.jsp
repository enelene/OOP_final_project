<%--
  Created by IntelliJ IDEA.
  User: Enele
  Date: 7/7/2024
  Time: 3:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Result</title>
    <link rel="stylesheet" type="text/css" href="userStyles.css">
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
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin: 20px;
            text-align: center;
        }
        .score {
            font-size: 24px;
            margin: 20px 0;
            color: #424874;
        }
        .back-link {
            display: inline-block;
            background-color: #424874;
            color: #fff;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 5px;
            margin-top: 20px;
        }
        .back-link:hover {
            background-color: #313759;
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
        <h2>Quiz Result for ${quiz.name}</h2>
        <div class="score">Your score: ${score} out of ${totalQuestions}</div>
        <a href="QuizzesServlet" class="back-link">Back to Quiz List</a>
    </div>
    <footer>@Elene&Ana&Ana</footer>
</div>
</body>
</html>