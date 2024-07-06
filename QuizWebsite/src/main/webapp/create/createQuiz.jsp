<!DOCTYPE html>
<html>
<head>
    <title>Create a New Quiz</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
<<<<<<< Updated upstream
    <link rel="stylesheet" type="text/css" href="quizCreationStyles.css">
=======
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/quizCreationStyles.css">
>>>>>>> Stashed changes
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">Quiz Website</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/HomepageServlet?action=home" title="Home"><img src="${pageContext.request.contextPath}/icons/home-icon.png" alt="homepage icon"></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="${pageContext.request.contextPath}/icons/play-icon.png" alt="play quiz icon"></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/HomepageServlet?action=createQuiz" title="Create a quiz"><img src="${pageContext.request.contextPath}/icons/create-icon.png" alt="create a quiz icon"></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/HomepageServlet?action=profile" title="Your Profile"><img src="${pageContext.request.contextPath}/icons/profile-icon.png" alt="profile page icon"></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/HomepageServlet?action=logout" title="Logout"><img src="${pageContext.request.contextPath}/icons/logout.png" alt="logout icon"></a></li>
        </ul>
    </div>
</nav>
<div class="container mt-5">
    <h2>Create a New Quiz</h2>
    <form action="${pageContext.request.contextPath}/SaveQuizServlet" method="post">
        <div class="form-group">
            <label for="quizName">Quiz Name:</label>
            <input type="text" class="form-control" id="quizName" name="quizName" required>
        </div>
        <div class="form-group">
            <label for="quizDescription">Quiz Description:</label>
            <textarea class="form-control" id="quizDescription" name="quizDescription" rows="3" required></textarea>
        </div>
        <div class="form-group">
            <label for="quizCategory">Quiz Category:</label>
            <select class="form-control" id="quizCategory" name="quizCategory" required>
                <option value="">Select a category</option>
                <option value="MATH">Math</option>
                <option value="SCIENCE">Science</option>
                <option value="HISTORY">History</option>
                <option value="LITERATURE">Literature</option>
                <option value="GENERAL_KNOWLEDGE">General Knowledge</option>
            </select>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="displayOnSinglePage" name="displayOnSinglePage" value="true">
            <input type="hidden" name="displayOnSinglePage" value="false">
            <label class="form-check-label" for="displayOnSinglePage">Display questions on a single page?</label>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="displayInRandomOrder" name="displayInRandomOrder" value="true">
            <input type="hidden" name="displayInRandomOrder" value="false">
            <label class="form-check-label" for="displayInRandomOrder">Display questions in a random order?</label>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="allowPracticeMode" name="allowPracticeMode" value="true">
            <input type="hidden" name="allowPracticeMode" value="false">
            <label class="form-check-label" for="allowPracticeMode">Allow users to take quiz in practice mode?</label>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="correctImmediately" name="correctImmediately" value="true">
            <input type="hidden" name="correctImmediately" value="false">
            <label class="form-check-label" for="correctImmediately">Correct user responses immediately?</label>
        </div>
        <button type="submit" class="btn btn-danger">Add Questions</button>
    </form>
</div>
</body>
<<<<<<< Updated upstream
</html>
=======
</html>
>>>>>>> Stashed changes
