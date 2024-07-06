<!DOCTYPE html>
<html lang="en">
<head>
<<<<<<< Updated upstream
    <title>Add Questions to Quiz</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="quizCreationStyles.css">
    <%--temporary fix because I dont like the fact that styles are written like this--%>
     <style>
         .navbar {
             background-color: #424874; !important
             max-height: 80px;!important
             font-family: sans-serif; !important
         }

         body {
             background-color: #f4eeff; !important
         }

         img {
             height: 50px; !important
             width:  50px; !important
         }
     </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="#">Quiz Website</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <%--todo- cant identify servlet--%>
                <li class="nav-item"><a class="nav-link" href="../../HomepageServlet?action=home" title="Home"><img src="../icons/home-icon.png" alt="homepage icon"></a></li>
                <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="../icons/play-icon.png" alt="play quiz icon"></a></li>
                <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=createQuiz" title="Create a quiz"><img src="../icons/create-icon.png" alt="create a quiz icon"></a></li>
                <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=profile" title="Your Profile"><img src="../icons/profile-icon.png" alt="profile page icon"></a></li>
                <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=logout" title="Logout"><img src="../icons/logout.png" alt="logout icon"></a></li>
            </ul>
=======
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
>>>>>>> Stashed changes
        </div>
    </nav>

<<<<<<< Updated upstream
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
=======
        <div class="form-group">
            <label for="questionType">Question Type:</label>
            <select class="form-control" id="questionType" name="questionType" required>
                <option value="MULTIPLE_CHOICE">Multiple Choice</option>
                <option value="TRUE_FALSE">True/False</option>
                <option value="SINGLE_ANSWER">Short Answer</option>
            </select>
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
>>>>>>> Stashed changes
</body>
</html>