<%@ page import="com.example.quizwebsite.quizManager.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userStyles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homepageStyles.css">
</head>
<body>
<div class="grid-container">
    <div class="header">
        <h1>Quiz Website</h1>
        <div class="nav">
            <a href="HomepageServlet?action=home" title="Home"><img src="icons/home-icon.png" alt="homepage icon"></a>
            <a href="HomepageServlet?action=takeQuiz" title="Play a quiz"><img src="icons/play-icon.png" alt="play quiz icon"></a>
            <a href="HomepageServlet?action=createQuiz" title="Create a quiz"><img src="icons/create-icon.png" alt="create a quiz icon"></a>
            <a href="HomepageServlet?action=profile" title="Your Profile"><img src="icons/profile-icon.png" alt="profile page icon"></a>
            <a href="HomepageServlet?action=logout" title="Logout"><img src="icons/logout.png" alt="logout icon"></a>
        </div>
    </div>
    <div class="notes-section">
        <div class="notes-header">
            <span class="notes-title">Recent Notes</span>
            <button class="send-note-btn" onclick="openNoteModal()">Send a Note</button>
        </div>
        <div class="note-list">
            <c:forEach items="${recentNotes}" var="note">
                <div class="note-item">
                    <div class="note-sender">From: ${note.senderUsername}</div>
                    <div class="note-preview">${fn:substring(note.message, 0, 50)}${fn:length(note.message) > 50 ? '...' : ''}</div>
                </div>
            </c:forEach>
            <c:if test="${empty recentNotes}">
                <p>You have no recent notes.</p>
            </c:if>
        </div>
    </div>
    <div class="content">
        <h2>Hello, ${user.getUsername()}!</h2>
        <p>Welcome to the Quiz Website. Choose an option from the menu above to get started.</p>

        <c:if test="${not empty noteMessage}">
            <p>${noteMessage}</p>
        </c:if>
    </div>
    <div class="quizzes-section">
        <div class="quizzes-header">
            <span class="quizzes-title">Your Quizzes</span>
        </div>
        <div class="quiz-list">
            <%
                List<Quiz> quizzes = (List<Quiz>) request.getAttribute("userQuizzes");
                if (quizzes != null && !quizzes.isEmpty()) {
                    for (Quiz quiz : quizzes) {
            %>
            <div class="quiz-item">
                <h3><%= quiz.getName() %></h3>
                <a href="${pageContext.request.contextPath}/aboutQuiz?quizId=<%= quiz.getId() %>">About Quiz</a>
            </div>
            <%
                }
            } else {
            %>
            <p>You don't have any quizzes yet.</p>
            <%
                }
            %>
        </div>
    </div>
</div>

<!-- Modal for sending notes -->
<div id="noteModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeNoteModal()">&times;</span>
        <h3>Send a Note</h3>
        <form class="note-form" action="NoteServlet" method="post">
            <input type="hidden" name="action" value="sendNote">
            <input type="text" name="recipient" placeholder="Recipient" required>
            <textarea name="message" placeholder="Your message" required></textarea>
            <button type="submit">Send Note</button>
        </form>
    </div>
</div>

<script>
    function openNoteModal() {
        document.getElementById('noteModal').style.display = 'block';
    }

    function closeNoteModal() {
        document.getElementById('noteModal').style.display = 'none';
    }

    // Close the modal if the user clicks outside of it
    window.onclick = function(event) {
        var modal = document.getElementById('noteModal');
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }
</script>
</body>
</html>
