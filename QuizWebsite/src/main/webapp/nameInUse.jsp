<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Username Taken</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #484c74;
            color: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: center;
        }
        h1 {
            color: #484c74;
        }
        a {
            color: #484c74;
            text-decoration: none;
        }
        a:hover {
            color: #3a3d5c;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Username already in use. Please try another.</h1>
    <a href="createAccount.jsp">Go back to create account page</a>
</div>
</body>
</html>