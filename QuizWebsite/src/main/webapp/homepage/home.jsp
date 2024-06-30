<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="user-styles.css">
</head>
<body>
    <div class="grid-container">
        <div class="grid-item header">
            <h1>Quiz Website</h1>
        </div>
        <div class="grid-item nav">
            <a href="HomepageServlet?action=home" title="Home"><img src="icons/home-icon.png" alt="homepage icon"></a>
            <a href="HomepageServlet?action=createQuiz" title="Play a quiz"><img src="icons/play-icon.png" alt="play quiz icon"></a>
            <a href="HomepageServlet?action=takeQuiz" title="Create a quiz"><img src="icons/create-icon.png" alt="create a quiz icon"></a>
            <a href="HomepageServlet?action=profile" title="Your Profile"><img src="icons/profile-icon.png" alt="profile page icon"></a>                                          <!--you need to add correct link-->
            <a href="HomepageServlet?action=logout" title="Logout"><img src="icons/logout.png" alt="logout icon"></a>
        </div>
        <hr class="after-header">
        <div class="grid-item content">
                <h2>Hello, ${user.getUsername()}!</h2>
                <p>Welcome to the Quiz Website. Choose an option from the menu above to get started.</p>
        </div>

        <hr class="before-footer">
        <footer>@Elene&Ana&Ana</footer>
    </div>
</body>
</html>



