<%@ page import="com.example.quizwebsite.quizManager.Attempt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.quizwebsite.quizManager.Quiz" %>
<!DOCTYPE html>
<html>
<head>
    <title>Attempts List</title>
</head>
<body>
<%
    Quiz quiz = ((Quiz)request.getAttribute("quiz"));
    String quizDescription = quiz.getDescription();
    String quizName = quiz.getName();
    int avg = (int)request.getAttribute("averageScore");
    int count = (int)request.getAttribute("attemptNum");
%>
<p><%= quizName %></p>
<p><%= quizDescription %></p>
<p>Number of Attempts: <%= count %></p>
<p>AverageScore: <%= avg %></p>
<a href="PlayQuizServlet?quizId=<%= quiz.getId() %>">Take Quiz</a>
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
<h1>List of Attempts</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Quiz ID</th>
        <th>User ID</th>
        <th>Score</th>
        <th>Time</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Attempt> attempts = (List<Attempt>) request.getAttribute("attempts");
        if (attempts != null) {
            for (Attempt attempt : attempts) {
    %>
    <tr>
        <td><%= attempt.getId() %></td>
        <td><%= attempt.getQuizId() %></td>
        <td><%= attempt.getUserId() %></td>
        <td><%= attempt.getScore() %></td>
        <td><%= attempt.getTime() %></td>
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
</body>
</html>
