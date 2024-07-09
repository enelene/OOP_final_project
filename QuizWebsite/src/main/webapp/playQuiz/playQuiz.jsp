<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${quiz.name}</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userStyles.css">
  <style>
    .grid-container {
      grid-template-areas:
                'header'
                'content'
                'footer';
    }
    .header {
      height: 80px; !important;
      display: flex;
      justify-content: space-between;
      padding-left: 12px;
      padding-right: 12px;
      grid-area: header;
    }
    .content {
      grid-area: content;
      padding: 20px;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
      margin: 20px;
    }
    .question {
      margin-bottom: 20px;
      padding: 15px;
      background-color: #f4eeff;
      border-radius: 8px;
    }
    .options {
      margin-left: 20px;
    }
    input[type="submit"] {
      background-color: #424874;
      color: #fff;
      border: none;
      padding: 10px 20px;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
    }
    input[type="submit"]:hover {
      background-color: #313759;
    }
    .question-image-container {
      display: flex;
      justify-content: center;
      margin: 20px 0;
    }

    .question-image {
      width: 400px; /* Reduced from 700px to 400px */
      height: auto;
      max-width: 100%;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    @media (max-width: 768px) {
      .question-image {
        width: 90%; /* Slightly reduced for smaller screens */
      }
    }
  </style>
</head>
<body>
<div class="grid-container">
  <div class="grid-item header">
    <h1>Quiz Website</h1>
    <div class="nav">
      <a href="HomepageServlet?action=home" title="Home"><img src="icons/home-icon.png" alt="homepage icon"></a>
      <a href="HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="icons/play-icon.png" alt="play quiz icon"></a>
      <a href="HomepageServlet?action=createQuiz" title="Create a quiz"><img src="icons/create-icon.png" alt="create a quiz icon"></a>
      <a href="HomepageServlet?action=profile" title="Your Profile"><img src="icons/profile-icon.png" alt="profile page icon"></a>
      <a href="HomepageServlet?action=logout" title="Logout"><img src="icons/logout.png" alt="logout icon"></a>
    </div>
  </div>
  <div class="grid-item content">
    <h2>${quiz.name}</h2>
    <form action="PlayQuizServlet" method="post">
      <input type="hidden" name="quizId" value="${quiz.id}">
      <c:forEach var="question" items="${quiz.questions}" varStatus="status">
        <div class="question">
          <p><strong>Question ${status.index + 1}:</strong> ${question.text}</p>

          <c:if test="${question.type == 'PICTURE_RESPONSE' && not empty question.imageUrl}">
            <div class="question-image-container">
              <img src="${question.imageUrl}" alt="Question Image" class="question-image">
            </div>
          </c:if>

          <div class="options">
            <c:choose>
              <c:when test="${question.type == 'MULTIPLE_CHOICE'}">
                <c:forEach var="option" items="${question.options}">
                  <input type="radio" name="question_${question.id}" value="${option}" id="option_${question.id}_${option}">
                  <label for="option_${question.id}_${option}">${option}</label><br>
                </c:forEach>
              </c:when>
              <c:when test="${question.type == 'TRUE_FALSE'}">
                <input type="radio" name="question_${question.id}" value="true" id="true_${question.id}">
                <label for="true_${question.id}">True</label><br>
                <input type="radio" name="question_${question.id}" value="false" id="false_${question.id}">
                <label for="false_${question.id}">False</label><br>
              </c:when>
              <c:when test="${question.type == 'PICTURE_RESPONSE' || question.type == 'SINGLE_ANSWER'}">
                <input type="text" name="question_${question.id}" class="form-control">
              </c:when>
            </c:choose>
          </div>
        </div>
      </c:forEach>
      <input type="submit" value="Submit Quiz">
    </form>
  </div>
  <footer>@Elene&Ana&Ana</footer>
</div>
</body>
</html>