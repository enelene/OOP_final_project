<html>
<head>
    <title>Create Account</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h1>Create a New Account</h1>
    <form action="${pageContext.request.contextPath}/requests" method="post">
        <input type="number" name="id" placeholder="id"><br>
        <input type="text" name="user" placeholder="user"><br>
        <input type="submit" value="Send Request">
    </form>
</div>
</body>
</html>

<%--todo--%>