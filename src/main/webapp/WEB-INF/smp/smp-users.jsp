<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">UserID (Email)</th>
        <th scope="col">Display Name</th>
        <th scope="col">Status</th>
        <th scope="col">Role</th>
        <th scope="col">Created At</th>
        <th scope="col">Last Logged In</th>
        <th scope="col">Last Updated</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="u">
        <tr>
            <th scope="row">${u.getUserID()}</th>
            <td>${u.getDisplayName()}</td>
            <td>${u.getStatus()}</td>
            <td>${u.getRole()}</td>
            <td>${u.getCreatedAt()}</td>
            <td>${u.getLastLoggedIn()}</td>
            <td>${u.getUpdatedAt()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>