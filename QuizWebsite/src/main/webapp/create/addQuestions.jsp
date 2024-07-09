<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Questions to Quiz</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/quizCreationStyles.css">
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
    <h2>Add Questions to Quiz: ${param.quizName}</h2>
    <form action="${pageContext.request.contextPath}/AddQuestionServlet" method="post">
        <input type="hidden" name="quizId" value="${param.quizId}">
        <input type="hidden" name="quizName" value="${param.quizName}">

        <div class="form-group">
            <label for="questionText">Question:</label>
            <textarea class="form-control" id="questionText" name="questionText" rows="3" required></textarea>
        </div>

        <div class="form-group">
            <label for="questionType">Question Type:</label>
            <select class="form-control" id="questionType" name="questionType" required>
                <option value="MULTIPLE_CHOICE">Multiple Choice</option>
                <option value="TRUE_FALSE">True/False</option>
                <option value="SINGLE_ANSWER">Short Answer</option>
                <option value="PICTURE_RESPONSE">Picture-Response</option>
            </select>
        </div>

        <div id="imageUrlField" style="display:none;">
            <div class="form-group">
                <label for="imageUrl">Image URL:</label>
                <input type="url" class="form-control" id="imageUrl" name="imageUrl">
            </div>
        </div>

        <div id="answerOptions">
            <!-- Options will be dynamically inserted here -->
        </div>

        <button type="submit" class="btn btn-danger">Add Question</button>
    </form>

    <div class="mt-4">
        <a href="${pageContext.request.contextPath}/ViewQuizServlet?id=${param.quizId}&mode=view" class="btn btn-secondary">Finish Adding Questions</a>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const questionType = document.getElementById('questionType');
        const answerOptions = document.getElementById('answerOptions');
        const imageUrlField = document.getElementById('imageUrlField');

        function updateAnswerOptions() {
            switch(questionType.value) {
                case 'MULTIPLE_CHOICE':
                    answerOptions.innerHTML = '';
                    for (let i = 1; i <= 4; i++) {
                        answerOptions.appendChild(generateOptionInput(i));
                    }
                    const addButton = document.createElement('button');
                    addButton.type = 'button';
                    addButton.className = 'btn btn-sm btn-secondary mt-2';
                    addButton.id = 'addOption';
                    addButton.textContent = 'Add Option';
                    addButton.addEventListener('click', addNewOption);
                    answerOptions.appendChild(addButton);
                    break;
                case 'TRUE_FALSE':
                    answerOptions.innerHTML = '<div class="form-group">' +
                        '<div class="form-check">' +
                        '<input class="form-check-input" type="radio" name="correctAnswer" id="true" value="true" required>' +
                        '<label class="form-check-label" for="true">True</label>' +
                        '</div>' +
                        '<div class="form-check">' +
                        '<input class="form-check-input" type="radio" name="correctAnswer" id="false" value="false" required>' +
                        '<label class="form-check-label" for="false">False</label>' +
                        '</div>' +
                        '</div>';
                    break;
                case 'SINGLE_ANSWER':
                    answerOptions.innerHTML = '<div class="form-group">' +
                        '<label for="correctAnswer">Correct Answer:</label>' +
                        '<input type="text" class="form-control" id="correctAnswer" name="correctAnswer" required>' +
                        '</div>';
                    break;
                case 'PICTURE_RESPONSE':
                    imageUrlField.style.display = 'block';
                    answerOptions.innerHTML = '<div class="form-group">' +
                        '<label for="correctAnswer">Correct Answer:</label>' +
                        '<input type="text" class="form-control" id="correctAnswer" name="correctAnswer" required>' +
                        '</div>';
                    break;
            }
        }

        function generateOptionInput(index) {
            const div = document.createElement('div');
            div.className = 'form-group';
            div.innerHTML = '<label for="option' + index + '">Option ' + index + ':</label>' +
                '<div class="input-group">' +
                '<input type="text" class="form-control" id="option' + index + '" name="option' + index + '" ' + (index <= 2 ? 'required' : '') + '>' +
                '<div class="input-group-append">' +
                '<div class="input-group-text">' +
                '<input type="checkbox" name="correctOptions" value="' + (index - 1) + '">' +
                '<label class="mb-0 ml-2">Correct</label>' +
                '</div>' +
                '</div>' +
                '</div>';
            return div;
        }

        function addNewOption() {
            const optionCount = answerOptions.querySelectorAll('.form-group').length;
            if (optionCount < 6) {  // Limit to 6 options
                answerOptions.insertBefore(generateOptionInput(optionCount + 1), document.getElementById('addOption'));
            }
            if (optionCount === 5) {
                document.getElementById('addOption').style.display = 'none';
            }
        }

        questionType.addEventListener('change', updateAnswerOptions);
        updateAnswerOptions();  // Initialize on page load
    });
</script>
</body>
</html>