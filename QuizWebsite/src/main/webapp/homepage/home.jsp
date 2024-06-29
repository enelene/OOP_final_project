<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
<div class="header">
    <h1>Quiz Website</h1>
</div>

<div class="nav">
    <a href="HomepageServlet?action=home">Home</a>
    <a href="HomepageServlet?action=createQuiz">Create Quiz</a>
    <a href="HomepageServlet?action=takeQuiz">Take Quiz</a>
    <a href="HomepageServlet?action=profile">Profile</a>
    <a href="HomepageServlet?action=logout">Logout</a>
</div>


<div class="content">
        <h1>Hello, ${user.getUsername()}!</h1>
        <p>Welcome to the Quiz Website. Choose an option from the menu above to get started.</p>
</div>
</body>
</html>



