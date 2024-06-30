<!DOCTYPE html>
<html>
<head>
    <title>Create a New Quiz</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</head>
<body>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Quiz Website</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item"><a class="nav-link" href="#">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Play</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Create</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Profile</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Logout</a></li>
        </ul>
    </div>
</nav>
<div class="container mt-5">
    <h2>Create a New Quiz</h2>
    <form action="SaveQuizServlet" method="post">
        <div class="form-group">
            <label for="quizName">Quiz Name:</label>
            <input type="text" class="form-control" id="quizName" name="quizName" required>
        </div>
        <div class="form-group">
            <label for="quizDescription">Quiz Description:</label>
            <textarea class="form-control" id="quizDescription" name="quizDescription" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="quizCategory">Quiz Category:</label>
            <select class="form-control" id="quizCategory" name="quizCategory">
                <option value="None">None</option>
                <!-- Add more categories as needed -->
            </select>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="displaySinglePage" name="displaySinglePage">
            <label class="form-check-label" for="displaySinglePage">Display questions on a single page?</label>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="displayRandomOrder" name="displayRandomOrder">
            <label class="form-check-label" for="displayRandomOrder">Display questions in a random order?</label>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="allowPracticeMode" name="allowPracticeMode">
            <label class="form-check-label" for="allowPracticeMode">Allow users to take quiz in practice mode?</label>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="correctImmediately" name="correctImmediately">
            <label class="form-check-label" for="correctImmediately">Correct user responses immediately?</label>
        </div>
        <button type="submit" class="btn btn-danger">Add Questions</button>
    </form>
</div>
</body>

</body>
</html>
