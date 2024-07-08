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
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        .grid-container {
            display: grid;
            grid-template-areas:
                'header header'
                'notes content';
            grid-template-columns: 300px 1fr;
            grid-template-rows: auto 1fr;
            height: 100vh;
            overflow: hidden;
        }
        .header {
            grid-area: header;
            max-height: 80px;
            display: flex;
            justify-content: space-between;
            padding-left: 12px;
            padding-right: 12px;
            background-color: #484c74;
            color: white;
        }
        .content {
            grid-area: content;
            padding: 20px;
            overflow-y: auto;
            background-color: #f8f0ff;
        }
        .notes-section {
            grid-area: notes;
            background-color: #f0f0f0;
            padding: 20px;
            box-shadow: 2px 0 4px rgba(0,0,0,0.1);
            overflow-y: auto;
        }
        .notes-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        .notes-title {
            font-size: 1.2em;
            color: #484c74;
        }
        .send-note-btn {
            background-color: #484c74;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
        }
        .note-list {
            overflow-y: auto;
        }
        .note-item {
            background-color: white;
            border-left: 4px solid #484c74;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 4px;
        }
        .note-sender {
            font-weight: bold;
            margin-bottom: 5px;
            color: #484c74;
        }
        .note-preview {
            color: #666;
            font-size: 0.9em;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            border-radius: 8px;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .note-form input,
        .note-form textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .note-form button {
            background-color: #484c74;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
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