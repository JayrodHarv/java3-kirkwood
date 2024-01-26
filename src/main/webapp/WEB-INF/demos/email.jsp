<%@include file="/WEB-INF/shared/top.jsp"%>
    <title>Email</title>
</head>
<body>
    <c:if test="${not empty results.success}">
        <h5>${results.success}</h5>
    </c:if>
    <form action="${appURL}/email" method="post">
        <!-- Email Address -->
        <label for="email">Email Address:</label>
        <input type="email" id="email" name="email" required>
        <br>

        <!-- Subject -->
        <label for="subject">Subject:</label>
        <input type="text" id="subject" name="subject" required>
        <br>

        <!-- Message -->
        <label for="message">Message:</label>
        <textarea id="message" name="message" rows="4" required></textarea>
        <br>

        <!-- Submit Button -->
        <input type="submit" value="Submit">
    </form>
<%@include file="/WEB-INF/shared/bottom.jsp"%>
