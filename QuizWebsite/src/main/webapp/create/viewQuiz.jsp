<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.example.quizwebsite.quizManager.Question" %>
<%@ page import="com.example.quizwebsite.quizManager.Quiz" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%!
    private String displayQuizDetails(Quiz quiz) {
        StringBuilder sb = new StringBuilder();
        sb.append("<p><strong>Description:</strong> ").append(quiz.getDescription()).append("</p>");
        sb.append("<p><strong>Category:</strong> ").append(quiz.getCategory()).append("</p>");
        sb.append("<p><strong>Display on Single Page:</strong> ").append(quiz.isDisplayOnSinglePage() ? "Yes" : "No").append("</p>");
        sb.append("<p><strong>Display in Random Order:</strong> ").append(quiz.isDisplayInRandomOrder() ? "Yes" : "No").append("</p>");
        sb.append("<p><strong>Allow Practice Mode:</strong> ").append(quiz.isAllowPracticeMode() ? "Yes" : "No").append("</p>");
        sb.append("<p><strong>Correct Immediately:</strong> ").append(quiz.isCorrectImmediately() ? "Yes" : "No").append("</p>");
        return sb.toString();
    }

    private String displayQuestion(Question question, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='card mb-3'>");
        sb.append("<div class='card-body'>");
        sb.append("<h5 class='card-title'>Question ").append(index + 1).append("</h5>");
        sb.append("<p class='card-text'><strong>Question:</strong> ").append(question.getText()).append("</p>");
        sb.append("<p class='card-text'><strong>Type:</strong> ").append(question.getType()).append("</p>");

        String questionType = question.getType().toString();
        if ("MULTIPLE_CHOICE".equals(questionType)) {
            sb.append("<p class='card-text'><strong>Options:</strong></p><ul>");
            List<String> options = question.getOptions();
            List<Boolean> correctOptions = question.getCorrectOptions();
            if (options != null && correctOptions != null) {
                for (int j = 0; j < options.size(); j++) {
                    sb.append("<li>").append(options.get(j));
                    if (correctOptions.get(j)) {
                        sb.append(" <span class='badge badge-success'>Correct</span>");
                    }
                    sb.append("</li>");
                }
            }
            sb.append("</ul>");
        } else if ("TRUE_FALSE".equals(questionType)) {
            String correctAnswer = question.getCorrectAnswer();
            sb.append("<p class='card-text'><strong>Correct Answer:</strong> ")
                    .append(correctAnswer != null ? correctAnswer : "Not set")
                    .append("</p>");
        } else if ("SINGLE_ANSWER".equals(questionType) || "PICTURE_RESPONSE".equals(questionType)) {
            String correctAnswer = question.getCorrectAnswer();
            sb.append("<p class='card-text'><strong>Correct Answer:</strong> ")
                    .append(correctAnswer != null ? correctAnswer : "Not set")
                    .append("</p>");

            if ("PICTURE_RESPONSE".equals(questionType)) {
                String imageUrl = question.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    sb.append("<img src='").append(imageUrl).append("' alt='Question Image' class='img-fluid mb-3' style='max-width: 100%; height: auto;'>");
                } else {
                    sb.append("<p class='text-warning'>No image provided for this picture-response question.</p>");
                }
            }
        }

        sb.append("</div></div>");
        return sb.toString();
    }
%>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/quizCreationStyles.css">
    <style>
        .badge-success {
            background-color: #28a745;
        }
    </style>
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
            <%= displayQuizDetails(quiz) %>
        </div>
    </div>

    <h2 class="mb-3">Questions</h2>

    <%
        List<Question> questions = (List<Question>) request.getAttribute("questions");
        if (questions != null && !questions.isEmpty()) {
            for (int i = 0; i < questions.size(); i++) {
    %>
    <%= displayQuestion(questions.get(i), i) %>
    <%
        }
    } else {
    %>
    <div class="alert alert-info" role="alert">
        No questions available for this quiz.
    </div>
    <%
        }
    %>

    <div class="mt-4">
        <a href="${pageContext.request.contextPath}/create/addQuestions.jsp?quizId=<%= quiz.getId() %>&quizName=<%= URLEncoder.encode(quiz.getName(), StandardCharsets.UTF_8.toString()) %>" class="btn btn-primary">Add More Questions</a>
        <a href="${pageContext.request.contextPath}/HomepageServlet?action=home" class="btn btn-secondary">Back to Home</a>
    </div>
    <%
    } else {
    %>
    <div class="alert alert-warning" role="alert">
        No quiz information available.
    </div>
    <a href="${pageContext.request.contextPath}/HomepageServlet?action=home" class="btn btn-secondary">Back to Home</a>
    <%
        }
    %>
</div>
</body>
</html>