<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.example.quizwebsite.quizManager.Question" %>
<%@ page import="com.example.quizwebsite.quizManager.Quiz" %>
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
    <link rel="stylesheet" type="text/css" href="quizCreationStyles.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">Quiz Website</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=home" title="Home"><img src="icons/home-icon.png" alt="homepage icon"></a></li>
            <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="icons/play-icon.png" alt="play quiz icon"></a></li>
            <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=createQuiz" title="Create a quiz"><img src="icons/create-icon.png" alt="create a quiz icon"></a></li>
            <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=profile" title="Your Profile"><img src="icons/profile-icon.png" alt="profile page icon"></a> </li>
            <li class="nav-item"><a class="nav-link" href="HomepageServlet?action=logout" title="Logout"><img src="icons/logout.png" alt="logout icon"></a></li>
        </ul>
    </div>
</nav>

<div class="container mt-5">
    <%
        Quiz quiz = (Quiz) request.getAttribute("quiz");
        if (quiz != null) {
    %>
    <h1 class="mb-4"><%= quiz.getName() %></h1>

    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Quiz Details</h5>
            <p class="card-text"><strong>Description:</strong> <%= quiz.getDescription() %></p>
            <p class="card-text"><strong>Category:</strong> <%= quiz.getCategory() %></p>
            <p class="card-text"><strong>Display on Single Page:</strong> <%= quiz.isDisplayOnSinglePage() ? "Yes" : "No" %></p>
            <p class="card-text"><strong>Display in Random Order:</strong> <%= quiz.isDisplayInRandomOrder() ? "Yes" : "No" %></p>
            <p class="card-text"><strong>Allow Practice Mode:</strong> <%= quiz.isAllowPracticeMode() ? "Yes" : "No" %></p>
            <p class="card-text"><strong>Correct Immediately:</strong> <%= quiz.isCorrectImmediately() ? "Yes" : "No" %></p>
        </div>
    </div>

    <h2 class="mb-3">Questions</h2>

    <%
        List<Question> questions = (List<Question>) request.getAttribute("questions");
        if (questions != null && !questions.isEmpty()) {
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
    %>
    <div class="card mb-3">
        <div class="card-body">
            <h5 class="card-title">Question <%= i + 1 %></h5>
            <p class="card-text"><strong>Question:</strong> <%= question.getText() %></p>
            <p class="card-text"><strong>Type:</strong> <%= question.getType() %></p>

            <% if ("multiple_choice".equals(question.getType())) { %>
            <p class="card-text"><strong>Options:</strong></p>
            <ul>
                <%
                    List<String> options = question.getOptions();
                    List<Boolean> correctOptions = question.getCorrectOptions();
                    if (options != null && correctOptions != null) {
                        for (int j = 0; j < options.size(); j++) {
                %>
                <li><%= options.get(j) %> <%= correctOptions.get(j) ? "(Correct)" : "" %></li>
                <%
                        }
                    }
                %>
            </ul>
            <% } else if ("true_false".equals(question.getType()) || "short_answer".equals(question.getType())) { %>
            <p class="card-text"><strong>Correct Answer:</strong> <%= question.getCorrectAnswer() %></p>
            <% } %>
        </div>
    </div>
    <%
        }
    } else {
    %>
    <p>No questions available for this quiz.</p>
    <%
        }
    %>

    <a href="create/addQuestions.jsp?quizId=<%= quiz.getId() %>&quizName=<%= URLEncoder.encode(quiz.getName(), "UTF-8") %>" class="btn btn-primary">Add More Questions</a>
    <a href="../homepage/home.jsp" class="btn btn-secondary">Back to Home</a>
    <%
    } else {
    %>
    <p>No quiz information available.</p>
    <a href="../homepage/home.jsp" class="btn btn-secondary">Back to Home</a>
    <%
        }
    %>
</div>
</body>
</html>