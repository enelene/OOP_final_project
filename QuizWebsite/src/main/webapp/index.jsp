<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
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
        }
        h1 {
            color: #484c74;
            text-align: center;
            margin-bottom: 30px;
        }
        .success-message {
            color: green;
        }
        .error-message {
            color: red;
        }
        input {
            border-color: #484c74;
        }
        .btn-primary {
            background-color: #484c74;
            border-color: #484c74;
        }
        .btn-primary:hover {
            background-color: #3a3d5c;
            border-color: #3a3d5c;
        }
        .form-check label {
            color: #484c74;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome to Our Website</h1>

    <% if (request.getAttribute("message") != null) { %>
    <p class="success-message"><%= request.getAttribute("message") %></p>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
    <p class="error-message"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="LoginServlet" method="post">
        <div class="mb-3">
            <input type="text" class="form-control" name="username" placeholder="Username" required>
        </div>
        <div class="mb-3">
            <input type="password" class="form-control" name="password" placeholder="Password" required>
        </div>
        <div class="mb-3 form-check">
            <label>
                <input type="checkbox" class="form-check-input" name="rememberMe">
                Remember Me
            </label>
        </div>
        <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>
    </form>
    <div class="text-center mt-3">
        <a href="createAccount.jsp" class="text-decoration-none">Create a new account</a>
    </div>
</div>
</body>
</html>