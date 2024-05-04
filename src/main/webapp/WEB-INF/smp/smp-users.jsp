<div class="d-flex justify-content-between mb-4">
    <h2>No Go Outside Users</h2>
    <div class="text-end">
<%--        <a href="${appURL}/add-build" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add Build"><i class="bi bi-plus-lg"></i></a>--%>
    </div>
</div>
<table class="table table-responsive table-bordered table-striped table-hover">
    <thead>
    <tr>
        <th scope="col">UserID (Email)</th>
        <th scope="col">Display Name</th>
        <th scope="col">Role</th>
        <th scope="col">Member Since</th>
        <th scope="col">Last Logged In</th>
        <th scope="col">Last Updated</th>
        <th scope="col">Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.getUserID()}</td>
            <td>${user.getDisplayName()}</td>
            <td>${user.getRole()}</td>
            <td><fmt:formatDate value="${user.getCreatedAtDate()}" type="date" dateStyle="long" timeStyle="long"></fmt:formatDate></td>
            <td><fmt:formatDate value="${user.getLastLoggedInDate()}" type="both" dateStyle="long" timeStyle="medium"></fmt:formatDate></td>
            <td><fmt:formatDate value="${user.getUpdatedAtDate()}" type="both" dateStyle="long" timeStyle="medium"></fmt:formatDate></td>
            <td>${user.getStatus()}</td>
            <td>
                <div class="btn-group">
                    <a href="${appURL}/edit-user?userId=${user.getUserID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit User"><i class="bi bi-pencil-fill"></i></a>
                    <a class="btn btn-outline-danger" onclick="Confirm('${user.getUserID()}')" data-bs-toggle="tooltip" data-bs-title="Ban User"><i class="bi bi-hammer"></i></a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Modal -->
<div class="modal fade" id="deleteModal" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="${appURL}/ban-user" method="POST">
                <input type="hidden" id="userID" name="userID"/>
                <div class="modal-header">
                    <h5 class="modal-title">Ban User</h5>
                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you sure you want to ban this user?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-danger">Confirm</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function Confirm(id) {
        $('#userID').val(id);
        $('#deleteModal').modal('show');
    }
</script>