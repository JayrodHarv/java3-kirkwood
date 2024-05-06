<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid p-0" style="margin-top: 75px; margin-bottom: 40px;">
    <div class="container py-3">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <div class="d-flex justify-content-between mb-4">
            <h2>No Go Outside Users</h2>
            <div class="text-end">
                <%--        <a href="${appURL}/add-build" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add Build"><i class="bi bi-plus-lg"></i></a>--%>
            </div>
        </div>
        <table class="table table-responsive table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">Pfp</th>
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
                    <td>
                        <img src="${user.getBase64Pfp()}" alt="pfp" width="45" height="45" class="rounded-circle">
                    </td>
                    <td>${user.getUserID()}</td>
                    <td>${user.getDisplayName()}</td>
                    <td>${user.getRole()}</td>
                    <td><fmt:formatDate value="${user.getCreatedAtDate()}" type="date" dateStyle="long" timeStyle="long"></fmt:formatDate></td>
                    <td><fmt:formatDate value="${user.getLastLoggedInDate()}" type="both" dateStyle="long" timeStyle="medium"></fmt:formatDate></td>
                    <td><fmt:formatDate value="${user.getUpdatedAtDate()}" type="both" dateStyle="long" timeStyle="medium"></fmt:formatDate></td>
                    <td>${user.getStatus()}</td>
                    <td>
                        <div class="btn-group">
                            <c:if test="${sessionScope.activeSMPUser.getRole().canEditUsers()}">
                                <a href="${appURL}/smp-edit-user?userID=${user.getUserID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit User"><i class="bi bi-pencil-fill"></i></a>
                            </c:if>

                            <c:if test="${sessionScope.activeSMPUser.getRole().canBanUsers()}">
                                <c:choose>
                                    <c:when test="${user.getStatus() eq 'active'}">
                                        <a class="btn btn-outline-danger" onclick="ConfirmBan('${user.getUserID()}')" data-bs-toggle="tooltip" data-bs-title="Ban User"><i class="bi bi-hammer"></i></a>
                                    </c:when>
                                    <c:when test="${user.getStatus() eq 'locked'}">
                                        <a class="btn btn-outline-success" onclick="ConfirmUnBan('${user.getUserID()}')" data-bs-toggle="tooltip" data-bs-title="Un-Ban User"><i class="bi bi-hammer"></i></a>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Ban Modal -->
    <div class="modal" id="banModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Ban User</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to ban this user?</p>
                </div>
                <form action="${appURL}/ban-user" method="POST">
                    <input type="hidden" id="userID" name="userID"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-danger">Confirm</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Ban Modal -->
    <div class="modal" id="unBanModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Un-Ban User</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to un-ban this user?</p>
                </div>
                <form action="${appURL}/un-ban-user" method="POST">
                    <input type="hidden" id="userID2" name="userID2"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-danger">Confirm</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        function ConfirmBan(id) {
            $('#userID').val(id);
            $('#banModal').modal('show');
        }
        function ConfirmUnBan(id) {
            $('#userID2').val(id);
            $('#unBanModal').modal('show');
        }
    </script>

</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>