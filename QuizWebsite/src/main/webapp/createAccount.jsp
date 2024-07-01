<html>
<head>
    <title>Create Account</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h1>Create a New Account</h1>
    <form action="${pageContext.request.contextPath}/users" method="post">
        <input type="text" name="username" placeholder="Username"><br>
        <input type="password" name="password" placeholder="Password"><br>
        <input type="submit" value="Create Account">
    </form>
</div>
</body>
</html>
