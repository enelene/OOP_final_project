<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .header {
            background-color: #333;
            color: #fff;
            padding: 10px 0;
            text-align: center;
        }
        .nav {
            margin: 20px;
            text-align: center;
        }
        .nav a {
            margin: 0 15px;
            text-decoration: none;
            color: #333;
            font-size: 18px;
        }
        .content {
            text-align: center;
            margin-top: 50px;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Quiz Website</h1>
</div>
<div class="nav">
    <a href="/QuizWebsite_war_exploded/homepage/home.jsp">Home</a>
    <a href="/QuizWebsite_war_exploded/homepage/play.jsp">Play</a>
    <a href="/QuizWebsite_war_exploded/homepage/create.jsp">Create</a>
    <a href="/QuizWebsite_war_exploded/homepage/logout.jsp">Logout</a>
</div>
<div class="content">
    <h2>Hello, <%= request.getParameter("username") %>!</h2>
    <p>Welcome to the Quiz Website. Choose an option from the menu above to get started.</p>
</div>
</body>
</html>
