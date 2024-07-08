<html>
<head>
    <title>Create Account</title>
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
    </style>
</head>
<body>
<div class="container">
    <h1>Create a New Account</h1>
    <form action="${pageContext.request.contextPath}/users" method="post">
        <div class="mb-3">
            <input type="text" class="form-control" name="username" placeholder="Username" required>
        </div>
        <div class="mb-3">
            <input type="password" class="form-control" name="password" placeholder="Password" required>
        </div>
        <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary">Create Account</button>
        </div>
    </form>
</div>
</body>
</html>