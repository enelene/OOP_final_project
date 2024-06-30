<!--<%@ page contentType="text/html;charset=UTF-8" language="java" %>-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>user Profile</title>
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
            <h2> ${user.getUsername()}'s Profile</h2>
        </div>
        <div class="grid-item achievement-board">
            <h3>Achievement Board</h3>
            <h4>Total score:</h4> <!--overall score number-->
            <h4>Quizzes taken:</h4> <!--number of quizzes taken-->
            <h4>Quizzes created:</h4> <!--number of quizzes created-->
        </div>
        <div class="grid-item friends-board">
            <h4 title="Friends"><img src="icons/friends-icon.png" alt="friends icon">3</h4> <!--friends number-->
            <h4 title="Friend requests"><img src="icons/friend-request-icon.png" alt="friend request icon">1</h4> <!--friend request number-->
        </div>
        <hr class="before-footer">
        <footer>@Elene&Ana&Ana</footer>
    </div>
</body>
</html>
