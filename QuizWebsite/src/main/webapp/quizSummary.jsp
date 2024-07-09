</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.quizwebsite.quizManager.Attempt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.quizwebsite.quizManager.Quiz" %>
<%@ page import="com.example.quizwebsite.userManager.UserManager" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Summary</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f0f0f0;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            color: #424874;
            border-bottom: 2px solid #424874;
            padding-bottom: 10px;
        }

        p {
            margin-bottom: 10px;
        }

        a, button {
            display: inline-block;
            background-color: #424874;
            color: #fff;
            padding: 10px 15px;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 10px;
            margin-bottom: 10px;
        }

        a:hover, button:hover {
            background-color: #313759;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f4eeff;
            color: #424874;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<div class="container">
    <%
        Quiz quiz = ((Quiz) request.getAttribute("quiz"));
        UserManager um = (UserManager) application.getAttribute("userManager");
        String quizDescription = quiz.getDescription();
        String quizName = quiz.getName();
        int avg = (int) request.getAttribute("averageScore");
        int count = (int) request.getAttribute("attemptNum");
    %>
    <h1>Quiz Summary: <%= quizName %>
    </h1>
    <p><strong>Description:</strong> <%= quizDescription %>
    </p>
    <p><strong>Number of Attempts:</strong> <%= count %>
    </p>
    <p><strong>Average Score:</strong> <%= avg %>
    </p>

    <a href="PlayQuizServlet?quizId=<%= quiz.getId() %>">Take Quiz</a>

    <h2>Quiz Statistics</h2>
    <form action="${pageContext.request.contextPath}/aboutQuiz" method="get">
        <input type="hidden" name="quizId" value="${pageContext.request.getParameter("quizId")}">
        <input type="hidden" name="board" value="highscore">
        <button type="submit" name="action" value="highscores">View High Scores</button>
    </form>
    <form action="${pageContext.request.contextPath}/aboutQuiz" method="get">
        <input type="hidden" name="quizId" value="${pageContext.request.getParameter("quizId")}">
        <input type="hidden" name="board" value="recent">
        <button type="submit" name="action" value="recentquizzes">View Recent Attempts</button>
    </form>
    <form action="${pageContext.request.contextPath}/aboutQuiz" method="get">
        <input type="hidden" name="quizId" value="${pageContext.request.getParameter("quizId")}">
        <input type="hidden" name="board" value="topToday">
        <button type="submit" name="action" value="toptoday">View Today's Top Attempts</button>
    </form>
    <form action="${pageContext.request.contextPath}/aboutQuiz" method="get">
        <input type="hidden" name="quizId" value="${pageContext.request.getParameter("quizId")}">
        <input type="hidden" name="board" value="myAttempts">
        <button type="submit" name="action" value="myattempts">View My Previous Attempts</button>
    </form>

    <h2>List of Attempts</h2>
    <table>
        <thead>
        <tr>
            <th>Username</th>
            <th>Score</th>
            <th>Time</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Attempt> attempts = (List<Attempt>) request.getAttribute("attempts");
            if (attempts != null && !attempts.isEmpty()) {
                for (Attempt attempt : attempts) {
        %>
        <% String friendUsername = um.getUserById(attempt.getUserId()).getUsername();
        %>
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/users" method="get">
                    <input type="hidden" name="friendUsername" value="<%=friendUsername%>">
                    <button type="submit"><%=um.getUserById(attempt.getUserId()).getUsername()%>
                    </button>
                </form>
            </td>
            <td><%= attempt.getScore() %>
            </td>
            <td><%= attempt.getTime() %>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5">No attempts found.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>