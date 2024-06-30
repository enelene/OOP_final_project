<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Quiz</title>
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
            <li class="nav-item"><a class="nav-link" href="home.jsp">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Play</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Create</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Profile</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Logout</a></li>
        </ul>
    </div>
</nav>

<div class="container mt-5">
    <h1 class="mb-4">${quiz.name}</h1>

    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Quiz Details</h5>
            <p class="card-text"><strong>Description:</strong> ${quiz.description}</p>
            <p class="card-text"><strong>Category:</strong> ${quiz.category}</p>
            <p class="card-text"><strong>Display on Single Page:</strong> ${quiz.displayOnSinglePage ? 'Yes' : 'No'}</p>
            <p class="card-text"><strong>Display in Random Order:</strong> ${quiz.displayInRandomOrder ? 'Yes' : 'No'}</p>
            <p class="card-text"><strong>Allow Practice Mode:</strong> ${quiz.allowPracticeMode ? 'Yes' : 'No'}</p>
            <p class="card-text"><strong>Correct Immediately:</strong> ${quiz.correctImmediately ? 'Yes' : 'No'}</p>
        </div>
    </div>

    <h2 class="mb-3">Questions</h2>

    <c:forEach var="question" items="${questions}" varStatus="status">
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">Question ${status.index + 1}</h5>
                <p class="card-text"><strong>Question:</strong> ${question.text}</p>
                <p class="card-text"><strong>Type:</strong> ${question.type}</p>

                <c:if test="${question.type == 'multiple_choice'}">
                    <p class="card-text"><strong>Options:</strong></p>
                    <ul>
                        <c:forEach var="option" items="${question.options}" varStatus="optionStatus">
                            <li>${option} ${question.correctOptions[optionStatus.index] ? '(Correct)' : ''}</li>
                        </c:forEach>
                    </ul>
                </c:if>

                <c:if test="${question.type == 'true_false'}">
                    <p class="card-text"><strong>Correct Answer:</strong> ${question.correctAnswer}</p>
                </c:if>

                <c:if test="${question.type == 'short_answer'}">
                    <p class="card-text"><strong>Correct Answer:</strong> ${question.correctAnswer}</p>
                </c:if>
            </div>
        </div>
    </c:forEach>

    <a href="add_questions.jsp?quizId=${quiz.id}&quizName=${quiz.name}" class="btn btn-primary">Add More Questions</a>
    <a href="home.jsp" class="btn btn-secondary">Back to Home</a>
</div>
</body>
</html>