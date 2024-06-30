<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Questions to Quiz</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Quiz Website</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item"><a class="nav-link" href="../homepage/home.jsp">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Play</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Create</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Profile</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Logout</a></li>
        </ul>
    </div>
</nav>

<div class="container mt-5">
    <h1 class="mb-4">Add Questions to Quiz: ${param.quizName}</h1>
    <form action="../AddQuestionServlet" method="post">
        <input type="hidden" name="quizId" value="${param.quizId}">

        <div class="form-group">
            <label for="questionText">Question:</label>
            <textarea class="form-control" id="questionText" name="questionText" rows="3" required></textarea>
        </div>

        <div class="form-group">
            <label for="questionType">Question Type:</label>
            <select class="form-control" id="questionType" name="questionType" required>
                <option value="multiple_choice">Multiple Choice</option>
                <option value="true_false">True/False</option>
                <option value="short_answer">Short Answer</option>
            </select>
        </div>

        <div id="answerOptions">
            <div class="form-group">
                <label for="option1">Option 1:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="option1" name="option1" required>
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <input type="checkbox" name="correctOptions" value="0">
                            <label class="mb-0 ml-2">Correct</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="option2">Option 2:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="option2" name="option2" required>
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <input type="checkbox" name="correctOptions" value="1">
                            <label class="mb-0 ml-2">Correct</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="option3">Option 3:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="option3" name="option3">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <input type="checkbox" name="correctOptions" value="2">
                            <label class="mb-0 ml-2">Correct</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="option4">Option 4:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="option4" name="option4">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <input type="checkbox" name="correctOptions" value="3">
                            <label class="mb-0 ml-2">Correct</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Add Question</button>
    </form>

    <a href="../ViewQuizServlet?id=${param.quizId}" class="btn btn-secondary mt-3">Finish Adding Questions</a>
</div>

<script>
    document.getElementById('questionType').addEventListener('change', function() {
        var answerOptions = document.getElementById('answerOptions');
        if (this.value === 'true_false') {
            answerOptions.innerHTML = `
                    <div class="form-group">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctAnswer" id="true" value="true" required>
                            <label class="form-check-label" for="true">True</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctAnswer" id="false" value="false" required>
                            <label class="form-check-label" for="false">False</label>
                        </div>
                    </div>
                `;
        } else if (this.value === 'short_answer') {
            answerOptions.innerHTML = `
                    <div class="form-group">
                        <label for="correctAnswer">Correct Answer:</label>
                        <input type="text" class="form-control" id="correctAnswer" name="correctAnswer" required>
                    </div>
                `;
        } else {
            answerOptions.innerHTML = `
                    <div class="form-group">
                        <label for="option1">Option 1:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="option1" name="option1" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <input type="checkbox" name="correctOptions" value="0">
                                    <label class="mb-0 ml-2">Correct</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="option2">Option 2:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="option2" name="option2" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <input type="checkbox" name="correctOptions" value="1">
                                    <label class="mb-0 ml-2">Correct</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="option3">Option 3:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="option3" name="option3">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <input type="checkbox" name="correctOptions" value="2">
                                    <label class="mb-0 ml-2">Correct</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="option4">Option 4:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="option4" name="option4">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <input type="checkbox" name="correctOptions" value="3">
                                    <label class="mb-0 ml-2">Correct</label>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
        }
    });
</script>
</body>
</html>